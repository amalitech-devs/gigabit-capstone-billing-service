package com.gigacapstone.billingservice.model;

<<<<<<< HEAD
import com.gigacapstone.billingservice.constant.ExpiryDuration;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class TariffPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    private int dataSize;

    private double price;

    private boolean vat;

    private int downloadSpeed;

    private int uploadSpeed;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private ExpiryDuration expiry;

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
=======
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
>>>>>>> origin/dev
}
