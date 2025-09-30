package com.galeria.art.galeria_api.dto;

import com.galeria.art.galeria_api.models.Album;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FotoDTO {
    private Long id;
    private String nome;
    private String filePath;
    private String autor;
    private LocalDateTime dataUpload;
    private String extensao;
    private Long tamanho;
    private Integer largura;
    private Integer altura;
    private Album album;
}
