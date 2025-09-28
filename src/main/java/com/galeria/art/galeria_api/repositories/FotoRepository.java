package com.galeria.art.galeria_api.repositories;

import com.galeria.art.galeria_api.models.Foto;
import com.galeria.art.galeria_api.models.Album;
import com.galeria.art.galeria_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findByOwner(User owner);
    List<Foto> findByAlbum(Album album);
    List<Foto> findByAutor(String autor);
    List<Foto> findByExtensao(String extensao);
    List<Foto> findByDataUploadYear(int ano);
}
