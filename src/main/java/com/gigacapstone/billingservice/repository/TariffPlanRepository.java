package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.TariffPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface TariffPlanRepository extends CrudRepository<TariffPlan, UUID> ,
        PagingAndSortingRepository<TariffPlan, UUID> {
}
