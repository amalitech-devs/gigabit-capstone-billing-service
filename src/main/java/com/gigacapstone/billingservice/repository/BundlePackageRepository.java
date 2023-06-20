package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.BundlePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BundlePackageRepository extends JpaRepository<BundlePackage, Integer> {

    Optional<BundlePackage> findBundlePackageByName(String name);
}
