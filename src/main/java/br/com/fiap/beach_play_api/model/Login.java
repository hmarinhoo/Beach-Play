package br.com.fiap.beach_play_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Data
public class Login implements UserDetails {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O email é obrigatório")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, max = 10, message = "Deve ter entre 5 e 10 caracteres.")
    private String senha;

    // Métodos UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // ou Collections.emptyList() — não usará permissões
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // sem expiração
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // sem bloqueio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // credenciais válidas
    }

    @Override
    public boolean isEnabled() {
        return true; // conta ativada
    }
}
