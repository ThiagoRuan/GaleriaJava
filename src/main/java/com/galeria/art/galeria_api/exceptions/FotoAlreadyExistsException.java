package com.galeria.art.galeria_api.exceptions;

public class FotoAlreadyExistsException extends RuntimeException {
    public FotoAlreadyExistsException(String message) {
        super(message);
    }
}
