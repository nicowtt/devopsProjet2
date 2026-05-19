package com.openclassrooms.etudiant.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    private static final String FIRST_NAME = "testFirst";
    private static final String LAST_NAME = "testLast";
    private static final String EMAIL = "test@test.com";

    @Test
    public void test_default_uuid_is_generated() {
        // GIVEN / WHEN
        Student student = new Student();

        // THEN
        assertThat(student.getUuid()).isNotNull();
    }

    @Test
    public void test_two_new_students_have_different_uuids() {
        // GIVEN / WHEN
        Student student1 = new Student();
        Student student2 = new Student();

        // THEN
        assertThat(student1.getUuid()).isNotEqualTo(student2.getUuid());
    }

    @Test
    public void test_getters_setters() {
        // GIVEN
        Student student = new Student();
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        student.setId(1L);
        student.setUuid(uuid);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);
        student.setCreated_at(now);
        student.setUpdated_at(now);

        // THEN
        assertThat(student.getId()).isEqualTo(1L);
        assertThat(student.getUuid()).isEqualTo(uuid);
        assertThat(student.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(student.getLastName()).isEqualTo(LAST_NAME);
        assertThat(student.getEmail()).isEqualTo(EMAIL);
        assertThat(student.getCreated_at()).isEqualTo(now);
        assertThat(student.getUpdated_at()).isEqualTo(now);
    }

    @Test
    public void test_all_args_constructor() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        Student student = new Student(1L, uuid, FIRST_NAME, LAST_NAME, EMAIL, now, now);

        // THEN
        assertThat(student.getId()).isEqualTo(1L);
        assertThat(student.getUuid()).isEqualTo(uuid);
        assertThat(student.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(student.getLastName()).isEqualTo(LAST_NAME);
        assertThat(student.getEmail()).isEqualTo(EMAIL);
        assertThat(student.getCreated_at()).isEqualTo(now);
        assertThat(student.getUpdated_at()).isEqualTo(now);
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        UUID uuid = UUID.randomUUID();

        Student student1 = new Student();
        student1.setId(1L);
        student1.setUuid(uuid);
        student1.setFirstName(FIRST_NAME);
        student1.setLastName(LAST_NAME);
        student1.setEmail(EMAIL);

        Student student2 = new Student();
        student2.setId(1L);
        student2.setUuid(uuid);
        student2.setFirstName(FIRST_NAME);
        student2.setLastName(LAST_NAME);
        student2.setEmail(EMAIL);

        // THEN
        assertThat(student1).isEqualTo(student2);
        assertThat(student1.hashCode()).isEqualTo(student2.hashCode());
    }

    @Test
    public void test_not_equals_when_different_email() {
        // GIVEN
        UUID uuid = UUID.randomUUID();

        Student student1 = new Student();
        student1.setId(1L);
        student1.setUuid(uuid);
        student1.setEmail(EMAIL);

        Student student2 = new Student();
        student2.setId(1L);
        student2.setUuid(uuid);
        student2.setEmail("other@test.com");

        // THEN
        assertThat(student1).isNotEqualTo(student2);
    }
}