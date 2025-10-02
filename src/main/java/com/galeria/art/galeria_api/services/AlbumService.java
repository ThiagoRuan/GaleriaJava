package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.*;
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

    public List<AlbumDTO> listarAlbuns(User owner) {
        List<Album> albuns = albumRepository.findByOwner(owner);

        return albuns.stream()
                .map(album -> modelMapper.map(album, AlbumDTO.class))
                .collect(Collectors.toList());
    }

    public AlbumDTO criarAlbum(AlbumCreateDTO createAlbumDTO, User owner) {
        albumRepository.findByTituloAndOwner(createAlbumDTO.getTitulo(), owner).ifPresent(album -> {
            throw new AlbumAlreadyExistsException("Já existe um álbum '"+createAlbumDTO.getTitulo()+"' na sua galeria.");
        });

        Album album = new Album();
        album.setTitulo(createAlbumDTO.getTitulo());
        album.setOwner(owner);

        return modelMapper.map(albumRepository.save(album), AlbumDTO.class);
    }

    public void deletarAlbum(User owner, Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Álbum com id '"+albumId+"' não encontrado."));
        if (!album.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para deletar esse álbum.");
        }

        albumRepository.deleteById(albumId);
    }

    public AlbumDTO atualizarAlbum(User owner, Long albumId, AlbumUpdateDTO albumDTO) {
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

        return modelMapper.map(
                albumRepository.findById(albumId).orElseThrow(() -> new ItemNotFoundException("Album '"+albumId+"' não encontrado.")).getCoverFoto(),
                FotoDTO.class
        );
    }

    public Set<FotoDTO> fotos(User owner, Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Album '"+albumId+"' não encontrado."));

        if (!album.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para ver as fotos desse álbum");
        }

        Set<Foto> fotos = album.getFotos();
        return fotos.stream()
                .map(foto -> modelMapper.map(foto, FotoDTO.class))
                .collect(Collectors.toSet());
    }

    public void adicionarFoto(User owner, AlbumAddFotoDTO albumAddFotoDTO, Long albumId) {
        Foto foto = fotoRepository.findById(albumAddFotoDTO.getFotoId())
                .orElseThrow(() -> new ItemNotFoundException("Foto com id '"+albumAddFotoDTO.getFotoId()+"' não encontrada."));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Album '"+albumId+"' não encontrado."));

        if (!album.getOwner().getId().equals(owner.getId()) || !foto.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para adicionar a foto no álbum.");
        }

        foto.setAlbum(album);
        fotoRepository.save(foto);
    }

    public void deletarFotoAlbum(User owner, Long fotoId, Long albumId) {
        Foto foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new ItemNotFoundException("Foto com id '"+fotoId+"' não encontrada."));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ItemNotFoundException("Album '"+albumId+"' não encontrado."));

        if (!album.getOwner().getId().equals(owner.getId()) || !foto.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para deletar a foto do álbum.");
        }

        foto.setAlbum(null);
        fotoRepository.save(foto);
    }

}
