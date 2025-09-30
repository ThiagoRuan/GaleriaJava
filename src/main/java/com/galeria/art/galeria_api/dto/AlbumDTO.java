package com.galeria.art.galeria_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AlbumDTO {
    private Long id;
    private String titulo;
    private FotoDTO coverFoto;
    private Set<FotoDTO> fotos;
    private Set<TagDTO> tags;
}
