package com.gigacapstone.billingservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BundlePackage extends TariffPlan{

    private double dataSize;

    private double downloadSpeed;

    private double uploadSpeed;

    private CallTime callTime;
}
