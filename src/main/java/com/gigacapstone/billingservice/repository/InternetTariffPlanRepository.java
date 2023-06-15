package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.InternetPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.UUID;

public interface InternetTariffPlanRepository extends CrudRepository<InternetPackage, UUID> ,
        PagingAndSortingRepository<InternetPackage, UUID> {
    Page<InternetPackage> findByTariffPlanNameContainingIgnoreCase(String tariffPlanName, Pageable pageable);
}
