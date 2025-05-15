package br.com.fiap.beach_play_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import br.com.fiap.beach_play_api.repository.CadastroRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return cadastroRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
