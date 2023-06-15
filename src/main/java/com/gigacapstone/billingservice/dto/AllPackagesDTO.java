package com.gigacapstone.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPackagesDTO {
    private List<VoicePackageDTO> voicePackages;
    private List<BundlePackageDTO> bundlePackages;
    private List<InternetPackageDTO> internetPackages;
}
