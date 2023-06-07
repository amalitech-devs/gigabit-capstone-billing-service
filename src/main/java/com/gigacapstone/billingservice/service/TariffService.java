package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.model.VoicePackage;

public interface TariffService {

    VoicePackageDTO createVoicePackage(VoicePackage voicePackage);
}
