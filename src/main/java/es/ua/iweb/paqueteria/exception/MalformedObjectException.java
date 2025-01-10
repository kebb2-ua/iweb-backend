package es.ua.iweb.paqueteria.exception;

import es.ua.iweb.paqueteria.constants.ErrorMessages;

public class MalformedObjectException extends SHSRuntimeException {
    public static String INVALID_OBJECT = "INVALID_OBJECT";

    public MalformedObjectException(String message, String translationKey) {
        super(message, translationKey);
    }

    public static MalformedObjectException invalidObject() {
        return new MalformedObjectException(ErrorMessages.INVALID_OBJECT, INVALID_OBJECT);
    }
}