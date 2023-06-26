package com.gigacapstone.billingservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.model.InternetPackage;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class InternetTariffPlanServiceImplTest {

    @Mock
    private InternetTariffPlanRepository tariffPlanRepository;

    @Mock
    private ObjectMapper objectMapper;

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

        when(objectMapper.convertValue(any(), eq(InternetPackage.class))).thenReturn(savedTariffPlan);
        when(tariffPlanRepository.save(any())).thenReturn(savedTariffPlan);
        when(objectMapper.convertValue(any(), eq(InternetPackageDTO.class))).thenReturn(expectedDto);

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
        when(objectMapper.convertValue(tariffPlan, InternetPackageDTO.class)).thenReturn(expectedDto);

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

    @Test
    public void testSearchByTariffPlanName_ValidName_ReturnsMatchingTariffPlans() {
        MockitoAnnotations.openMocks(this);

        // Mocking the repository
        String tariffPlanName = "Internet Package";
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        List<InternetPackage> mockedTariffPlans = Collections.singletonList(new InternetPackage());
        Page<InternetPackage> mockedPage = new PageImpl<>(mockedTariffPlans, pageable, 1);
        when(tariffPlanRepository.findByTariffPlanNameContainingIgnoreCase(tariffPlanName, pageable)).thenReturn(mockedPage);

        // Creating the service and invoking the method
        InternetTariffPlanServiceImpl tariffPlanService = new InternetTariffPlanServiceImpl(tariffPlanRepository, new ObjectMapper());
        Page<InternetPackage> result = tariffPlanService.searchByTariffPlanName(tariffPlanName, pageable);

        // Asserting the result
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(mockedTariffPlans, result.getContent());
    }

    @Test
    public void testSearchByTariffPlanName_NoMatchingName_ReturnsEmptyPage() {
        MockitoAnnotations.openMocks(this);

        // Mocking the repository
        String tariffPlanName = "Internet Package";
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<InternetPackage> mockedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(tariffPlanRepository.findByTariffPlanNameContainingIgnoreCase(tariffPlanName, pageable)).thenReturn(mockedPage);

        // Creating the service and invoking the method
        InternetTariffPlanServiceImpl tariffPlanService = new InternetTariffPlanServiceImpl(tariffPlanRepository, new ObjectMapper());
        Page<InternetPackage> result = tariffPlanService.searchByTariffPlanName(tariffPlanName, pageable);

        // Asserting the result
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
    }
}