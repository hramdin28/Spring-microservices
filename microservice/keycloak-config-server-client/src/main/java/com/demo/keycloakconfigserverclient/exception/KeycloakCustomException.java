package com.demo.keycloakconfigserverclient.exception;

public class KeycloakCustomException extends Exception {
    public KeycloakCustomException(String errorMessage) {
        super(errorMessage);
    }
}
