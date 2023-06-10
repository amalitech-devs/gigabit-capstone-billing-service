package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.model.InternetPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InternetTariffPlanService {
    InternetPackageDTO createTariffPlan(InternetPackageDTO tariffPlanDto);

    InternetPackageDTO getTariffPlanById(UUID id);

    Page<InternetPackage> getAllTariffPlans(Pageable pageable);

    void deleteTariffPlan(UUID id);
}
