package com.galeria.art.galeria_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "O formato do email é inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    private String senha;
}