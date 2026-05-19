package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginRequestDTOTest {

    private static final String LOGIN = "testUser";
    private static final String PASSWORD = "testPassword";

    @Test
    public void test_getters_setters() {
        // GIVEN
        LoginRequestDTO dto = new LoginRequestDTO();

        // WHEN
        dto.setLogin(LOGIN);
        dto.setPassword(PASSWORD);

        // THEN
        assertThat(dto.getLogin()).isEqualTo(LOGIN);
        assertThat(dto.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        LoginRequestDTO dto1 = new LoginRequestDTO();
        dto1.setLogin(LOGIN);
        dto1.setPassword(PASSWORD);

        LoginRequestDTO dto2 = new LoginRequestDTO();
        dto2.setLogin(LOGIN);
        dto2.setPassword(PASSWORD);

        // THEN
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    public void test_not_equals_when_different_login() {
        // GIVEN
        LoginRequestDTO dto1 = new LoginRequestDTO();
        dto1.setLogin(LOGIN);
        dto1.setPassword(PASSWORD);

        LoginRequestDTO dto2 = new LoginRequestDTO();
        dto2.setLogin("otherUser");
        dto2.setPassword(PASSWORD);

        // THEN
        assertThat(dto1).isNotEqualTo(dto2);
    }
}