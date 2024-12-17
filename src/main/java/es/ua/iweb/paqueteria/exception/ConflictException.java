package es.ua.iweb.paqueteria.exception;

import es.ua.iweb.paqueteria.constants.ErrorMessages;

public class ConflictException extends SHSRuntimeException {
    public static String EMAIL_IS_REGISTERED = "CONFLICT_EXCEPTION.EMAIL_IS_REGISTERED";
    public static String EMAIL_IS_VERIFIED = "CONFLICT_EXCEPTION.EMAIL_IS_VERIFIED";

    public ConflictException(String message, String translationKey) {
        super(message, translationKey);
    }

    public static ConflictException emailIsRegistered() {
        return new ConflictException(ErrorMessages.EMAIL_IS_REGISTERED, EMAIL_IS_REGISTERED);
    }
    public static ConflictException emailIsVerified() {
        return new ConflictException(ErrorMessages.EMAIL_ALREADY_VERIFIED, EMAIL_IS_VERIFIED);
    }
}
