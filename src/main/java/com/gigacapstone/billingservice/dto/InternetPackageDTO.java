package com.gigacapstone.billingservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;


@Data
public class InternetPackageDTO {
    private UUID id;
    @Positive
    @NotNull(message = "downloadSpeed cannot be null")
    private Double downloadSpeed;
    @Positive
    @NotNull(message = "dataSize cannot be null")
    private Double dataSize;
    @Positive
    @NotNull(message = "uploadSpeed cannot be null")
    private Double uploadSpeed;
    @NotNull(message = "TariffPlan cannot be null")
    private TariffPlanDTO tariffPlan;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}