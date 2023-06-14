package com.gigacapstone.billingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.model.InternetPackage;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import com.gigacapstone.billingservice.service.InternetTariffPlanService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.*;

@Service
public class InternetTariffPlanServiceImpl implements InternetTariffPlanService {

    private final InternetTariffPlanRepository tariffPlanRepository;
    private final ObjectMapper objectMapper;
    private  final String NOTFOUND = "Internet tariff package not found with ID: ";

    @Autowired
    public InternetTariffPlanServiceImpl(InternetTariffPlanRepository tariffPlanRepository,
                                         ObjectMapper objectMapper) {
        this.tariffPlanRepository = tariffPlanRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public InternetPackageDTO createTariffPlan(@NotNull(message = "Input Internet package cannot be null") InternetPackageDTO tariffPlanDto) {
        InternetPackage tariffPlan = objectMapper.convertValue(tariffPlanDto, InternetPackage.class);
        InternetPackage savedTariffPlan;
        try {
            savedTariffPlan = supplyAsync(()-> {
                tariffPlan.setCreatedAt(new Timestamp(new Date().getTime()));
                return  tariffPlanRepository
                        .save(tariffPlan);
            })
                    .join();

        }catch (Exception e){
            throw new EntityAlreadyExistException("Internet Package with Name:"+tariffPlanDto.getTariffPlan().getName()+" already exists",e.getCause());
        }
        return objectMapper.convertValue(savedTariffPlan, InternetPackageDTO.class);
    }

    @Override
    public InternetPackageDTO getTariffPlanById(UUID id) {
        InternetPackage tariffPlan = supplyAsync(()->
                        tariffPlanRepository
                                .findById(id))
                .join()
                .orElseThrow(() -> new NotFoundException( NOTFOUND + id));
        return objectMapper.convertValue(tariffPlan, InternetPackageDTO.class);
    }

    @Override
    public InternetPackageDTO updateTariffPlanById(UUID id, InternetPackageDTO tariffPlanDto) {
        InternetPackage tariffPlan = objectMapper.convertValue(tariffPlanDto, InternetPackage.class);
        InternetPackage updateInterPackage;
        try {
            InternetPackage internetPackage  = supplyAsync(() -> tariffPlanRepository.findById(id)
                    .orElseThrow(() ->
                            new NotFoundException(NOTFOUND + id)))
                    .join();
            tariffPlan.setId(internetPackage.getId());
            tariffPlan.setCreatedAt(internetPackage.getCreatedAt());
            tariffPlan.setUpdatedAt(new Timestamp(new Date().getTime()));
            updateInterPackage = supplyAsync(() -> tariffPlanRepository.save(tariffPlan)).join();
        }catch (Exception e){
            throw new EntityAlreadyExistException("Internet Package with Name:"+tariffPlanDto.getTariffPlan().getName()+" conflict with other internet package plan name.",e.getCause());
        }
        return objectMapper.convertValue(updateInterPackage, InternetPackageDTO.class);
    }

    @Override
    public Page<InternetPackage> getAllTariffPlans(Pageable pageable) {
        return supplyAsync(()->
                tariffPlanRepository
                        .findAll(pageable))
                .join();
    }

    @Override
    public Map<String, String> deleteTariffPlan(UUID id) {
        InternetPackage tariffPlan = supplyAsync(()
                        ->tariffPlanRepository
                        .findById(id))
                .join()
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id));
        CompletableFuture.runAsync(()->tariffPlanRepository.delete(tariffPlan));
        return Map.of("message", "Internet tariff package with ID: " + id+ "deleted successfully");
    }

}