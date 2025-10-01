package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.AlbumDTO;
import com.galeria.art.galeria_api.dto.UpdateAlbumDTO;
import com.galeria.art.galeria_api.dto.CreateAlbumDTO;
import com.galeria.art.galeria_api.dto.FotoDTO;
import com.galeria.art.galeria_api.exceptions.AlbumAlreadyExistsException;
import com.galeria.art.galeria_api.exceptions.ItemNotFoundException;
import com.galeria.art.galeria_api.exceptions.UnauthorizedUserException;
import com.galeria.art.galeria_api.models.Album;
import com.galeria.art.galeria_api.models.Foto;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.repositories.AlbumRepository;
import com.galeria.art.galeria_api.repositories.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FotoRepository fotoRepository;
    private final ModelMapper modelMapper;

    public List<AlbumDTO> findAlbumsByOwner(User owner) {
        List<Album> albuns = albumRepository.findByOwner(owner);

        return albuns.stream()
                .map(album -> modelMapper.map(album, AlbumDTO.class))
                .collect(Collectors.toList());
    }

    public AlbumDTO criarAlbum(CreateAlbumDTO createAlbumDTO, User owner) {
        albumRepository.findByTituloAndOwner(createAlbumDTO.getTitulo(), owner).ifPresent(album -> {
            throw new AlbumAlreadyExistsException("Já existe um álbum '"+createAlbumDTO.getTitulo()+"' na sua galeria.");
        });

        Album album = new Album();
        album.setTitulo(createAlbumDTO.getTitulo());
        album.setOwner(owner);

        Album albumSalvo = albumRepository.save(album);
        return modelMapper.map(albumSalvo, AlbumDTO.class);
    }

    public void deletarAlbum(User owner, Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Álbum com id '"+albumId+"' não encontrado."));
        if (!album.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para deletar esse álbum.");
        }

        albumRepository.deleteById(albumId);
    }

    public AlbumDTO atualizarAlbum(User owner, Long albumId, UpdateAlbumDTO albumDTO) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Álbum com id '"+albumId+"' não encontrado."));

        if (!album.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para atualizar esse álbum.");
        }
        if (!albumDTO.getTitulo().isBlank()) {
            album.setTitulo(albumDTO.getTitulo());
        }
        if (albumDTO.getCoverId() != null) {
            Foto cover = fotoRepository.findById(albumDTO.getCoverId())
                    .orElseThrow(() -> new ItemNotFoundException("Foto com id '"+albumDTO.getCoverId()+"' não encontrada."));
            if (cover.getOwner().getId().equals(owner.getId())) {
                album.setCoverFoto(cover);
            }
        }

        return modelMapper.map(albumRepository.save(album), AlbumDTO.class);
    }

    public FotoDTO cover(Long albumId) {

        Foto cover = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Album '"+albumId+"' não encontrado."))
                .getCoverFoto();

        return modelMapper.map(cover, FotoDTO.class);
    }

    public Set<FotoDTO> fotos(Long albumId) {

        Set<Foto> fotos = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Album '"+albumId+"' não encontrado."))
                .getFotos();

        return fotos.stream()
                .map(foto -> modelMapper.map(foto, FotoDTO.class))
                .collect(Collectors.toSet());
    }

}
