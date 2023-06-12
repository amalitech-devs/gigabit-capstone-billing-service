package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.TariffRepository;
import com.gigacapstone.billingservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final Mapper objectMapper;

    @Override
    public VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage) {
        if (voicePackage == null) {
            throw new IllegalArgumentException("Input voice package cannot be null");
        }

        if (doesPackageAlreadyExist(voicePackage.getName())) {
            throw new EntityAlreadyExistException("Package Name already exists");
        }
        voicePackage.setIsEnabled(false);
        tariffRepository.save(objectMapper.mapVoicePackageDTOToVoicePackage(voicePackage));

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
        tariffRepository.save(objectMapper.mapBundlePackageDTOToBundlePackage(bundlePackage));

        return bundlePackage;
    }

    @Override
    public List<BundlePackageDTO> listAllBundlePackages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BundlePackage> bundlePackages = tariffRepository.findBundlePackages(pageable);
        return bundlePackages.getContent()
                .stream().map(objectMapper::mapBundlePackageToBundlePackageDTO)
                .toList();
    }

    @Override
    public List<VoicePackageDTO> listAllVoicePackages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VoicePackage> voicePackages = tariffRepository.findVoicePackages(pageable);
        return voicePackages.getContent()
                .stream()
                .map(objectMapper::mapVoicePackageToVoicePackageDTO)
                .toList();
    }

    private boolean doesPackageAlreadyExist(String packageName) {
        return tariffRepository.findTariffPlanByName(packageName).isPresent();
    }
}
