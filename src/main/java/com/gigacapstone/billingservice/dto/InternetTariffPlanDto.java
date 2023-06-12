package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;


@Data
public class InternetTariffPlanDto {
    private UUID id;
    @NotBlank
    private String name;
    private int dataSize;
    private double price;
    private boolean isVatApplied;
    private int vatPercentage;
    private boolean isEnabled;
    private int downloadSpeed;
    private int uploadSpeed;
    private ExpirationRate expiry;
}
