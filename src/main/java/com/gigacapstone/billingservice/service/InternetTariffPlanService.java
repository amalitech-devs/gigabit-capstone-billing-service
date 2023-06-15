package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.model.InternetPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InternetTariffPlanService {
    InternetPackageDTO createTariffPlan(InternetPackageDTO tariffPlanDto);

    InternetPackageDTO getTariffPlanById(UUID id);
    InternetPackageDTO updateTariffPlanById(UUID id, InternetPackageDTO tariffPlanDto);

    Page<InternetPackage> getAllTariffPlans(Pageable pageable);

    Map<String, String> deleteTariffPlan(UUID id);
    Page<InternetPackage> searchByTariffPlanName(String tariffPlanName, Pageable pageable);
}
