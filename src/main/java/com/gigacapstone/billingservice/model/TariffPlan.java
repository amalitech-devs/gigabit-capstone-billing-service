package com.gigacapstone.billingservice.model;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class TariffPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double price;
    private boolean isEnabled;
    private boolean isVatApplied;
    private int vatPercentage;

    @Enumerated(EnumType.STRING)
    private ExpirationRate expirationRate;
}
