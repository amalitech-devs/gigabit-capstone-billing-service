package com.gigacapstone.billingservice.service.impl;

import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.dto.InternetTariffPlanDto;
import com.gigacapstone.billingservice.dto.mappers.ModelMapper;
import com.gigacapstone.billingservice.model.InternetTariffPlan;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import com.gigacapstone.billingservice.service.InternetTariffPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public InternetTariffPlanDto createTariffPlan(InternetTariffPlanDto tariffPlanDto) {
        InternetTariffPlan tariffPlan = tariffPlanMapper.mapToEntityOrDto(tariffPlanDto, InternetTariffPlan.class);
        InternetTariffPlan savedTariffPlan = tariffPlanRepository.save(tariffPlan);
        return tariffPlanMapper.mapToEntityOrDto(savedTariffPlan, InternetTariffPlanDto.class);
    }

    @Override
    public InternetTariffPlanDto getTariffPlanById(UUID id) {
        InternetTariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Internet tariff plan not found with ID: " + id));
        return tariffPlanMapper.mapToEntityOrDto(tariffPlan, InternetTariffPlanDto.class);
    }

    @Override
    public Page<InternetTariffPlan> getAllTariffPlans(Pageable pageable) {
        return tariffPlanRepository.findAll(pageable);
    }

    @Override
    public void deleteTariffPlan(UUID id) {
        InternetTariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Internet tariff plan not found with ID: " + id));
        tariffPlanRepository.delete(tariffPlan);
    }

}