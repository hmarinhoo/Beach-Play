package br.com.fiap.beach_play_api.controller;


import org.springframework.web.bind.annotation.*;

import br.com.fiap.beach_play_api.model.Cadastro;
import br.com.fiap.beach_play_api.model.Login;
import br.com.fiap.beach_play_api.repository.CadastroRepository;
import br.com.fiap.beach_play_api.repository.LoginRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cadastro") 
@CrossOrigin(origins = "http://localhost:3000")


public class UserController {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private LoginRepository loginRepository;

    @CacheEvict(value = "cadastros", allEntries = true)
    @PostMapping
    @Operation(summary = "Criar cadastro.", description = "Permite a criação de um novo cadastro no sistema.")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Cadastro cadastro, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

    Cadastro novoCadastro = cadastroRepository.save(cadastro);

    // Criar e salvar um novo Login com o mesmo email e senha
    Login novoLogin = new Login();
    novoLogin.setEmail(novoCadastro.getEmail());
    novoLogin.setSenha(novoCadastro.getSenha());
    loginRepository.save(novoLogin);

    return ResponseEntity.status(HttpStatus.CREATED).body(novoCadastro);
}

    @Cacheable("cadastros")
    @GetMapping
    @Operation(summary = "Lista os cadastros.", description = "Retorna todos os cadastros no sistema.")
    public List<Cadastro> index() {
        return cadastroRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca cadastro.", description = "Retorna o cadastro referente ao id passado.")
    public ResponseEntity<Cadastro> get(@PathVariable Long id) {
        Optional<Cadastro> cadastro = cadastroRepository.findById(id);
        return cadastro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita um cadastro.", description = "Permite a edição de dados de cadastro no sistema.")
    @CacheEvict(value = "cadastros", allEntries = true)
    public ResponseEntity<Cadastro> update(@PathVariable Long id, @RequestBody Cadastro cadastro) {
        if (!cadastroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cadastro.setId(id);
        Cadastro cadastroAtualizado = cadastroRepository.save(cadastro);
        return ResponseEntity.ok(cadastroAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um cadastro.", description = "Deleta o cadastro referente ao id passado.")
    @CacheEvict(value = "cadastros", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!cadastroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cadastroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
