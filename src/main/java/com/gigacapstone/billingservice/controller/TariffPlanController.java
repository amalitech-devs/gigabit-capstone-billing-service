package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.TariffPlanDto;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.servce.TariffPlanService;
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
@RequestMapping("/tariff-plans")
public class TariffPlanController {

    private final TariffPlanService tariffPlanService;

    @Autowired
    public TariffPlanController(TariffPlanService tariffPlanService) {
        this.tariffPlanService = tariffPlanService;
    }

    @PostMapping
    public ResponseEntity<TariffPlanDto> createTariffPlan(@RequestBody @Validated TariffPlanDto tariffPlanDto) {
        TariffPlanDto createdTariffPlan = tariffPlanService.createTariffPlan(tariffPlanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTariffPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffPlanDto> getTariffPlanById(@PathVariable("id") UUID id) {
        TariffPlanDto tariffPlan = tariffPlanService.getTariffPlanById(id);
        return ResponseEntity.ok(tariffPlan);
    }

    @GetMapping
    public ResponseEntity<Page<TariffPlan>> getAllTariffPlans(Pageable pageable) {
        Page<TariffPlan> allTariffPlans = tariffPlanService.getAllTariffPlans(pageable);
        return ResponseEntity.ok(allTariffPlans);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTariffPlan(@PathVariable("id") UUID id) {
        tariffPlanService.deleteTariffPlan(id);
        return ResponseEntity.noContent().build();
    }
}
