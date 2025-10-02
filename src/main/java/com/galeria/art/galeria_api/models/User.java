package com.galeria.art.galeria_api.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    // ID do usuário.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do usuário.
    @Column(nullable = false)
    private String nome;

    // Senha do usuário.
    @Column(nullable = false)
    private String senha;

    // Email do usuário utilizado para cadastrar e autenticar.
    @Column(nullable = false, unique = true)
    private String email;

    // Fotos do usuário.
    // CascadeType.ALL: Se um usuário for deletado, todas as suas fotos também serão.
    // orphanRemoval = true: Se uma foto for removida desta lista, ela será deletada do banco.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;
    
    // Álbuns do usuário.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
