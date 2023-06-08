package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.InternetTariffPlanDto;
import com.gigacapstone.billingservice.model.InternetTariffPlan;
import com.gigacapstone.billingservice.model.TariffPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InternetTariffPlanService {
    InternetTariffPlanDto createTariffPlan(InternetTariffPlanDto tariffPlanDto);

    InternetTariffPlanDto getTariffPlanById(UUID id);

    Page<InternetTariffPlan> getAllTariffPlans(Pageable pageable);

    void deleteTariffPlan(UUID id);
}
