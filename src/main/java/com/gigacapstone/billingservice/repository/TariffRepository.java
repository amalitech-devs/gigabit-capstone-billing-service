package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.model.VoicePackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<TariffPlan, Long> {

    Optional<TariffPlan> findTariffPlanByName(String name);

    @Query("SELECT bp FROM BundlePackage bp")
    Page<BundlePackage> findAllBundlePackages(Pageable pageable);

    @Query("SELECT vp FROM VoicePackage vp")
    Page<VoicePackage> findAllVoicePackages(Pageable pageable);
}
