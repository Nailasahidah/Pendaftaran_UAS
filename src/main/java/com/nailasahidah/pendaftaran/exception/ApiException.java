package com.nailasahidah.pendaftaran.exception;

public class ApiException extends RuntimeException{
    public ApiException (String messages) {
        super(messages);
    }
}
