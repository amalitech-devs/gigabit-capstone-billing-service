package com.gigacapstone.billingservice;

import com.gigacapstone.billingservice.controller.TariffPlanController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TariffPlanController.class)
@AutoConfigureMockMvc
public class TariffPlanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final int port = 8088;
    private static final String baseUrl = "http://localhost"
            .concat(":")
            .concat(String.valueOf(port))
            .concat("/api/v1/tariff");

}
