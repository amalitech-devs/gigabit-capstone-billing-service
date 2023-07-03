package com.gigacapstone.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gigacapstone.billingservice.enums.BillingType;
import com.gigacapstone.billingservice.enums.TariffType;
import com.gigacapstone.billingservice.model.CallTime;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private UUID id;
    private String tariffName;
    private String status;
    private UUID userId;

    @NotNull(message = "tariff type is required")
    private TariffType type;

    @NotNull(message = "billing type is required. (ONE_TIME or AUTO_RENEWAL)")
    private BillingType billingType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    private double downloadSpeed;
    private double dataSize;
    private double uploadSpeed;
    private CallTime callTime;
    private double price;
}
