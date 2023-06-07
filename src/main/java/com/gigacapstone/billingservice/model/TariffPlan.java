package com.gigacapstone.billingservice.model;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class TariffPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "tariff name cannot be empty")
    @Pattern(regexp = "\\S+.*\\S+", message = "Username cannot have leading or trailing spaces")
    private String name;

    @Positive(message = "price must be a positive value")
    private double price;

    @NotNull
    private Boolean isEnabled;

    @NotNull
    private Boolean isVatApplied;

    @Positive
    private int vatPercentage;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "expiration rate cannot be null")
    private ExpirationRate expirationRate;
}
