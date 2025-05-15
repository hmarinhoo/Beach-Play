package br.com.fiap.beach_play_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // CORRETO!
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.beach_play_api.model.dto.LoginRequestDTO;
import br.com.fiap.beach_play_api.model.dto.TokenDTO;
import br.com.fiap.beach_play_api.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequestDTO loginDto) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.senha())
        );

        String token = tokenService.generateToken(loginDto.email());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
