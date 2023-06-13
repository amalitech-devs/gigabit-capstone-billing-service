package com.gigacapstone.billingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final ObjectMapper mapper;

    @Override
    public VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage) {
        if (voicePackage == null) {
            throw new IllegalArgumentException("Input voice package cannot be null");
        }

        if (doesPackageAlreadyExist(voicePackage.getName())) {
            throw new EntityAlreadyExistException("Package Name already exists");
        }
        voicePackage.setIsEnabled(false);
        tariffRepository.save(mapper.convertValue(voicePackage, VoicePackage.class));

        return voicePackage;
    }

    @Override
    public BundlePackageDTO createBundlePackage(BundlePackageDTO bundlePackage) {
        if (bundlePackage == null) {
            throw new IllegalArgumentException("Input voice package cannot be null");
        }

        if (doesPackageAlreadyExist(bundlePackage.getName())) {
            throw new EntityAlreadyExistException("Package Name already exists");
        }
        bundlePackage.setIsEnabled(false);
        tariffRepository.save(mapper.convertValue(bundlePackage, BundlePackage.class));

        return bundlePackage;
    }

    @Override
    public Page<BundlePackageDTO> listAllBundlePackages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BundlePackage> bundlePackages = tariffRepository.findBundlePackages(pageable);
        return bundlePackages.map(bundle -> mapper.convertValue(bundle, BundlePackageDTO.class));
    }

    @Override
    public Page<VoicePackageDTO> listAllVoicePackages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VoicePackage> voicePackages = tariffRepository.findVoicePackages(pageable);
        return voicePackages.map(voice -> mapper.convertValue(voice, VoicePackageDTO.class));
    }

    private boolean doesPackageAlreadyExist(String packageName) {
        return tariffRepository.findTariffPlanByName(packageName).isPresent();
    }
}
