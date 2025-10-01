package com.galeria.art.galeria_api.repositories;

import com.galeria.art.galeria_api.models.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>, JpaSpecificationExecutor<Foto> {
//    List<Foto> findByOwner(User owner);
//    List<Foto> findByAlbum(Album album);
//    List<Foto> findByAutor(String autor);
//    List<Foto> findByExtensao(String extensao);
//    List<Foto> findByDataUploadBetween(LocalDateTime inicioDoAno, LocalDateTime fimDoAno);
}
