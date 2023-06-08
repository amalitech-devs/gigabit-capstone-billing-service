package com.gigacapstone.billingservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.dto.InternetTariffPlanDto;
import com.gigacapstone.billingservice.dto.mappers.ModelMapper;
import com.gigacapstone.billingservice.model.InternetTariffPlan;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class InternetTariffPlanServiceImplTest {

    @Mock
    private InternetTariffPlanRepository tariffPlanRepository;

    @Mock
    private ModelMapper tariffPlanMapper;

    @InjectMocks
    private InternetTariffPlanServiceImpl tariffPlanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTariffPlan_Success() {
        // Arrange
        InternetTariffPlanDto inputDto = new InternetTariffPlanDto();
        InternetTariffPlan savedTariffPlan = new InternetTariffPlan();
        InternetTariffPlanDto expectedDto = new InternetTariffPlanDto();

        when(tariffPlanMapper.mapToEntityOrDto(any(), eq(InternetTariffPlan.class))).thenReturn(savedTariffPlan);
        when(tariffPlanRepository.save(any())).thenReturn(savedTariffPlan);
        when(tariffPlanMapper.mapToEntityOrDto(any(), eq(InternetTariffPlanDto.class))).thenReturn(expectedDto);

        // Act
        InternetTariffPlanDto resultDto = tariffPlanService.createTariffPlan(inputDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void getTariffPlanById_ExistingId_Success() {
        // Arrange
        UUID id = UUID.randomUUID();
        InternetTariffPlan tariffPlan = new InternetTariffPlan();
        InternetTariffPlanDto expectedDto = new InternetTariffPlanDto();

        when(tariffPlanRepository.findById(id)).thenReturn(Optional.of(tariffPlan));
        when(tariffPlanMapper.mapToEntityOrDto(tariffPlan, InternetTariffPlanDto.class)).thenReturn(expectedDto);

        // Act
        InternetTariffPlanDto resultDto = tariffPlanService.getTariffPlanById(id);

        // Assert
        assertNotNull(resultDto);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void getTariffPlanById_NonExistingId_ThrowsNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(tariffPlanRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> tariffPlanService.getTariffPlanById(id));
    }

    @Test
    void getAllTariffPlans_Success() {
        // Arrange
        Page<InternetTariffPlan> expectedPage = Page.empty();
        Pageable pageable = Pageable.unpaged();

        when(tariffPlanRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<InternetTariffPlan> resultPage = tariffPlanService.getAllTariffPlans(pageable);

        // Assert
        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
    }

    @Test
    void deleteTariffPlan_ExistingId_Success() {
        // Arrange
        UUID id = UUID.randomUUID();
        InternetTariffPlan tariffPlan = new InternetTariffPlan();

        when(tariffPlanRepository.findById(id)).thenReturn(Optional.of(tariffPlan));

        // Act & Assert
        assertDoesNotThrow(() -> tariffPlanService.deleteTariffPlan(id));
    }

    @Test
    void deleteTariffPlan_NonExistingId_ThrowsNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(tariffPlanRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> tariffPlanService.deleteTariffPlan(id));
    }
}