package br.com.fiap.beach_play_api.model;

public class Cadastro {
    private String name;
    private String email;
    private String cpf;
    private String senha;

    //Construtores
    public Cadastro(String name, String email, String cpf, String senha) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    //Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

