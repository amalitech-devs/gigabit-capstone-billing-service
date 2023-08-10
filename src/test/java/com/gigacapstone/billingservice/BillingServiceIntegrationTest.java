package com.gigacapstone.billingservice;

import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.enums.TimeUnit;
import com.gigacapstone.billingservice.model.CallTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BillingServiceIntegrationTest {

    @LocalServerPort //From within the application context get the port the application is running on
    private int port;

    private String baseUrl = "http://localhost";

    private static WebClient webClient;

    @BeforeAll
    static void init(){
        webClient = WebClient.builder()
                .build();
    }

    @BeforeEach
    void setUp(){
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/api/v1/tariff");
    }

    @Test
    void testCreateVoicePackage(){
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Test Name");
        voicePackageDTO.setPrice(2.0);
        voicePackageDTO.setIsEnabled(false);
        voicePackageDTO.setIsVatApplied(true);
        voicePackageDTO.setVatPercentage(20);
        voicePackageDTO.setExpirationRate(ExpirationRate.TWO_WEEKS);
        voicePackageDTO.setCallTime(new CallTime(TimeUnit.MINUTES, 20));

        //Add the body this is a post request
        VoicePackageDTO voicePackage = webClient.post()
                .uri(baseUrl.concat("/voice"))
                .body(BodyInserters.fromValue(voicePackageDTO))
                .retrieve()
                .bodyToMono(VoicePackageDTO.class)
                .block();

        assertNotNull(voicePackage);
        assertEquals(voicePackageDTO.getName(), voicePackage.getName());
    }

    @Test
    void testCreateBundlePackage(){
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

        BundlePackageDTO response = webClient.post()
                .uri(baseUrl.concat("/bundle"))
                .body(BodyInserters.fromValue(bundlePackageDTO))
                .retrieve()
                .bodyToMono(BundlePackageDTO.class)
                .block();

        assertNotNull(response);
        assertEquals(bundlePackageDTO.getName(), response.getName());
        
    }
}
