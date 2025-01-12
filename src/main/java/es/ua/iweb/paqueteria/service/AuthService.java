package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.AuthenticationResponse;
import es.ua.iweb.paqueteria.dto.LoginRequest;
import es.ua.iweb.paqueteria.dto.RegisterRequest;
import es.ua.iweb.paqueteria.entity.DireccionEntity;
import es.ua.iweb.paqueteria.entity.PasswordResetEntity;
import es.ua.iweb.paqueteria.entity.SessionEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.exception.*;
import es.ua.iweb.paqueteria.repository.DireccionRepository;
import es.ua.iweb.paqueteria.repository.PasswordResetRepository;
import es.ua.iweb.paqueteria.repository.SessionRepository;
import es.ua.iweb.paqueteria.repository.UserRepository;
import es.ua.iweb.paqueteria.type.AccountStatusType;
import es.ua.iweb.paqueteria.type.RoleType;
import es.ua.iweb.paqueteria.type.TokenType;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    public static final String VERIFY_EMAIL_PATH = "/api/v1/auth/verification/verify?token=";

    @Value("${application.host}")
    private String host;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordResetRepository passwordResetRepository;

    @Autowired
    private final SessionRepository sessionRepository;

    @Autowired
    private final DireccionRepository direccionRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final EmailingService emailingService;

    private static final SecureRandom secureRandom = new SecureRandom(); // thread safe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private final static String EMAIL_SUBJECT = "Verifica tu correo";
    private final static String EMAIL_TEXT = "Por favor verifique su correo en el siguiente enlace {link}";

    private final static Integer PASSWORD_RESET_EXPIRATION = 60 * 60; // SECONDS

    public void sendPasswordRecoveryMail(String email) {
        // Obtenemos la entidad asociada al email
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(DataNotFoundException::userNotFound);

        // Calculamos cuando expira el token de contraseña olvidada
        Instant expiryInstant = Instant.now().plusSeconds(PASSWORD_RESET_EXPIRATION);

        // Si ya existe un PasswordResetEntity
        PasswordResetEntity passwordResetEntity = passwordResetRepository.findByUserEntity(userEntity)
                .orElse(PasswordResetEntity.builder().userEntity(userEntity).build());

        passwordResetEntity.setToken(jwtService.generatePasswordResetToken(userEntity, PASSWORD_RESET_EXPIRATION));
        passwordResetEntity.setExpiryDate(Date.from(expiryInstant));
        passwordResetEntity.setUsed(false);

        var finalPasswordResetEntity = passwordResetRepository.save(passwordResetEntity);

        String recoverLink = host + "/api/v1/auth/recover?token=" + finalPasswordResetEntity.getToken();
        emailingService.sendEmail(email,
                "Recuperación de contraseña",
                "Para recuperar tu contraseña haz click en el siguiente enlace: " + recoverLink);
    }

    public void recoverPassword(String token, String newPassword) {
        // Obtenemos el PasswordResetEntity
        PasswordResetEntity passwordReset = passwordResetRepository.findByToken(token).orElseThrow(DataNotFoundException::tokenNotFound);

        // Comprobamos que no ha caducado el token
        if (passwordReset.getExpiryDate().before(new Date())) {
            throw InvalidTokenException.tokenExpired();
        }

        // Comprobamos que no se ha usado el token
        if (passwordReset.isUsed()) {
            throw InvalidTokenException.tokenAlreadyUsed();
        }

        // Cambiamos la contraseña y guardamos
        UserEntity userEntity = passwordReset.getUserEntity();
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);

        passwordReset.setUsed(true);
        passwordResetRepository.save(passwordReset);
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent((userEntity) -> {
            throw ConflictException.emailIsRegistered();
        });

        userRepository.findByNif(request.getNif()).ifPresent((userEntity) -> {
            throw ConflictException.nifIsRegistered();
        });

        List<RoleType> roleTypeList = new ArrayList<>();
        roleTypeList.add(RoleType.USER);
        UserEntity userEntity = UserEntity.builder()
                .nif(request.getNif())
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .razonSocial(request.getRazonSocial())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountStatusType(AccountStatusType.NOT_VERIFIED)
                .verificationToken(jwtService.generateEmailVerificationToken(request.getEmail()))
                .rolesList(roleTypeList)
                .build();

        var savedUser = saveCredentials(userEntity);

        DireccionEntity direccionEntity = direccionRepository.save(
                DireccionEntity.builder()
                .user(savedUser)
                .nombre(request.getRazonSocial() != null ? request.getRazonSocial() : request.getNombre() + " " + request.getApellidos())
                .nif(request.getNif())
                .lineaDireccion1(request.getDireccion().getLineaDireccion1())
                .lineaDireccion2(request.getDireccion().getLineaDireccion2())
                .municipio(request.getDireccion().getMunicipio())
                .localidad(request.getDireccion().getLocalidad())
                .codigoPostal(request.getDireccion().getCodigoPostal())
                .provincia(request.getDireccion().getProvincia())
                .pais(request.getDireccion().getPais())
                .build()
        );

        savedUser.setDireccion(direccionEntity);
        saveCredentials(savedUser);

        var accessToken = jwtService.generateAccessToken(userEntity);
        var refreshToken = jwtService.generateRefreshToken(userEntity);
        var publicId = getPublicId();

        String verificationLink = host + VERIFY_EMAIL_PATH + savedUser.getVerificationToken();
        emailingService.sendEmail(request.getEmail(),
                EMAIL_SUBJECT,
                EMAIL_TEXT.replace("{link}", verificationLink));
        saveUserSession(savedUser, accessToken, refreshToken, publicId);

        var jwt = jwtService.generateJWT(userEntity, accessToken, refreshToken, publicId);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }

    @Transactional
    @Modifying
    public void verifyEmailByToken(String token) {
        UserEntity userEntity = userRepository.findByVerificationToken(token).orElseThrow(UnauthorizedException::notAuthorized);

        userEntity.setVerificationToken(null);
        userEntity.setAccountStatusType(AccountStatusType.VERIFIED);

        userRepository.save(userEntity);
    }

    public void resendVerificationEmail(UserEntity userEntity) {
        if (userEntity.getAccountStatusType() == AccountStatusType.VERIFIED) {
            throw ConflictException.emailIsVerified();
        }

        // Generamos un nuevo token y lo enviamos
        userEntity.setVerificationToken(jwtService.generateEmailVerificationToken(userEntity.getEmail()));
        var savedUser = saveCredentials(userEntity);

        String verificationLink = host + VERIFY_EMAIL_PATH + savedUser.getVerificationToken();
        emailingService.sendEmail(savedUser.getEmail(),
                EMAIL_SUBJECT,
                EMAIL_TEXT.replace("{link}", verificationLink));
    }

    public AuthenticationResponse login(LoginRequest request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(DataNotFoundException::userNotFound);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var accessToken = jwtService.generateAccessToken(userEntity);
        var refreshToken = jwtService.generateRefreshToken(userEntity);
        var publicId = getPublicId();

        saveUserSession(userEntity, accessToken, refreshToken, publicId);

        var jwt = jwtService.generateJWT(userEntity, accessToken, refreshToken, publicId);

        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }

    private UserEntity saveCredentials(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    private void saveUserSession(UserEntity userEntity, String accessToken,
                                 String refreshToken, String publicId) {
        SessionEntity session = SessionEntity.builder()
                .user(userEntity)
                .publicId(publicId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .build();
        sessionRepository.save(session);
    }

    public static String getPublicId() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}