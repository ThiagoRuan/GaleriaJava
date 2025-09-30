package com.galeria.art.galeria_api.exceptions;

public class FotoUploadException extends RuntimeException {
    public FotoUploadException(String message) {
        super(message);
    }
    public FotoUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
