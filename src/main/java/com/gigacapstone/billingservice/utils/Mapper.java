package com.gigacapstone.billingservice.utils;

import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.model.VoicePackage;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public VoicePackageDTO mapVoicePackageToVoicePackageDTO(VoicePackage voicePackage){
        return new VoicePackageDTO(
                voicePackage.getName(),
                voicePackage.getPrice()
        );
    }
}
