package com.galeria.art.galeria_api.controllers;

import com.galeria.art.galeria_api.dto.*;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.services.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/albuns")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumDTO> criarAlbum(
            @Valid @RequestBody AlbumCreateDTO albumDTO,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.criarAlbum(albumDTO, usuarioLogado));
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> listarAlbuns(@AuthenticationPrincipal User usuarioLogado) {
        return ResponseEntity.ok(albumService.listarAlbuns(usuarioLogado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAlbum(
            @PathVariable Long id,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        albumService.deletarAlbum(usuarioLogado, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> atualizarAlbum(
            @Valid @RequestBody AlbumUpdateDTO albumDTO,
            @PathVariable Long id,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        return ResponseEntity.ok(albumService.atualizarAlbum(usuarioLogado, id, albumDTO));
    }

    @GetMapping("/{albumId}/fotos")
    public ResponseEntity<Set<FotoDTO>> listarFotosAlbum(
            @AuthenticationPrincipal User usuarioLogado,
            @PathVariable Long albumId
    ) {
        return ResponseEntity.ok(albumService.fotos(usuarioLogado, albumId));
    }

    @PostMapping("/{albumId}/fotos")
    public ResponseEntity<Void> adicionarFoto(
            @Valid @RequestBody AlbumAddFotoDTO albumAddFotoDTO,
            @PathVariable Long albumId,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        albumService.adicionarFoto(usuarioLogado, albumAddFotoDTO, albumId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{albumId}/fotos/{fotoId}")
    public ResponseEntity<Void> deletarFotoAlbum(
            @AuthenticationPrincipal User usuarioLogado,
            @PathVariable Long albumId,
            @PathVariable Long fotoId
    ) {
        albumService.deletarFotoAlbum(usuarioLogado, fotoId, albumId);
        return ResponseEntity.noContent().build();
    }
}
