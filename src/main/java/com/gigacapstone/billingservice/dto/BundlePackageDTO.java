package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.model.CallTime;
import lombok.Data;

@Data
public class BundlePackageDTO {
    String name;
    double price;
    Boolean isEnabled;
    Boolean isVatApplied;
    int vatPercentage;
    ExpirationRate expirationRate;
    CallTime callTime;
    private int downloadSpeed;
    private int uploadSpeed;
    private int dataSize;
}
