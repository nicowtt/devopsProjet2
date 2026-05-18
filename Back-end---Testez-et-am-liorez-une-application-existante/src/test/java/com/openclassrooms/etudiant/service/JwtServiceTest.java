package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET_KEY = "dGVzdFNlY3JldEtleUZvckp3dFRlc3RpbmdQdXJwb3Nlcw==";
    private static final long EXPIRATION = 3600000L; // 1 hour

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "expiration", EXPIRATION);

        User user = new User();
        user.setLogin("testuser");
        userDetails = user;
    }

    @Test
    void generateToken_shouldReturnNonNullToken() {
        String token = jwtService.generateToken(userDetails);
        assertThat(token).isNotNull();
        assertThat(token.split("\\.")).hasSize(3); // header.payload.signature
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    void isTokenValid_shouldReturnTrue_whenTokenIsValid() {
        String token = jwtService.generateToken(userDetails);
        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenUsernameDoesNotMatch() {
        String token = jwtService.generateToken(userDetails);
        User otherUser = new User();
        otherUser.setLogin("otheruser");
        assertThat(jwtService.isTokenValid(token, otherUser)).isFalse();
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenTokenIsExpired() {
        // Génère un token déjà expiré
        ReflectionTestUtils.setField(jwtService, "expiration", -1000L);
        String expiredToken = jwtService.generateToken(userDetails);

        ReflectionTestUtils.setField(jwtService, "expiration", EXPIRATION);
        assertThat(jwtService.isTokenValid(expiredToken, userDetails)).isFalse();
    }
}
