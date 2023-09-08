package com.natan.carapi.infra.exceptions;

public class InvalidBrandException extends Exception {
    public InvalidBrandException() {
    }

    public InvalidBrandException(String message) {
        super(message);
    }
}
