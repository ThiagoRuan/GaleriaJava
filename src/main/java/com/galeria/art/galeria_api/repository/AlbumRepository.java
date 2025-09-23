package com.galeria.art.galeria_api.repository;

import com.galeria.art.galeria_api.models.Album;
import com.galeria.art.galeria_api.models.Tag;
import com.galeria.art.galeria_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByOwner(User owner);
    Optional<Album> findByTituloAndOwner(String titulo, User owner);
    List<Album> findByTags(Tag tag);
}
