package com.gigacapstone.billingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.BundlePackageRepository;
import com.gigacapstone.billingservice.repository.TariffRepository;
import com.gigacapstone.billingservice.repository.VoicePackageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TariffServiceImplTest {

    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private VoicePackageRepository voicePackageRepository;

    @Mock
    private BundlePackageRepository bundlePackageRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVoicePackage_WithValidPackage_ShouldReturnCreatedPackage() {
        // Arrange
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Package A");
        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName("Package A");

        when(voicePackageRepository.findVoicePackageByName(anyString())).thenReturn(Optional.empty());
        when(tariffRepository.save(any(VoicePackage.class))).thenReturn(voicePackage);
        when(mapper.convertValue(any(VoicePackageDTO.class), eq(VoicePackage.class))).thenReturn(voicePackage);

        // Act
        VoicePackageDTO createdPackage = tariffService.createVoicePackage(voicePackageDTO);

        // Assert
        Assertions.assertEquals(voicePackageDTO.getName(), createdPackage.getName());
        verify(voicePackageRepository, times(1)).findVoicePackageByName(anyString());
        verify(tariffRepository, times(1)).save(any(VoicePackage.class));
    }

    @Test
    void createVoicePackage_WithExistingPackage_ShouldThrowEntityAlreadyExistException() {
        // Arrange
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Package A");
        VoicePackage existingPackage = new VoicePackage();
        existingPackage.setName("Package A");

        when(voicePackageRepository.findVoicePackageByName(anyString())).thenReturn(Optional.of(existingPackage));

        // Act and Assert
        assertThrows(EntityAlreadyExistException.class, () -> {
            tariffService.createVoicePackage(voicePackageDTO);
        });
        verify(voicePackageRepository, times(1)).findVoicePackageByName(anyString());
        verify(tariffRepository, never()).save(any(VoicePackage.class));
    }

    @Test
    void createBundlePackage_WithValidPackage_ShouldReturnCreatedPackage() {
        // Arrange
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Package X");
        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName("Package X");

        when(bundlePackageRepository.findBundlePackageByName(anyString())).thenReturn(Optional.empty());
        when(tariffRepository.save(any(BundlePackage.class))).thenReturn(bundlePackage);
        when(mapper.convertValue(any(BundlePackageDTO.class), eq(BundlePackage.class))).thenReturn(bundlePackage);

        // Act
        BundlePackageDTO createdPackage = tariffService.createBundlePackage(bundlePackageDTO);

        // Assert
        Assertions.assertEquals(bundlePackageDTO.getName(), createdPackage.getName());
        verify(bundlePackageRepository, times(1)).findBundlePackageByName(anyString());
        verify(tariffRepository, times(1)).save(any(BundlePackage.class));
    }

    @Test
    void createBundlePackage_WithExistingPackage_ShouldThrowEntityAlreadyExistException() {
        // Arrange
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Package X");
        BundlePackage existingPackage = new BundlePackage();
        existingPackage.setName("Package X");

        when(bundlePackageRepository.findBundlePackageByName(anyString())).thenReturn(Optional.of(existingPackage));

        // Act and Assert
        assertThrows(EntityAlreadyExistException.class, () -> {
            tariffService.createBundlePackage(bundlePackageDTO);
        });
        verify(bundlePackageRepository, times(1)).findBundlePackageByName(anyString());
        verify(tariffRepository, never()).save(any(BundlePackage.class));
    }
}
