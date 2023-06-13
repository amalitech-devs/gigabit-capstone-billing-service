package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import org.springframework.data.domain.Page;

public interface TariffService {

    VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage);

    BundlePackageDTO createBundlePackage(BundlePackageDTO bundlePackage);

    Page<BundlePackageDTO> listAllBundlePackages(int page, int size);

    Page<VoicePackageDTO> listAllVoicePackages(int page, int size);
}
