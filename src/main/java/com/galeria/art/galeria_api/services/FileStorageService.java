package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.exceptions.FileStorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path rootLocation;
    private static final long maxFileSize = 100 * 1024 * 1024; // 100MB
    private static final List<String> allowedContentTypes = Arrays.asList("image/jpeg", "image/png", "image/gif");

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível criar o diretório de upload.", e);
        }
    }

    public String salvarFoto(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("O arquivo está vazio.");
            }
            if (file.getSize() > maxFileSize) {
                throw new FileStorageException("O arquivo é grande demais.");
            }
            if (!allowedContentTypes.contains(file.getContentType())) {
                throw new FileStorageException("Formato de arquivo inválido, apenas JPEG e PNG são permitidos.");
            }

            String extensao = FilenameUtils.getExtension(file.getOriginalFilename());
            String novoNomeArq = UUID.randomUUID() + "." + extensao;

            Path caminhoArq = this.rootLocation.resolve(novoNomeArq);

            if (!caminhoArq.getParent().equals(this.rootLocation)) {
                throw new FileStorageException("Não foi possível salvar o arquivo fora do diretório raiz.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, caminhoArq, StandardCopyOption.REPLACE_EXISTING);
            }

            return novoNomeArq;
        } catch (IOException e) {
            throw new FileStorageException("Falha ao salvar o arquivo.", e);
        }
    }

    public void deletarFoto(String filePath) {

        Path path = this.rootLocation.resolve(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível deletar o arquivo.");
        }
    }
}
