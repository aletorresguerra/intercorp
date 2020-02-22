package com.intercorp.entrevista.Exception;

public class BaseException extends RuntimeException {
    private String messageCode;

    public BaseException(final String messageCode) {
        this.messageCode = messageCode;
    }
}
