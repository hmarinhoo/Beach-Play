package br.com.fiap.beach_play_api.controller;

import br.com.fiap.beach_play_api.model.Login;
import br.com.fiap.beach_play_api.model.dto.LoginRequestDTO;
import br.com.fiap.beach_play_api.model.dto.TokenDTO;
import br.com.fiap.beach_play_api.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        if (Objects.equals(loginRequest.email(), "admin@beach.com") &&
            Objects.equals(loginRequest.senha(), "123456")) {

            // Usuário fictício para teste
            Login login = Login.builder()
                    .id(1L)
                    .email(loginRequest.email())
                    .senha(loginRequest.senha())
                    .build();

            TokenDTO token = tokenService.createToken(login);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
