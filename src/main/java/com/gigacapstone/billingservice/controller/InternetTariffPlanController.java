package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.model.InternetPackage;
import com.gigacapstone.billingservice.service.InternetTariffPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ResponseEntity<InternetPackageDTO> createTariffPlan(@RequestBody @Validated InternetPackageDTO tariffPlanDto) {
        InternetPackageDTO createdTariffPlan = tariffPlanService.createTariffPlan(tariffPlanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTariffPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternetPackageDTO> updateTariffPlan(@PathVariable("id") UUID id, @RequestBody @Validated InternetPackageDTO tariffPlanDto) {
        InternetPackageDTO createdTariffPlan = tariffPlanService.updateTariffPlanById(id,tariffPlanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTariffPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternetPackageDTO> getTariffPlanById(@PathVariable("id") UUID id) {
        InternetPackageDTO tariffPlan = tariffPlanService.getTariffPlanById(id);
        return ResponseEntity.ok(tariffPlan);
    }

    @GetMapping
    public ResponseEntity<Page<InternetPackage>> getAllTariffPlans(Pageable pageable) {
        Page<InternetPackage> allTariffPlans = tariffPlanService.getAllTariffPlans(pageable);
        return ResponseEntity.ok(allTariffPlans);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteTariffPlan(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(tariffPlanService.deleteTariffPlan(id));
    }
}
