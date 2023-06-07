package com.gigacapstone.billingservice.servce.impl;

import com.gigacapstone.billingservice.config.exception.TariffPlanNotFoundException;
import com.gigacapstone.billingservice.dto.TariffPlanDto;
import com.gigacapstone.billingservice.dto.mappers.ModelMapper;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.repository.TariffPlanRepository;
import com.gigacapstone.billingservice.servce.TariffPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TariffPlanServiceImpl implements TariffPlanService {

    private final TariffPlanRepository tariffPlanRepository;
    private final ModelMapper tariffPlanMapper;

    @Autowired
    public TariffPlanServiceImpl(TariffPlanRepository tariffPlanRepository,
                                 ModelMapper tariffPlanMapper) {
        this.tariffPlanRepository = tariffPlanRepository;
        this.tariffPlanMapper = tariffPlanMapper;
    }

    @Override
    public TariffPlanDto createTariffPlan(TariffPlanDto tariffPlanDto) {
        TariffPlan tariffPlan = tariffPlanMapper.mapToEntityOrDto(tariffPlanDto,TariffPlan.class);
        TariffPlan savedTariffPlan = tariffPlanRepository.save(tariffPlan);
        return tariffPlanMapper.mapToEntityOrDto(savedTariffPlan, TariffPlanDto.class);
    }

    @Override
    public TariffPlanDto getTariffPlanById(UUID id) {
        TariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new TariffPlanNotFoundException("Tariff plan not found with ID: " + id));
        return tariffPlanMapper.mapToEntityOrDto(tariffPlan, TariffPlanDto.class);
    }

    @Override
    public Page<TariffPlan> getAllTariffPlans(Pageable pageable) {
        return tariffPlanRepository.findAll(pageable);
    }

    @Override
    public void deleteTariffPlan(UUID id) {
        TariffPlan tariffPlan = tariffPlanRepository.findById(id)
                .orElseThrow(() -> new TariffPlanNotFoundException("Tariff plan not found with ID: " + id));
        tariffPlanRepository.delete(tariffPlan);
    }

}