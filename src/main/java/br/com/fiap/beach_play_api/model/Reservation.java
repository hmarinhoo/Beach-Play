package br.com.fiap.beach_play_api.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Reservation {
    private Long id;
    private int quadra;
    private LocalDate data;
    private LocalTime horario;

    //  Contrutores
    public Reservation(Long id, int quadra, LocalDate data, LocalTime horario) {
        this.id = Math.abs(new Random().nextLong()); //aleatorizar um id
        this.quadra = quadra;
        this.data = data;
        this.horario = horario;
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
    
}
