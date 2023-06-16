package com.gigacapstone.billingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.TariffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class TariffServiceImplTest {

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tariffService = new TariffServiceImpl(tariffRepository, mapper, null);
    }

    @Test
    void createVoicePackage_NullInput_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            tariffService.createVoicePackage(null);
        });
        verifyNoInteractions(tariffRepository);
    }

    @Test
    void createVoicePackage_ValidInput_ReturnsVoicePackageDTO() {
        // Arrange
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Voice Package");

        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName(voicePackageDTO.getName());

        when(mapper.convertValue(voicePackageDTO, VoicePackage.class))
                .thenReturn(voicePackage);

        when(tariffRepository.save(voicePackage)).thenReturn(voicePackage);


        // Act
        VoicePackageDTO result = tariffService.createVoicePackage(voicePackageDTO);

        // Assert
        assertNotNull(result);
        assertEquals(voicePackageDTO.getName(), result.getName());
        assertFalse(result.getIsEnabled());
        verify(tariffRepository, times(1)).save(voicePackage);
    }

    @Test
    void createVoicePackage_DuplicateName_ThrowsEntityAlreadyExistException() {
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Existing Package");

        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName(voicePackageDTO.getName());

        when(tariffRepository.findTariffPlanByName(voicePackageDTO.getName()))
                .thenReturn(Optional.of(voicePackage));

        //Assert that EntityAlreadyExists is thrown
        assertThrows(EntityAlreadyExistException.class, () -> {
            tariffService.createVoicePackage(voicePackageDTO);
        });
        verify(tariffRepository, never()).save(any(VoicePackage.class));
    }

    @Test
    void createBundlePackage_NullInput_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            tariffService.createBundlePackage(null);
        });
        verifyNoInteractions(tariffRepository);
    }

    @Test
    void createBundlePackage_ValidInput_ReturnsBundlePackageDTO() {
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Bundle Package");

        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName(bundlePackageDTO.getName());

        when(mapper.convertValue(bundlePackageDTO, BundlePackage.class)).thenReturn(bundlePackage);

        when(tariffRepository.save(bundlePackage)).thenReturn(bundlePackage);

        BundlePackageDTO result = tariffService.createBundlePackage(bundlePackageDTO);

        assertNotNull(result);
        assertEquals(result, bundlePackageDTO);

        verify(tariffRepository, times(1)).save(bundlePackage);
    }

    @Test
    void createBundlePackage_DuplicateName_ThrowsEntityAlreadyExistException() {
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Bundle Package");

        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName(bundlePackage.getName());

        when(tariffRepository.findTariffPlanByName(bundlePackageDTO.getName())).thenReturn(Optional.of(bundlePackage));

        assertThrows(EntityAlreadyExistException.class, () -> {
            tariffService.createBundlePackage(bundlePackageDTO);
        });

        verify(tariffRepository, times(1)).findTariffPlanByName(bundlePackageDTO.getName());
    }

    @Test
    void checkObjectMapper() {
        assertNotNull(mapper);
    }
}