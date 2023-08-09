package com.gigacapstone.billingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.enums.TimeUnit;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.CallTime;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.BundlePackageRepository;
import com.gigacapstone.billingservice.repository.VoicePackageRepository;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TariffPlanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VoicePackageRepository voicePackageRepository;

    @Autowired
    private BundlePackageRepository bundlePackageRepository;

    @LocalServerPort
    private static int port;
    private static final String baseUrl = "http://localhost"
            .concat(":")
            .concat(String.valueOf(port))
            .concat("/api/v1/tariff");


    @BeforeEach
    void setup(){
        VoicePackage voicePackage1 = new VoicePackage();
        voicePackage1.setName("Test Name");
        voicePackage1.setPrice(2.0);
        voicePackage1.setIsEnabled(false);
        voicePackage1.setIsVatApplied(true);
        voicePackage1.setVatPercentage(20);
        voicePackage1.setExpirationRate(ExpirationRate.TWO_WEEKS);
        voicePackage1.setCallTime(new CallTime(TimeUnit.MINUTES, 20));

        VoicePackage voicePackage2 = new VoicePackage();
        voicePackage2.setName("Test Name 2");
        voicePackage2.setPrice(2.0);
        voicePackage2.setIsEnabled(false);
        voicePackage2.setIsVatApplied(true);
        voicePackage2.setVatPercentage(20);
        voicePackage2.setExpirationRate(ExpirationRate.TWO_WEEKS);
        voicePackage2.setCallTime(new CallTime(TimeUnit.MINUTES, 20));

        voicePackageRepository.saveAll(List.of(voicePackage1, voicePackage2));

        //Bundle Setup
        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName("Test Bundle");
        bundlePackage.setPrice(12.0);
        bundlePackage.setCallTime(new CallTime(TimeUnit.HOURS,2));
        bundlePackage.setVatPercentage(15);
        bundlePackage.setIsVatApplied(true);
        bundlePackage.setExpirationRate(ExpirationRate.ONE_WEEK);
        bundlePackage.setDataSize(100);
        bundlePackage.setDownloadSpeed(12.0);
        bundlePackage.setUploadSpeed(10.0);

        bundlePackageRepository.save(bundlePackage);
    }

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

    @Test
    void getVoicePackages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl.concat("/voice"))
                .accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    private static String asJsonString(final Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
