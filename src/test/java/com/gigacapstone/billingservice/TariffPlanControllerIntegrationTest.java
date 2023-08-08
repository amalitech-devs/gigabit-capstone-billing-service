package com.gigacapstone.billingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.enums.TimeUnit;
import com.gigacapstone.billingservice.model.CallTime;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TariffPlanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final int port = 8088;
    private static final String baseUrl = "http://localhost"
            .concat(":")
            .concat(String.valueOf(port))
            .concat("/api/v1/tariff");

    @Test
    void createVoicePackage() throws Exception {
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Test Name");
        voicePackageDTO.setPrice(2.0);
        voicePackageDTO.setIsEnabled(false);
        voicePackageDTO.setIsVatApplied(true);
        voicePackageDTO.setVatPercentage(20);
        voicePackageDTO.setExpirationRate(ExpirationRate.TWO_WEEKS);
        voicePackageDTO.setCallTime(new CallTime(TimeUnit.MINUTES, 20));

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl.concat("/voice"))
                .content(asJsonString(voicePackageDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    void createBundlePackage() throws Exception {
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Test Name");
        bundlePackageDTO.setPrice(12.0);
        bundlePackageDTO.setCallTime(new CallTime(TimeUnit.HOURS,2));
        bundlePackageDTO.setVatPercentage(15);
        bundlePackageDTO.setIsVatApplied(true);
        bundlePackageDTO.setExpirationRate(ExpirationRate.ONE_WEEK);
        bundlePackageDTO.setDataSize(100);
        bundlePackageDTO.setDownloadSpeed(12.0);
        bundlePackageDTO.setUploadSpeed(10.0);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl.concat("/bundle"))
                .content(asJsonString(bundlePackageDTO))
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    private static String asJsonString(final Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
