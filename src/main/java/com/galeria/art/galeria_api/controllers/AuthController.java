package com.galeria.art.galeria_api.controllers;

import com.galeria.art.galeria_api.dto.LoginDTO;
import com.galeria.art.galeria_api.dto.LoginResponseDTO;
import com.galeria.art.galeria_api.dto.UserRegisterDTO;
import com.galeria.art.galeria_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registrarUsuario(@Valid @RequestBody UserRegisterDTO userDTO) {
        userService.registrarUsuario(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = userService.autenticarUsuario(loginDTO);
        LoginResponseDTO response = new LoginResponseDTO(token);

        return ResponseEntity.ok(response);
    }
}