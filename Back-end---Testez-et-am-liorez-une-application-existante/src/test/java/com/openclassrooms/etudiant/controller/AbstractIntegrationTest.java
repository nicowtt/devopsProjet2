package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.dto.LoginRequestDTO;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class AbstractIntegrationTest {

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33");

    static {
        mySQLContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    protected String getToken(MockMvc mockMvc, ObjectMapper objectMapper, String login, String password) throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin(login);
        loginRequestDTO.setPassword(password);

        return mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .content(objectMapper.writeValueAsString(loginRequestDTO))
                .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

}
