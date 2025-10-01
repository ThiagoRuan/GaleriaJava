package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.FotoDTO;
import com.galeria.art.galeria_api.dto.FotoUploadDTO;
import com.galeria.art.galeria_api.exceptions.FotoUploadException;
import com.galeria.art.galeria_api.exceptions.ItemNotFoundException;
import com.galeria.art.galeria_api.exceptions.UnauthorizedUserException;
import com.galeria.art.galeria_api.models.Foto;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.repositories.AlbumRepository;
import com.galeria.art.galeria_api.repositories.FotoRepository;
import com.galeria.art.galeria_api.specifications.FotoSpecifications;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final ModelMapper modelMapper;
    private final FotoRepository fotoRepository;
    private final FileStorageService fileStorageService;
    private final AlbumRepository albumRepository;

    public Page<Foto> listarFotos(User owner, Long albumId, String autor, String extensao, LocalDateTime inicioData, LocalDateTime fimData, Pageable pageable) {
        Specification<Foto> spec = FotoSpecifications.comFiltros(
                owner,
                albumRepository.findById(albumId)
                        .orElseThrow(() -> new ItemNotFoundException("Álbum com id '"+albumId+"' não encontrado.")),
                autor,
                extensao,
                inicioData,
                fimData
        );

        return fotoRepository.findAll(spec, pageable);
    }

    public FotoDTO salvarFoto(FotoUploadDTO fotoDTO, MultipartFile file, User owner) {
        String filePath = fileStorageService.salvarFoto(file);

        Foto foto = new Foto();
        foto.setNome(fotoDTO.getNome());
        foto.setAutor(fotoDTO.getAutor());
        foto.setOwner(owner);

        foto.setFilePath(filePath);
        foto.setTamanho(file.getSize());
        foto.setExtensao(FilenameUtils.getExtension(file.getOriginalFilename()));

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage != null) {
                foto.setAltura(bufferedImage.getHeight());
                foto.setLargura(bufferedImage.getWidth());
            }
        } catch (IOException e) {
            throw new FotoUploadException("Falha ao coletar dimensões da foto.", e);
        }

        Foto fotoSalva = fotoRepository.save(foto);
        return modelMapper.map(fotoSalva, FotoDTO.class);
    }

    public void deletarFoto(User owner, Long fotoId) {
        Foto foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new ItemNotFoundException("Foto com id '"+fotoId+"' não encontrada."));

        if (!foto.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedUserException("Você não possui permissão para deletar essa foto.");
        }

        fileStorageService.deletarFoto(foto.getFilePath());
        fotoRepository.deleteById(fotoId);
    }
}
