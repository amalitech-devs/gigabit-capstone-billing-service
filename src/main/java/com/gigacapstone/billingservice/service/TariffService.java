package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;

import java.util.List;

public interface TariffService {

    VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage);

    BundlePackageDTO createBundlePackage(BundlePackageDTO bundlePackage);

    List<BundlePackageDTO> listAllBundlePackages(int page, int size);

    List<VoicePackageDTO> listAllVoicePackages(int page, int size);
}
