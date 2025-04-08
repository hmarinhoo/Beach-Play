package br.com.fiap.beach_play_api.controller;

import br.com.fiap.beach_play_api.model.Cadastro;
import br.com.fiap.beach_play_api.model.Login;
import br.com.fiap.beach_play_api.repository.CadastroRepository;
import br.com.fiap.beach_play_api.repository.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login") 
@CrossOrigin(origins = "http://localhost:3000") 
public class LoginController {

    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private LoginRepository loginRepository;


    // Autenticação de login (POST)
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody Login login) {
        Optional<Cadastro> usuario = cadastroRepository.findByEmail(login.getEmail());
    
        if (usuario.isPresent() && usuario.get().getSenha().equals(login.getSenha())) {
            return ResponseEntity.ok(usuario.get()); // retorna os dados do usuário
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos!");
        }
    }

    @GetMapping
    public List<Login> listarLogins() {
        return loginRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Login> buscarPorId(@PathVariable Long id) {
        Optional<Login> login = loginRepository.findById(id);
        return login.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Login> atualizarLogin(@PathVariable Long id, @Valid @RequestBody Login login) {
        if (!loginRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        login.setId(id);
        Login loginAtualizado = loginRepository.save(login);
        return ResponseEntity.ok(loginAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLogin(@PathVariable Long id) {
        if (!loginRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        loginRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


