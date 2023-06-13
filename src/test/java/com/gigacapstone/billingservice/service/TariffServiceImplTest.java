package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class TariffServiceImplTest {

    @Autowired
    private TariffServiceImpl tariffService;

    @MockBean
    private TariffRepository tariffRepository;


    @Test
    void testCreateVoicePackage_NullInput() {
        // Call the service method with null input and assert that the expected exception is thrown
        assertThrows(IllegalArgumentException.class, () -> tariffService.createVoicePackage(null));

        // Verify that the repository and mapper methods are not called
        verify(tariffRepository, never()).findTariffPlanByName(any());
        verify(tariffRepository, never()).save(any());
    }
}