package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.AllPackagesDTO;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TariffService {

    VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage);

    BundlePackageDTO createBundlePackage(BundlePackageDTO bundlePackage);

    Page<BundlePackageDTO> listAllBundlePackages(Pageable pageable);

    Page<VoicePackageDTO> listAllVoicePackages(Pageable pageable);

    AllPackagesDTO listAllPackages(Pageable pageable);
}
