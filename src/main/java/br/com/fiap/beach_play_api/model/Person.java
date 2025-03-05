package br.com.fiap.beach_play_api.model;

public class Person {
    private String name;
    private String cpf;
    private String email;

    // Construtores
    public Person(String name, String cpf, String email) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }
    // Getters (apenas para visualização, não haverá mudanças)
    public String getName() {
        return name;
    }
    public String getCpf() {
        return cpf;
    }
    public String getEmail() {
        return email;
    }
    
}
