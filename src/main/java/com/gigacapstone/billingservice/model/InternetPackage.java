package com.gigacapstone.billingservice.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class InternetPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double downloadSpeed;
    private Double dataSize;
    private Double uploadSpeed;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private TariffPlan tariffPlan;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TariffPlan that = (TariffPlan) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public String toString() {
        return "InternetPackage{" +
                "id=" + id +
                ", tariffPlan=" + tariffPlan +
                ", downloadSpeed=" + downloadSpeed +
                ", dataSize=" + dataSize +
                ", uploadSpeed=" + uploadSpeed +
                '}';
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
