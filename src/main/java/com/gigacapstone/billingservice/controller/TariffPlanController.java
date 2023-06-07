package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.model.VoicePackage;
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
    VoicePackageDTO createVoicePackage(@Valid @RequestBody VoicePackage voicePackage){
        return tariffService.createVoicePackage(voicePackage);
    }
}
