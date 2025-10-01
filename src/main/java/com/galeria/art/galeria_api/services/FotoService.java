package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.FotoDTO;
import com.galeria.art.galeria_api.dto.FotoUploadDTO;
import com.galeria.art.galeria_api.exceptions.FotoUploadException;
import com.galeria.art.galeria_api.models.Foto;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.repositories.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final ModelMapper modelMapper;
    private final FotoRepository fotoRepository;
    private final FileStorageService fileStorageService;

    public List<FotoDTO> listarFotos(User owner) {
        List<Foto> fotos = fotoRepository.findByOwner(owner);

        return fotos.stream()
                .map(foto -> modelMapper.map(foto, FotoDTO.class))
                .collect(Collectors.toList());
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

    public void deletarFoto(User owner) {

    }
}
