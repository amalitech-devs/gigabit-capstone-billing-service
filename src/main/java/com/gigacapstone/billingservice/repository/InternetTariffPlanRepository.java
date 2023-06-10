package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.InternetPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InternetTariffPlanRepository extends CrudRepository<InternetPackage, UUID> ,
        PagingAndSortingRepository<InternetPackage, UUID> {
}
