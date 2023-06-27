package com.gigacapstone.billingservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gigacapstone.billingservice.enums.TariffType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String tariffName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    private UUID userId;

    private String status;

    @Enumerated(EnumType.STRING)
    private TariffType type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
