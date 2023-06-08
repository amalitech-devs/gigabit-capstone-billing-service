package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.TariffRepository;
import com.gigacapstone.billingservice.utils.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class TariffServiceImplTest {

    @Autowired
    private TariffServiceImpl tariffService;

    @MockBean
    private TariffRepository tariffRepository;

    @MockBean
    private Mapper objectMapper;

    @Test
    void testCreateVoicePackage_SuccessfulCreation() {
        // Create a sample VoicePackage object
        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName("Sample Package");
        voicePackage.setPrice(1);

        // Mock the behavior of the repository
        when(tariffRepository.findTariffPlanByName(voicePackage.getName()))
                .thenReturn(Optional.empty());

        when(tariffRepository.save(voicePackage)).thenReturn(voicePackage);

        // Mock the behavior of the mapper
        VoicePackageDTO expectedDto = new VoicePackageDTO("Sample Package", 1);
        when(objectMapper.mapVoicePackageToVoicePackageDTO(voicePackage))
                .thenReturn(expectedDto);

        // Call the service method
        VoicePackageDTO resultDto = tariffService.createVoicePackage(voicePackage);

        // Verify the repository method calls
        verify(tariffRepository, times(1)).findTariffPlanByName(voicePackage.getName());
        verify(tariffRepository, times(1)).save(voicePackage);

        // Verify the mapper method calls
        verify(objectMapper, times(1)).mapVoicePackageToVoicePackageDTO(voicePackage);

        // Assert the result
        assertNotNull(resultDto);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void testCreateVoicePackage_AlreadyExists() {
        // Create a sample VoicePackage object
        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName("Sample Package");

        // Mock the behavior of the repository to return a non-empty optional
        when(tariffRepository.findTariffPlanByName(voicePackage.getName()))
                .thenReturn(Optional.of(voicePackage));

        // Call the service method and assert that the expected exception is thrown
        assertThrows(EntityAlreadyExistException.class, () -> tariffService.createVoicePackage(voicePackage));

        // Verify the repository method calls
        verify(tariffRepository, times(1)).findTariffPlanByName(voicePackage.getName());
        verify(tariffRepository, never()).save(voicePackage);

        // Verify the mapper method calls
        verify(objectMapper, never()).mapVoicePackageToVoicePackageDTO(any());

    }

    @Test
    void testCreateVoicePackage_NullInput() {
        // Call the service method with null input and assert that the expected exception is thrown
        assertThrows(IllegalArgumentException.class, () -> tariffService.createVoicePackage(null));

        // Verify that the repository and mapper methods are not called
        verify(tariffRepository, never()).findTariffPlanByName(any());
        verify(tariffRepository, never()).save(any());
        verify(objectMapper, never()).mapVoicePackageToVoicePackageDTO(any());
    }
}