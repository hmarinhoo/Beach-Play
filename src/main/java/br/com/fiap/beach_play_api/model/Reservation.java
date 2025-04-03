package br.com.fiap.beach_play_api.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull (message = "o número da quadra é obrigatório")
    private int quadra;

    @NotNull (message = "a escolha da data é obrigatória")
    private LocalDate data;

    @NotNull (message = "o horário é obrigatório")
    private LocalTime horario;

   
}
