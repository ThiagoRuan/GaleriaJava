package com.galeria.art.galeria_api.controllers;

import com.galeria.art.galeria_api.dto.FotoDTO;
import com.galeria.art.galeria_api.dto.FotoUploadDTO;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.services.FotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;

    @PostMapping("/upload")
    public ResponseEntity<FotoDTO> salvarFoto(
            @Valid FotoUploadDTO fotoDTO,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fotoService.salvarFoto(fotoDTO, file, usuarioLogado));
    }

    @GetMapping
    public ResponseEntity<List<FotoDTO>> listarFotos(@AuthenticationPrincipal User usuarioLogado) {
        return ResponseEntity.ok(fotoService.listarFotos(usuarioLogado));
    }
}
