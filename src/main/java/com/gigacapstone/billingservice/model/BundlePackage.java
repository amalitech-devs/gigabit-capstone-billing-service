package com.gigacapstone.billingservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BundlePackage extends TariffPlan{

    private int dataSize;

    private int downloadSpeed;

    private int uploadSpeed;

    private CallTime callTime;
}
