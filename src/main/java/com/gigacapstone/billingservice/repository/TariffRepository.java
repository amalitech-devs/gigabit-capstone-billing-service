package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<TariffPlan, Long> {

    Optional<TariffPlan> findTariffPlanByName(String name);
}
