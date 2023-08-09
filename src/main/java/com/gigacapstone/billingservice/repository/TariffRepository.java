package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.model.VoicePackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<TariffPlan, Integer> {
    @Query("SELECT bp FROM BundlePackage bp")
    List<BundlePackage> findBundlePackages();

    @Query("SELECT vp FROM VoicePackage vp")
    List<VoicePackage> findVoicePackages();

    @Query("SELECT vp FROM VoicePackage vp WHERE LOWER(vp.name) LIKE  %:packageName%")
    Page<VoicePackage> searchVoicePackagesIgnoreCase(String packageName, Pageable pageable);

    @Query("SELECT bp FROM BundlePackage bp WHERE LOWER(bp.name) LIKE  %:packageName%")
    Page<BundlePackage> searchBundlePackagesIgnoreCase(String packageName, Pageable pageable);
}
