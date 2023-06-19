package com.gigacapstone.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private String tariffName;
    private LocalDate expiryDate;
    private String status;
}
