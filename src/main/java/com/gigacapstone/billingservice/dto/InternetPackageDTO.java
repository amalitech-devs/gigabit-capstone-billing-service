package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.model.TariffPlan;
import lombok.Data;

import java.util.UUID;


@Data
public class InternetPackageDTO {
    private UUID id;
    private Double downloadSpeed;
    private Double dataSize;
    private Double uploadSpeed;
    private TariffPlanDTO tariffPlan;
}