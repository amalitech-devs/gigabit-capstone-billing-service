package com.gigacapstone.billingservice.config.exception;


public class TariffPlanNotFoundException extends RuntimeException {

    public TariffPlanNotFoundException(String message) {
        super(message);
    }

    public TariffPlanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}