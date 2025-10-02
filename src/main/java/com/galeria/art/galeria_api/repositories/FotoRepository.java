package com.galeria.art.galeria_api.repositories;

import com.galeria.art.galeria_api.models.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>, JpaSpecificationExecutor<Foto> {}
