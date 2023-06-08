package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.InternetTariffPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InternetTariffPlanRepository extends CrudRepository<InternetTariffPlan, UUID> ,
        PagingAndSortingRepository<InternetTariffPlan, UUID> {
}
