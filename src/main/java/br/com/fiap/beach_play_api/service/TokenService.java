package br.com.fiap.beach_play_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import br.com.fiap.beach_play_api.model.Login;
import br.com.fiap.beach_play_api.model.dto.TokenDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final String SECRET = "secret";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public TokenDTO createToken(Login login) {
        Instant expiresAt = LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
                .withSubject(login.getId().toString())
                .withClaim("email", login.getEmail())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new TokenDTO(token, login.getEmail());
    }

    public Login getUserFromToken(String token) {
        DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

        return Login.builder()
                .id(Long.valueOf(decoded.getSubject()))
                .email(decoded.getClaim("email").asString())
                .build();
    }

    public boolean isValid(String token) {
        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return JWT.require(algorithm).build().verify(token).getClaim("email").asString();
    }
}
