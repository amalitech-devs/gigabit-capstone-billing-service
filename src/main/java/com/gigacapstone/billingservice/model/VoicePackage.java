package com.gigacapstone.billingservice.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
    private CallTime callTime;
}
