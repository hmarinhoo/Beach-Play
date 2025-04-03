package br.com.fiap.beach_play_api.model;

public class Login {
    private String email;
    private String senha;

    // Construtores
    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
    
