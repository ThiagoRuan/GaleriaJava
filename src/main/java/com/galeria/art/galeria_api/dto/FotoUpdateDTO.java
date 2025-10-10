package com.galeria.art.galeria_api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoUpdateDTO {
    @Pattern(regexp = ".*\\S.*", message = "O nome não pode estar em branco.")
    private String nome;
    @Pattern(regexp = ".*\\S.*", message = "O autor não pode estar em branco.")
    private String autor;
}
