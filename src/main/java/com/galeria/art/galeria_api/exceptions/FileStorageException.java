package com.galeria.art.galeria_api.exceptions;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }
    public FileStorageException(String message, Throwable cause) { super(message, cause); }
}
