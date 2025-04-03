package br.com.fiap.beach_play_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Cadastro {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @NotBlank(message = "O nome é obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+.*$", message = "Informe o nome completo (mínimo 2 palavras)")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;  

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, max = 10, message = "Deve ter entre 5 e 10 caracteres.")
    private String senha;
}


