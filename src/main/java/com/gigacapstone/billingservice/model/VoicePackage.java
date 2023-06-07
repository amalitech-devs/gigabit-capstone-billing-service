package com.gigacapstone.billingservice.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VoicePackage extends TariffPlan{

    @Embedded
    @NotNull(message = "call time cannot be null")
    private CallTime callTime;
}
