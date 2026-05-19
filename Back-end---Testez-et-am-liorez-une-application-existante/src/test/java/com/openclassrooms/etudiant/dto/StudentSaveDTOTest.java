package com.openclassrooms.etudiant.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentSaveDTOTest {

    private static final String FIRST_NAME = "testFirst";
    private static final String LAST_NAME = "testLast";
    private static final String EMAIL = "test@test.com";

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void test_getters_setters() {
        // GIVEN
        StudentSaveDTO dto = new StudentSaveDTO();

        // WHEN
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setEmail(EMAIL);

        // THEN
        assertThat(dto.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(dto.getLastName()).isEqualTo(LAST_NAME);
        assertThat(dto.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void test_valid_dto_has_no_violations() {
        // GIVEN
        StudentSaveDTO dto = new StudentSaveDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setEmail(EMAIL);

        // WHEN
        Set<ConstraintViolation<StudentSaveDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isEmpty();
    }

    @Test
    public void test_blank_firstName_has_violation() {
        // GIVEN
        StudentSaveDTO dto = new StudentSaveDTO();
        dto.setFirstName("");
        dto.setLastName(LAST_NAME);
        dto.setEmail(EMAIL);

        // WHEN
        Set<ConstraintViolation<StudentSaveDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_null_lastName_has_violation() {
        // GIVEN
        StudentSaveDTO dto = new StudentSaveDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(null);
        dto.setEmail(EMAIL);

        // WHEN
        Set<ConstraintViolation<StudentSaveDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_invalid_email_has_violation() {
        // GIVEN
        StudentSaveDTO dto = new StudentSaveDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setEmail("not-a-valid-email");

        // WHEN
        Set<ConstraintViolation<StudentSaveDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_null_email_has_violation() {
        // GIVEN
        StudentSaveDTO dto = new StudentSaveDTO();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setEmail(null);

        // WHEN
        Set<ConstraintViolation<StudentSaveDTO>> violations = validator.validate(dto);

        // THEN
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        StudentSaveDTO dto1 = new StudentSaveDTO();
        dto1.setFirstName(FIRST_NAME);
        dto1.setLastName(LAST_NAME);
        dto1.setEmail(EMAIL);

        StudentSaveDTO dto2 = new StudentSaveDTO();
        dto2.setFirstName(FIRST_NAME);
        dto2.setLastName(LAST_NAME);
        dto2.setEmail(EMAIL);

        // THEN
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}