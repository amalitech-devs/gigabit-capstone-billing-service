package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.AllPackagesDTO;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tariff")
@RequiredArgsConstructor
public class TariffPlanController {

    private final TariffService tariffService;

    @PostMapping("/voice")
    @ResponseStatus(HttpStatus.CREATED)
    VoicePackageDTO createVoicePackage(@Valid @RequestBody VoicePackageDTO voicePackage) {
        return tariffService.createVoicePackage(voicePackage);
    }

    @PostMapping("/bundle")
    @ResponseStatus(HttpStatus.CREATED)
    BundlePackageDTO createBundlePackage(@Valid @RequestBody BundlePackageDTO bundlePackageDTO) {
        return tariffService.createBundlePackage(bundlePackageDTO);
    }

    @GetMapping("/voice")
    @ResponseStatus(HttpStatus.OK)
    Page<VoicePackageDTO> getVoicePackages(Pageable pageable) {
        return tariffService.listAllVoicePackages(pageable);
    }

    @GetMapping("/bundle")
    @ResponseStatus(HttpStatus.OK)
    Page<BundlePackageDTO> getBundlePackages(Pageable pageable) {
        return tariffService.listAllBundlePackages(pageable);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    AllPackagesDTO getAllPackages(Pageable pageable) {
        return tariffService.listAllPackages(pageable);
    }
}
