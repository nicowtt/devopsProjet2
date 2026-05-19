package com.openclassrooms.etudiant.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterDTOTest {

    private static final String FIRST_NAME = "testFirst";
    private static final String LAST_NAME = "testLast";
    private static final String LOGIN = "testLogin";
    private static final String PASSWORD = "test";

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void test_getters_setters() {
        // GIVEN
        RegisterDTO dto = new RegisterDTO();

        // WHEN
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLogin(LOGIN);
        dto.setPassword(PASSWORD);

        // THEN
        assertThat(dto.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(dto.getLastName()).isEqualTo(LAST_NAME);
        assertThat(dto.getLogin()).isEqualTo(LOGIN);
        assertThat(dto.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void test_valid_dto_has_no_violations() {
        // GIVEN
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLogin(LOGIN);
        dto.setPassword(PASSWORD);

        // WHEN
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isEmpty();
    }

    @Test
    public void test_blank_firstName_has_violation() {
        // GIVEN
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("");
        dto.setLastName(LAST_NAME);
        dto.setLogin(LOGIN);
        dto.setPassword(PASSWORD);

        // WHEN
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_null_lastName_has_violation() {
        // GIVEN
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(null);
        dto.setLogin(LOGIN);
        dto.setPassword(PASSWORD);

        // WHEN
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_blank_login_has_violation() {
        // GIVEN
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLogin("   ");
        dto.setPassword(PASSWORD);

        // WHEN
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_null_password_has_violation() {
        // GIVEN
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setLogin(LOGIN);
        dto.setPassword(null);

        // WHEN
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        RegisterDTO dto1 = new RegisterDTO();
        dto1.setFirstName(FIRST_NAME);
        dto1.setLastName(LAST_NAME);
        dto1.setLogin(LOGIN);
        dto1.setPassword(PASSWORD);

        RegisterDTO dto2 = new RegisterDTO();
        dto2.setFirstName(FIRST_NAME);
        dto2.setLastName(LAST_NAME);
        dto2.setLogin(LOGIN);
        dto2.setPassword(PASSWORD);

        // THEN
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}