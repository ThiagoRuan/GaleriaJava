package com.galeria.art.galeria_api.models

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "fotos")
public class Foto {

    // ID da foto.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome da foto.
    @Column(nullable = false)
    private String nome;

    // Autor da foto (Se possuir).
    @Column
    private String autor;

    // Data de criação/salvamento da foto.
    @CreationTimestamp
    private LocalDateTime dataUpload;

    // Extensão da foto.
    @Column
    private String extensao;

    // Tamanho da foto.
    @Column
    private Long tamanho;

    // Dimensões da foto.
    @Column
    private Integer largura;
    @Column
    private Integer altura;
    
    // Link para a foto (arquivo físico).
    @Column(nullable = false)
    private String filePath;
 
    // Dono da foto.
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Álbum da foto (Se possuir).
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
