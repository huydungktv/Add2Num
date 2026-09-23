package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "security.api-key=test-api-key",
        "addition.max-number-length=10"
})
@AutoConfigureMockMvc
class AdditionApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectRequestWithoutApiKey() throws Exception {
        mockMvc.perform(post("/api/v1/additions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstNumber\":\"12\",\"secondNumber\":\"3\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value("Unauthorized"));
    }

    @Test
    void shouldAddNumbersWithBearerApiKey() throws Exception {
        mockMvc.perform(post("/api/v1/additions")
                        .header("Authorization", "Bearer test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstNumber\":\"999\",\"secondNumber\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("1000"));
    }

    @Test
    void shouldReturnBadRequestForInvalidNumber() throws Exception {
        mockMvc.perform(post("/api/v1/additions")
                        .header("Authorization", "Bearer test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstNumber\":\"12a\",\"secondNumber\":\"3\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid request"));
    }

    @Test
    void shouldReturnBadRequestWhenNumberIsTooLong() throws Exception {
        mockMvc.perform(post("/api/v1/additions")
                        .header("Authorization", "Bearer test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstNumber\":\"12345678901\",\"secondNumber\":\"3\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("https://api.add2num.example/problems/number-too-long"));
    }

    @Test
    void shouldExposeOpenApiDocument() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.securitySchemes.bearerAuth.scheme").value("bearer"));
    }
}