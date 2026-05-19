package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentDTOTest {

    private static final UUID UUID_VALUE = UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601");
    private static final String FIRST_NAME = "testFirst";
    private static final String LAST_NAME = "testLast";
    private static final String EMAIL = "test@test.com";

    @Test
    public void test_getters_setters() {
        // GIVEN
        StudentDTO dto = new StudentDTO();
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        dto.setUuid(UUID_VALUE);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setEmail(EMAIL);
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);

        // THEN
        assertThat(dto.getUuid()).isEqualTo(UUID_VALUE);
        assertThat(dto.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(dto.getLastName()).isEqualTo(LAST_NAME);
        assertThat(dto.getEmail()).isEqualTo(EMAIL);
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void test_inherits_from_StudentListDTO() {
        // GIVEN / WHEN
        StudentDTO dto = new StudentDTO();

        // THEN
        assertThat(dto).isInstanceOf(StudentListDTO.class);
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();

        StudentDTO dto1 = new StudentDTO();
        dto1.setUuid(UUID_VALUE);
        dto1.setFirstName(FIRST_NAME);
        dto1.setLastName(LAST_NAME);
        dto1.setEmail(EMAIL);
        dto1.setCreatedAt(now);
        dto1.setUpdatedAt(now);

        StudentDTO dto2 = new StudentDTO();
        dto2.setUuid(UUID_VALUE);
        dto2.setFirstName(FIRST_NAME);
        dto2.setLastName(LAST_NAME);
        dto2.setEmail(EMAIL);
        dto2.setCreatedAt(now);
        dto2.setUpdatedAt(now);

        // THEN
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    public void test_not_equals_when_different_email() {
        // GIVEN
        StudentDTO dto1 = new StudentDTO();
        dto1.setUuid(UUID_VALUE);
        dto1.setEmail(EMAIL);

        StudentDTO dto2 = new StudentDTO();
        dto2.setUuid(UUID_VALUE);
        dto2.setEmail("other@test.com");

        // THEN
        assertThat(dto1).isNotEqualTo(dto2);
    }
}