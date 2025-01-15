package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.ApiKeyDTO;
import es.ua.iweb.paqueteria.dto.AuthenticationResponse;
import es.ua.iweb.paqueteria.dto.LoginRequest;
import es.ua.iweb.paqueteria.dto.RegisterRequest;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Hidden
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {;
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @GetMapping("/verification/verify")
    public ResponseEntity<HttpStatus> verifyEmailByToken(@RequestParam("token") String token) {
        authService.verifyEmailByToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification/resend")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> resendVerification() {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authService.resendVerificationEmail(userEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recover")
    public ResponseEntity<HttpStatus> recoverPassword(@RequestParam("token") String token, @RequestBody @Valid String password) {
        authService.recoverPassword(token, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recover/send")
    public ResponseEntity<HttpStatus> sendPasswordRecoveryMail(@RequestBody @Valid String email) {
        authService.sendPasswordRecoveryMail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    /* API ENDPOINTS */

    @GetMapping("/apikey")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ApiKeyDTO>> getApiKey() {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(authService.getAllUserApiKeys(userEntity));
    }

    @PostMapping("/apikey/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Map<String, String>> createApiKey() {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String jwt = authService.createApiKey(userEntity);
        return ResponseEntity.ok(Map.of("jwt", jwt));
    }

    @PostMapping("/apikey/delete")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> deleteApiKey(@RequestBody @Valid ApiKeyDTO apiKeyDTO) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authService.deleteApiKey(apiKeyDTO.getPublicId());
        return ResponseEntity.ok().build();
    }
}
