package com.gigacapstone.billingservice;

import com.gigacapstone.billingservice.model.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<TariffPlan, Long> {
}
