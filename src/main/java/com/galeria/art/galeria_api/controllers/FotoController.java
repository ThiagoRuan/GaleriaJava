package com.galeria.art.galeria_api.controllers;

import com.galeria.art.galeria_api.dto.FotoDTO;
import com.galeria.art.galeria_api.dto.FotoUploadDTO;
import com.galeria.art.galeria_api.models.Foto;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.services.FotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/fotos")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;
    private final ModelMapper modelMapper;

    @PostMapping("/upload")
    public ResponseEntity<FotoDTO> salvarFoto(
            @Valid FotoUploadDTO fotoDTO,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fotoService.salvarFoto(fotoDTO, file, usuarioLogado));
    }

    @GetMapping
    public ResponseEntity<Page<FotoDTO>> listarFotos(
            @AuthenticationPrincipal User usuarioLogado,
            @RequestParam(required = false) Long albumId,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String extensao,
            @RequestParam(required = false) LocalDateTime inicioData,
            @RequestParam(required = false) LocalDateTime fimData,
            Pageable pageable
    ) {
        Page<Foto> paginaDeFotos = fotoService.listarFotos(usuarioLogado, albumId, autor, extensao, inicioData, fimData, pageable);
        return ResponseEntity.ok(paginaDeFotos.map(foto -> modelMapper.map(foto, FotoDTO.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFoto(
            @PathVariable Long id,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        fotoService.deletarFoto(usuarioLogado, id);
        return ResponseEntity.noContent().build();
    }
}
