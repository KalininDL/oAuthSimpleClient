package ru.internship.oAuth.exceptions;

public class JWTSecurityException extends Exception{
    public JWTSecurityException(String errorMessage) {
        super(errorMessage);
    }
}
