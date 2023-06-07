package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class InternetTariffPlanDto {
    @NotBlank
    private String name;

    private int dataSize;

    private double price;

    private boolean vat;

    private int downloadSpeed;

    private int uploadSpeed;

    private boolean enabled;

    private ExpirationRate expiry;
}
