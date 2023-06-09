package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tariff")
@RequiredArgsConstructor
public class TariffPlanController {

    private final TariffService tariffService;

    @PostMapping("/voice")
    @ResponseStatus(HttpStatus.CREATED)
    VoicePackageDTO createVoicePackage(@Valid @RequestBody VoicePackageDTO voicePackage){
        return tariffService.createVoicePackage(voicePackage);
    }

    @PostMapping("/bundle")
    @ResponseStatus(HttpStatus.CREATED)
    BundlePackageDTO createBundlePackage(@Valid @RequestBody BundlePackageDTO bundlePackageDTO){
        return tariffService.createBundlePackage(bundlePackageDTO);
    }
}
