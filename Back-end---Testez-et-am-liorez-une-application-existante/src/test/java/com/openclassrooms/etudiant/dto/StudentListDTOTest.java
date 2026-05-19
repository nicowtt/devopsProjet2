package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentListDTOTest {

    private static final UUID UUID_VALUE = UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601");
    private static final String FIRST_NAME = "testFirst";
    private static final String LAST_NAME = "testLast";

    @Test
    public void test_getters_setters() {
        // GIVEN
        StudentListDTO dto = new StudentListDTO();

        // WHEN
        dto.setUuid(UUID_VALUE);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);

        // THEN
        assertThat(dto.getUuid()).isEqualTo(UUID_VALUE);
        assertThat(dto.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(dto.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        StudentListDTO dto1 = new StudentListDTO();
        dto1.setUuid(UUID_VALUE);
        dto1.setFirstName(FIRST_NAME);
        dto1.setLastName(LAST_NAME);

        StudentListDTO dto2 = new StudentListDTO();
        dto2.setUuid(UUID_VALUE);
        dto2.setFirstName(FIRST_NAME);
        dto2.setLastName(LAST_NAME);

        // THEN
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    public void test_not_equals_when_different_uuid() {
        // GIVEN
        StudentListDTO dto1 = new StudentListDTO();
        dto1.setUuid(UUID_VALUE);
        dto1.setFirstName(FIRST_NAME);
        dto1.setLastName(LAST_NAME);

        StudentListDTO dto2 = new StudentListDTO();
        dto2.setUuid(UUID.randomUUID());
        dto2.setFirstName(FIRST_NAME);
        dto2.setLastName(LAST_NAME);

        // THEN
        assertThat(dto1).isNotEqualTo(dto2);
    }
}