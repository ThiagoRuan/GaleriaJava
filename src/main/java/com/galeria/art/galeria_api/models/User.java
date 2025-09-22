package com.galeria.art.galeria_api.models

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

// O Lombok cuida dos Getters e Setters.
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
    // nullable = false: Não pode ser valor nulo.
    @Column(name = "nome", nullable = false)
    private String nome;

    // Senha do usuário.
    @Column(name = "senha", nullable = false)
    private String password;

    // Email do usuário utilizado para cadastrar e autenticar.
    // unique = true: O Email é único e pertence apenas a um usuário.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // CascadeType.ALL: Se um usuário for deletado, todas as suas fotos também serão.
    // orphanRemoval = true: Se uma foto for removida desta lista, ela será deletada do banco.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns;
    
}
