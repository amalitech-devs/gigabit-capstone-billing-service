package com.gigacapstone.billingservice.service.impl;

import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.dto.mappers.ModelMapper;
import com.gigacapstone.billingservice.model.InternetPackage;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import com.gigacapstone.billingservice.service.InternetTariffPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class InternetTariffPlanServiceImpl implements InternetTariffPlanService {

    private final InternetTariffPlanRepository tariffPlanRepository;
    private final ModelMapper tariffPlanMapper;

    @Autowired
    public InternetTariffPlanServiceImpl(InternetTariffPlanRepository tariffPlanRepository,
                                         ModelMapper tariffPlanMapper) {
        this.tariffPlanRepository = tariffPlanRepository;
        this.tariffPlanMapper = tariffPlanMapper;
    }

    @Override
    public InternetPackageDTO createTariffPlan(InternetPackageDTO tariffPlanDto) {
        InternetPackage tariffPlan = tariffPlanMapper.mapToEntityOrDto(tariffPlanDto, InternetPackage.class);
        InternetPackage savedTariffPlan = CompletableFuture.supplyAsync(()->
                tariffPlanRepository
                        .save(tariffPlan))
                .join();
        return tariffPlanMapper.mapToEntityOrDto(savedTariffPlan, InternetPackageDTO.class);
    }

    @Override
    public InternetPackageDTO getTariffPlanById(UUID id) {
        InternetPackage tariffPlan = CompletableFuture.supplyAsync(()->
                        tariffPlanRepository
                                .findById(id))
                .join()
                .orElseThrow(() -> new NotFoundException("Internet tariff plan not found with ID: " + id));
        return tariffPlanMapper.mapToEntityOrDto(tariffPlan, InternetPackageDTO.class);
    }

    @Override
    public Page<InternetPackage> getAllTariffPlans(Pageable pageable) {
        return CompletableFuture.supplyAsync(()->
                tariffPlanRepository
                        .findAll(pageable))
                .join();
    }

    @Override
    public void deleteTariffPlan(UUID id) {
        InternetPackage tariffPlan = CompletableFuture.supplyAsync(()
                        ->tariffPlanRepository
                        .findById(id))
                .join()
                .orElseThrow(() -> new NotFoundException("Internet tariff plan not found with ID: " + id));
        tariffPlanRepository.delete(tariffPlan);
    }

}