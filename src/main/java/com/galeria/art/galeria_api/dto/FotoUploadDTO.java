package com.galeria.art.galeria_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoUploadDTO {

    @NotBlank(message = "O nome da foto não pode ficar em branco.")
    private String nome;
    private String autor;

}
