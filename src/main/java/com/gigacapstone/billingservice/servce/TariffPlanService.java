package com.gigacapstone.billingservice.servce;

import com.gigacapstone.billingservice.dto.TariffPlanDto;
import com.gigacapstone.billingservice.model.TariffPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TariffPlanService {
    TariffPlanDto createTariffPlan(TariffPlanDto tariffPlanDto);

    TariffPlanDto getTariffPlanById(UUID id);

    Page<TariffPlan> getAllTariffPlans(Pageable pageable);

    void deleteTariffPlan(UUID id);
}
