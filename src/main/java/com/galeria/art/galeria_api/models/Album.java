package com.galeria.art.galeria_api.models

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "albums")
public class Album {
    
    // ID do Álbum.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Título do Álbum.
    @Column(nullable = false)
    private String titulo;

    // Capa do Álbum.
    @OneToOne
    @JoinColumn(name = "cover_id", unique = true)
    private Foto coverFoto;
    
    // Fotos do Álbum.
    @OneToMany(mappedBy = "album")
    private Set<Foto> fotos;

    // Dono do Álbum.
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Tags do Álbum.
    @ManyToMany
    @JoinTable(
      name = "album_tag", 
      joinColumns = @JoinColumn(name = "album_id"), 
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

}
