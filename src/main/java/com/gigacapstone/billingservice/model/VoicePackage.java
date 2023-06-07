package com.gigacapstone.billingservice.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity

public class VoicePackage extends TariffPlan{

    @Embedded
    private CallTime callTime;
}
