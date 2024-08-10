package com.nailasahidah.cashier.exception;

public class ApiException extends RuntimeException{
    public ApiException (String messages) {
        super(messages);
    }
}
