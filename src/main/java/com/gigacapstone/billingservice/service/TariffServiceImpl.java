package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.model.VoicePackage;
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
    public VoicePackageDTO createVoicePackage(VoicePackage voicePackage) {
        if(doesPackageAlreadyExist(voicePackage)){
            throw new EntityAlreadyExistException("Package Name already exists");
        }
        VoicePackage savedVoicePackage = tariffRepository.save(voicePackage);

        return objectMapper.mapVoicePackageToVoicePackageDTO(savedVoicePackage);
    }
    private boolean doesPackageAlreadyExist(TariffPlan tariffPlan){
        return tariffRepository.findTariffPlanByName(tariffPlan.getName()).isPresent();
    }
}
