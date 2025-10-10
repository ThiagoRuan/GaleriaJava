package com.galeria.art.galeria_api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumUpdateDTO {
    @Pattern(regexp = ".*\\S.*", message = "O título não pode estar em branco.")
    private String titulo;
    private Long coverId;
}
