package com.gigacapstone.billingservice.utils;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public VoicePackage mapVoicePackageDTOToVoicePackage(VoicePackageDTO voicePackageDTO) {
        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName(voicePackageDTO.getName());
        voicePackage.setPrice(voicePackageDTO.getPrice());
        voicePackage.setIsEnabled(voicePackageDTO.getIsEnabled());
        voicePackage.setIsVatApplied(voicePackageDTO.getIsVatApplied());
        voicePackage.setVatPercentage(voicePackageDTO.getVatPercentage());
        voicePackage.setCallTime(voicePackageDTO.getCallTime());
        voicePackage.setExpirationRate(voicePackageDTO.getExpirationRate());

        return voicePackage;
    }

    public BundlePackage mapBundlePackageDTOToBundlePackage(BundlePackageDTO bundlePackageDTO) {
        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName(bundlePackageDTO.getName());
        bundlePackage.setPrice(bundlePackageDTO.getPrice());
        bundlePackage.setIsEnabled(bundlePackageDTO.getIsEnabled());
        bundlePackage.setIsVatApplied(bundlePackageDTO.getIsVatApplied());
        bundlePackage.setVatPercentage(bundlePackageDTO.getVatPercentage());
        bundlePackage.setCallTime(bundlePackageDTO.getCallTime());
        bundlePackage.setExpirationRate(bundlePackageDTO.getExpirationRate());
        bundlePackage.setDataSize(bundlePackageDTO.getDataSize());
        bundlePackage.setDownloadSpeed(bundlePackageDTO.getDownloadSpeed());
        bundlePackage.setUploadSpeed(bundlePackageDTO.getUploadSpeed());

        return bundlePackage;
    }

    public BundlePackageDTO mapBundlePackageToBundlePackageDTO(BundlePackage bundlePackage){
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName(bundlePackage.getName());
        bundlePackageDTO.setPrice(bundlePackage.getPrice());
        bundlePackageDTO.setIsEnabled(bundlePackage.getIsEnabled());
        bundlePackageDTO.setIsVatApplied(bundlePackage.getIsVatApplied());
        bundlePackageDTO.setVatPercentage(bundlePackage.getVatPercentage());
        bundlePackageDTO.setCallTime(bundlePackage.getCallTime());
        bundlePackageDTO.setExpirationRate(bundlePackage.getExpirationRate());
        bundlePackageDTO.setDataSize(bundlePackage.getDataSize());
        bundlePackageDTO.setDownloadSpeed(bundlePackage.getDownloadSpeed());
        bundlePackageDTO.setUploadSpeed(bundlePackage.getUploadSpeed());

        return bundlePackageDTO;
    }

    public VoicePackageDTO mapVoicePackageToVoicePackageDTO(VoicePackage voicePackage){
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName(voicePackage.getName());
        voicePackageDTO.setPrice(voicePackage.getPrice());
        voicePackageDTO.setIsEnabled(voicePackage.getIsEnabled());
        voicePackageDTO.setIsVatApplied(voicePackage.getIsVatApplied());
        voicePackageDTO.setVatPercentage(voicePackage.getVatPercentage());
        voicePackageDTO.setCallTime(voicePackage.getCallTime());
        voicePackageDTO.setExpirationRate(voicePackage.getExpirationRate());

        return voicePackageDTO;
    }
}
