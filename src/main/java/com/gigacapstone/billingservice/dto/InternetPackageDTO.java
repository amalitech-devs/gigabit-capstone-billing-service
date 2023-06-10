package com.gigacapstone.billingservice.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class InternetPackageDTO {
    private UUID id;
    private TariffPlanDTO tariffPlan;
    private int downloadSpeed;
    private int dataSize;
    private int uploadSpeed;
}