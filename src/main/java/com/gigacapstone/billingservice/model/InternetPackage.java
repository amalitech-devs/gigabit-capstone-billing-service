package com.gigacapstone.billingservice.model;

import com.gigacapstone.billingservice.enums.ExpirationRate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class InternetPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    private TariffPlan tariffPlan;
    private Double downloadSpeed;
    private Double dataSize;
    private Double uploadSpeed;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TariffPlan that = (TariffPlan) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
