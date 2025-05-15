package br.com.fiap.beach_play_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.beach_play_api.model.Cadastro;
import br.com.fiap.beach_play_api.repository.CadastroRepository;

public class AuthService {
     @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cadastro register(Cadastro usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return cadastroRepository.save(usuario);
    }

    public boolean login(String email, String senha) {
        return cadastroRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(senha, user.getSenha()))
                .orElse(false);
    }
}
