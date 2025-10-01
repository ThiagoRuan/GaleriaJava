package com.galeria.art.galeria_api.controllers;

import com.galeria.art.galeria_api.dto.AlbumDTO;
import com.galeria.art.galeria_api.dto.CreateAlbumDTO;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.services.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumDTO> criarAlbum(
            @Valid @RequestBody CreateAlbumDTO albumDTO,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.criarAlbum(albumDTO, usuarioLogado));
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getMeusAlbuns(@AuthenticationPrincipal User usuarioLogado) {
        return ResponseEntity.ok(albumService.findAlbumsByOwner(usuarioLogado));
    }
}
