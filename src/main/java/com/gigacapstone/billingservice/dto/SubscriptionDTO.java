package com.gigacapstone.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gigacapstone.billingservice.enums.BillingType;
import com.gigacapstone.billingservice.enums.TariffType;
import com.gigacapstone.billingservice.model.CallTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private UUID id;
    private String tariffName;
    private String status;
    private UUID userId;
    private TariffType type;
    private BillingType billingType;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    private double downloadSpeed;
    private double dataSize;
    private double uploadSpeed;
    private CallTime callTime;
}
