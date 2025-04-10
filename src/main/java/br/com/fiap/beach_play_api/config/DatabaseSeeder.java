package br.com.fiap.beach_play_api.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.beach_play_api.model.Cadastro;
import br.com.fiap.beach_play_api.model.Reservation;
import br.com.fiap.beach_play_api.repository.CadastroRepository;
import br.com.fiap.beach_play_api.repository.ReservationRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CadastroRepository cadastroRepository;

    @PostConstruct
    public void init() {
        // Criando usuários de teste
        Cadastro usuario1 = new Cadastro();
        usuario1.setNome("Lana Del Rey");
        usuario1.setEmail("lanita@gmail.com");
        usuario1.setCpf("12345678900");
        usuario1.setSenha("lana123");

        Cadastro usuario2 = new Cadastro();
        usuario2.setNome("Loud Coringa");
        usuario2.setEmail("coringa@email.com");
        usuario2.setCpf("98765432100");
        usuario2.setSenha("loud456");

        cadastroRepository.saveAll(List.of(usuario1, usuario2));

        // Criando reservas de teste
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Reservation reserva = new Reservation();
            reserva.setData(LocalDate.now().plusDays(random.nextInt(15)));
            reserva.setHorario(LocalTime.of(8 + 2 * random.nextInt(7), 0)); // entre 8h e 20h
            reserva.setQuadra(random.nextBoolean() ? 1 : 2); // quadra 1 ou 2
            reserva.setUserId(i % 2 == 0 ? usuario1.getId() : usuario2.getId()); // define o id do usuário

            reservationRepository.save(reserva);
        }

    }
}
