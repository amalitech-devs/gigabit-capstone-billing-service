package com.gigacapstone.billingservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.dto.mappers.ModelMapper;
import com.gigacapstone.billingservice.model.InternetPackage;
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
        InternetPackageDTO inputDto = new InternetPackageDTO();
        InternetPackage savedTariffPlan = new InternetPackage();
        InternetPackageDTO expectedDto = new InternetPackageDTO();

        when(tariffPlanMapper.mapToEntityOrDto(any(), eq(InternetPackage.class))).thenReturn(savedTariffPlan);
        when(tariffPlanRepository.save(any())).thenReturn(savedTariffPlan);
        when(tariffPlanMapper.mapToEntityOrDto(any(), eq(InternetPackageDTO.class))).thenReturn(expectedDto);

        // Act
        InternetPackageDTO resultDto = tariffPlanService.createTariffPlan(inputDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void getTariffPlanById_ExistingId_Success() {
        // Arrange
        UUID id = UUID.randomUUID();
        InternetPackage tariffPlan = new InternetPackage();
        InternetPackageDTO expectedDto = new InternetPackageDTO();

        when(tariffPlanRepository.findById(id)).thenReturn(Optional.of(tariffPlan));
        when(tariffPlanMapper.mapToEntityOrDto(tariffPlan, InternetPackageDTO.class)).thenReturn(expectedDto);

        // Act
        InternetPackageDTO resultDto = tariffPlanService.getTariffPlanById(id);

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
        Page<InternetPackage> expectedPage = Page.empty();
        Pageable pageable = Pageable.unpaged();

        when(tariffPlanRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<InternetPackage> resultPage = tariffPlanService.getAllTariffPlans(pageable);

        // Assert
        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
    }

    @Test
    void deleteTariffPlan_ExistingId_Success() {
        // Arrange
        UUID id = UUID.randomUUID();
        InternetPackage tariffPlan = new InternetPackage();

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