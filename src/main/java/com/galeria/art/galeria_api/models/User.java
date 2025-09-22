package com.galeria.art.galeria_api.models

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    // ID do usuário.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do usuário.
    @Column(name = "nome", nullable = false)
    private String nome;

    // Senha do usuário.
    @Column(name = "senha", nullable = false)
    private String password;

    // Email do usuário utilizado para cadastrar e autenticar.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Fotos do usuário.
    // CascadeType.ALL: Se um usuário for deletado, todas as suas fotos também serão.
    // orphanRemoval = true: Se uma foto for removida desta lista, ela será deletada do banco.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;
    
    // Álbuns do usuário.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns;
    
}
