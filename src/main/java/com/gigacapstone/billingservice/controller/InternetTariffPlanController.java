package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.InternetTariffPlanDto;
import com.gigacapstone.billingservice.model.InternetTariffPlan;
import com.gigacapstone.billingservice.service.InternetTariffPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 7200)
@RequestMapping("api/v1/tariff/internet")
public class InternetTariffPlanController {

    private final InternetTariffPlanService tariffPlanService;

    @Autowired
    public InternetTariffPlanController(InternetTariffPlanService tariffPlanService) {
        this.tariffPlanService = tariffPlanService;
    }

    @PostMapping
    public ResponseEntity<InternetTariffPlanDto> createTariffPlan(@RequestBody @Validated InternetTariffPlanDto tariffPlanDto) {
        InternetTariffPlanDto createdTariffPlan = tariffPlanService.createTariffPlan(tariffPlanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTariffPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternetTariffPlanDto> getTariffPlanById(@PathVariable("id") UUID id) {
        InternetTariffPlanDto tariffPlan = tariffPlanService.getTariffPlanById(id);
        return ResponseEntity.ok(tariffPlan);
    }

    @GetMapping
    public ResponseEntity<Page<InternetTariffPlan>> getAllTariffPlans(Pageable pageable) {
        Page<InternetTariffPlan> allTariffPlans = tariffPlanService.getAllTariffPlans(pageable);
        return ResponseEntity.ok(allTariffPlans);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTariffPlan(@PathVariable("id") UUID id) {
        tariffPlanService.deleteTariffPlan(id);
        return ResponseEntity.noContent().build();
    }
}
