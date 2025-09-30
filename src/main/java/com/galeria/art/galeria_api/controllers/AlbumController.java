package com.galeria.art.galeria_api.controllers;

import com.galeria.art.galeria_api.dto.AlbumDTO;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getMeusAlbuns(@AuthenticationPrincipal User usuarioLogado) {
        List<AlbumDTO> meusAlbuns = albumService.findAlbumsByOwner(usuarioLogado);

        return ResponseEntity.ok(meusAlbuns);
    }
}
