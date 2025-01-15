package es.ua.iweb.paqueteria.exception;

import es.ua.iweb.paqueteria.constants.ErrorMessages;

public class ConflictException extends SHSRuntimeException {
    public static String EMAIL_IS_REGISTERED = "CONFLICT_EXCEPTION.EMAIL_IS_REGISTERED";
    public static String EMAIL_IS_VERIFIED = "CONFLICT_EXCEPTION.EMAIL_IS_VERIFIED";
    public static String NIF_IS_REGISTERED = "CONFLICT_EXCEPTION.NIF_IS_REGISTERED";
    public static String PAYMENT_ALREADY_ACCEPTED = "CONFLICT_EXCEPTION.PAYMENT_ALREADY_ACCEPTED";


    public ConflictException(String message, String translationKey) {
        super(message, translationKey);
    }

    public static ConflictException emailIsRegistered() {
        return new ConflictException(ErrorMessages.EMAIL_IS_REGISTERED, EMAIL_IS_REGISTERED);
    }

    public static ConflictException emailIsVerified() {
        return new ConflictException(ErrorMessages.EMAIL_ALREADY_VERIFIED, EMAIL_IS_VERIFIED);
    }

    public static ConflictException nifIsRegistered() {
        return new ConflictException(ErrorMessages.NIF_IS_REGISTERED, NIF_IS_REGISTERED);
    }

    public static ConflictException paymentAlreadyAccepted() {
        return new ConflictException(ErrorMessages.PAYMENT_ALREADY_ACCEPTED, PAYMENT_ALREADY_ACCEPTED);
    }
}
