package br.com.fiap.beach_play_api.repository;

import br.com.fiap.beach_play_api.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);
}
