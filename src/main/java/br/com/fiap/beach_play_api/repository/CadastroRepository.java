package br.com.fiap.beach_play_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.fiap.beach_play_api.model.Cadastro;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
}
