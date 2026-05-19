package com.openclassrooms.etudiant.handler;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorDetailsTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
    private static final String MESSAGE = "Resource not found";
    private static final String DETAILS = "uri=/api/students/99";

    @Test
    public void test_no_args_constructor() {
        // GIVEN / WHEN
        ErrorDetails errorDetails = new ErrorDetails();

        // THEN
        assertThat(errorDetails.getTimestamp()).isNull();
        assertThat(errorDetails.getMessage()).isNull();
        assertThat(errorDetails.getDetails()).isNull();
    }

    @Test
    public void test_all_args_constructor() {
        // GIVEN / WHEN
        ErrorDetails errorDetails = new ErrorDetails(TIMESTAMP, MESSAGE, DETAILS);

        // THEN
        assertThat(errorDetails.getTimestamp()).isEqualTo(TIMESTAMP);
        assertThat(errorDetails.getMessage()).isEqualTo(MESSAGE);
        assertThat(errorDetails.getDetails()).isEqualTo(DETAILS);
    }

    @Test
    public void test_getters_setters() {
        // GIVEN
        ErrorDetails errorDetails = new ErrorDetails();

        // WHEN
        errorDetails.setTimestamp(TIMESTAMP);
        errorDetails.setMessage(MESSAGE);
        errorDetails.setDetails(DETAILS);

        // THEN
        assertThat(errorDetails.getTimestamp()).isEqualTo(TIMESTAMP);
        assertThat(errorDetails.getMessage()).isEqualTo(MESSAGE);
        assertThat(errorDetails.getDetails()).isEqualTo(DETAILS);
    }

    @Test
    public void test_equals() {
        // GIVEN
        ErrorDetails errorDetails1 = new ErrorDetails(TIMESTAMP, MESSAGE, DETAILS);
        ErrorDetails errorDetails2 = new ErrorDetails(TIMESTAMP, MESSAGE, DETAILS);

        // THEN
        assertThat(errorDetails1).isEqualTo(errorDetails2);
    }

    @Test
    public void test_not_equals() {
        // GIVEN
        ErrorDetails errorDetails1 = new ErrorDetails(TIMESTAMP, MESSAGE, DETAILS);
        ErrorDetails errorDetails2 = new ErrorDetails(TIMESTAMP, "Other error", DETAILS);

        // THEN
        assertThat(errorDetails1).isNotEqualTo(errorDetails2);
    }
}