package com.gigacapstone.billingservice.dto;


import com.gigacapstone.billingservice.constant.ExpiryDuration;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TariffPlanDto {
    @NotBlank
    private String name;

    private int dataSize;

    private double price;

    private boolean vat;

    private int downloadSpeed;

    private int uploadSpeed;

    private boolean enabled;

    private ExpiryDuration expiry;
}
