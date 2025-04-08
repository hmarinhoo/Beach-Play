package br.com.fiap.beach_play_api.repository;

import br.com.fiap.beach_play_api.model.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
    Optional<Cadastro> findByEmail(String email);
}
