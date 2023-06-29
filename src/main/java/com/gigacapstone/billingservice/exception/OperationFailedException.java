package com.gigacapstone.billingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OperationFailedException extends RuntimeException{

    public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }
}
