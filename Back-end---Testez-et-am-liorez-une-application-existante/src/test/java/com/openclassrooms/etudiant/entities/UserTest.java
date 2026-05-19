package com.openclassrooms.etudiant.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private static final String FIRST_NAME = "testFirst";
    private static final String LAST_NAME = "testLAst";
    private static final String LOGIN = "testLogin";
    private static final String PASSWORD = "test";

    @Test
    public void test_getters_setters() {
        // GIVEN
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        user.setId(1L);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setCreated_at(now);
        user.setUpdated_at(now);

        // THEN
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME);
        assertThat(user.getLogin()).isEqualTo(LOGIN);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getCreated_at()).isEqualTo(now);
        assertThat(user.getUpdated_at()).isEqualTo(now);
    }

    @Test
    public void test_all_args_constructor() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        User user = new User(1L, FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, now, now);

        // THEN
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME);
        assertThat(user.getLogin()).isEqualTo(LOGIN);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void test_getUsername_returns_login() {
        // GIVEN
        User user = new User();
        user.setLogin(LOGIN);

        // THEN
        assertThat(user.getUsername()).isEqualTo(LOGIN);
    }

    @Test
    public void test_getPassword_returns_password() {
        // GIVEN
        User user = new User();
        user.setPassword(PASSWORD);

        // THEN
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void test_getAuthorities_returns_empty_list() {
        // GIVEN
        User user = new User();

        // THEN
        assertThat(user.getAuthorities()).isEmpty();
    }

    @Test
    public void test_account_is_not_expired() {
        // GIVEN
        User user = new User();

        // THEN
        assertThat(user.isAccountNonExpired()).isTrue();
    }

    @Test
    public void test_account_is_not_locked() {
        // GIVEN
        User user = new User();

        // THEN
        assertThat(user.isAccountNonLocked()).isTrue();
    }

    @Test
    public void test_credentials_are_not_expired() {
        // GIVEN
        User user = new User();

        // THEN
        assertThat(user.isCredentialsNonExpired()).isTrue();
    }

    @Test
    public void test_account_is_enabled() {
        // GIVEN
        User user = new User();

        // THEN
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    public void test_equals_and_hashcode() {
        // GIVEN
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName(FIRST_NAME);
        user1.setLastName(LAST_NAME);
        user1.setLogin(LOGIN);
        user1.setPassword(PASSWORD);

        User user2 = new User();
        user2.setId(1L);
        user2.setFirstName(FIRST_NAME);
        user2.setLastName(LAST_NAME);
        user2.setLogin(LOGIN);
        user2.setPassword(PASSWORD);

        // THEN
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    public void test_not_equals_when_different_login() {
        // GIVEN
        User user1 = new User();
        user1.setId(1L);
        user1.setLogin(LOGIN);

        User user2 = new User();
        user2.setId(1L);
        user2.setLogin("otherLogin");

        // THEN
        assertThat(user1).isNotEqualTo(user2);
    }
}