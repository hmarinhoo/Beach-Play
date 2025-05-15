package br.com.fiap.beach_play_api.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // Chave secreta com no mínimo 256 bits (32 caracteres ASCII para HS512)
    private static final String SECRET = "chave-secreta-super-segura-chave-secreta-super-segura";
    private static final long EXPIRATION = 86400000; // 1 dia em milissegundos

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Geração do token com email como "subject"
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // O conteúdo principal do token (identificação do usuário)
                .setIssuedAt(new Date()) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // Expiração
                .signWith(key, SignatureAlgorithm.HS512) // Algoritmo e chave de assinatura
                .compact(); // Finaliza e gera o token
    }

    // Extração do email a partir do token
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Define a chave para validação
                .build()
                .parseClaimsJws(token) // Faz o parsing
                .getBody()
                .getSubject(); // Retorna o "subject" (email)
    }

    // Verifica se o token é válido (estrutura + assinatura + expiração)
    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false; // Token inválido ou expirado
        }
    }
}
