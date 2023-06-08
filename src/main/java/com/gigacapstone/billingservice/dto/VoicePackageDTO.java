package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.model.CallTime;
import lombok.Data;

@Data
public class VoicePackageDTO{
    String name;
    double price;
    Boolean isEnabled;
    Boolean isVatApplied;
    int vatPercentage;
    ExpirationRate expirationRate;
    CallTime callTime;
}