package com.galeria.art.galeria_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAlbumDTO {

    @NotBlank(message = "O título do álbum não pode ficar em branco.")
    private String titulo;

}
