package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.repository.TariffRepository;
import com.gigacapstone.billingservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService{

    private final TariffRepository tariffRepository;
    private final Mapper objectMapper;
    @Override
    public VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage) {
        if(voicePackage == null){
            throw new IllegalArgumentException("Input voice package cannot be null");
        }

        if(doesPackageAlreadyExist(voicePackage.getName())){
            throw new EntityAlreadyExistException("Package Name already exists");
        }
        tariffRepository.save(objectMapper.mapVoicePackageDTOToVoicePackage(voicePackage));

        return voicePackage;
    }

    @Override
    public BundlePackageDTO createBundlePackage(BundlePackageDTO bundlePackage) {
        if(bundlePackage == null){
            throw new IllegalArgumentException("Input voice package cannot be null");
        }

        if(doesPackageAlreadyExist(bundlePackage.getName())){
            throw new EntityAlreadyExistException("Package Name already exists");
        }
        tariffRepository.save(objectMapper.mapBundlePackageDTOToBundlePackage(bundlePackage));

        return bundlePackage;
    }

    private boolean doesPackageAlreadyExist(String packageName){
        return tariffRepository.findTariffPlanByName(packageName).isPresent();
    }
}
