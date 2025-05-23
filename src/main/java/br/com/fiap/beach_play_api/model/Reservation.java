package br.com.fiap.beach_play_api.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull (message = "o número da quadra é obrigatório")
    private int quadra;

    @NotNull (message = "a escolha da data é obrigatória")
    private LocalDate data;

    @NotNull (message = "o horário é obrigatório")
    private LocalTime horario;

    private Long userId;

    //  Contrutores
    public Reservation(int quadra, LocalDate data, LocalTime horario, Long userId) {
        this.quadra = quadra;
        this.data = data;
        this.horario = horario;
        this.userId = userId;
    }
    
    public Reservation() {
    }
    
    
    //  Getters (obter valor, permite visualizar, ler o valor do atributo)
    //  Setters (modificar o valor)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getQuadra() {
        return quadra;
    }
    public void setQuadra(int quadra) {
        this.quadra = quadra;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public LocalTime getHorario() {
        return horario;
    }
    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
