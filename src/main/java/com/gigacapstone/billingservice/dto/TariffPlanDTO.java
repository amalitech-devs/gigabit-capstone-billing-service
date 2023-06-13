package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.Positive;

@Data
public class TariffPlanDTO{
    private Long id;

    @NotEmpty(message = "tariff name cannot be empty")
    @Pattern(regexp = "\\S+.*\\S+", message = "Username cannot have leading or trailing spaces")
    private String name;

    @Positive(message = "price must be a positive value")
    private Double price;
    @NotNull
    private Boolean isEnabled;

    @NotNull
    private Boolean isVatApplied;

    @Positive
    private Integer vatPercentage;

    @NotNull(message = "expiration rate cannot be null")
    private ExpirationRate expirationRate;

    public String getName() {
        return name.trim();
    }
}
