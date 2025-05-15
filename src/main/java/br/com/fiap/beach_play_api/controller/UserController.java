package br.com.fiap.beach_play_api.controller;


import org.springframework.web.bind.annotation.*;

import br.com.fiap.beach_play_api.model.Cadastro;

import br.com.fiap.beach_play_api.repository.CadastroRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/cadastro") 
@CrossOrigin(origins = "http://localhost:3000")


public class UserController {

   @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Cadastro cadastro, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        if (cadastroRepository.existsByEmail(cadastro.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        // Criptografar senha
        cadastro.setSenha(passwordEncoder.encode(cadastro.getSenha()));

        Cadastro novoCadastro = cadastroRepository.save(cadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCadastro);
    }
}
