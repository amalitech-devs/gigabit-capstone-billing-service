package com.gigacapstone.billingservice.dto;

import com.gigacapstone.billingservice.enums.TariffType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private String tariffName;
    private LocalDate expiryDate;
    private String status;
    private UUID userId;
    private TariffType type;
}
