package es.ua.iweb.paqueteria.exception;

import es.ua.iweb.paqueteria.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public class SHSRuntimeException extends RuntimeException {
    private String message;
    private String translationKey;

    public List<ErrorResponse> toList() {
        return Collections.singletonList(ErrorResponse.of(message, translationKey));
    }
}
