package com.galeria.art.galeria_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumAddFotoDTO {

    @NotNull(message = "O 'id' da foto não pode ser nulo.")
    Long fotoId;

}
