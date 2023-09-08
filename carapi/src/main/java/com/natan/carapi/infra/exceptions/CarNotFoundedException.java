package com.natan.carapi.infra.exceptions;

public class CarNotFoundedException extends Exception {

    public CarNotFoundedException() {
    }

    public CarNotFoundedException(String message) {
        super(message);
    }
}
