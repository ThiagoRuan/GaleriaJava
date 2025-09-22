package com.galeria.art.galeria_api.models

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {

    // ID da Tag.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome da Tag.
    @Column(unique = true, nullable = false)
    private String nome;

    // Álbuns da Tag.
    @ManyToMany(mappedBy = "tags")
    private Set<Album> albuns;

}
