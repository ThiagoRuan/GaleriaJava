package com.galeria.art.galeria_api.specifications;

import com.galeria.art.galeria_api.models.Album;
import com.galeria.art.galeria_api.models.Foto;
import com.galeria.art.galeria_api.models.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.Predicate;

public class FotoSpecifications {

    public static Specification<Foto> comFiltros(
            User owner,
            Album album,
            String autor,
            String extensao,
            LocalDateTime inicioData,
            LocalDateTime fimData
    ) {
      return (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (owner != null) {
              predicates.add(criteriaBuilder.equal(root.get("owner"), owner));
          }

          if (album != null) {
              predicates.add(criteriaBuilder.equal(root.get("album"), album));
          }

          if (autor != null && !autor.trim().isBlank()) {
              predicates.add(criteriaBuilder.like(root.get("autor"), "%"+autor.trim()+"%"));
          }

          if (extensao != null && !extensao.trim().isBlank()) {
              predicates.add(criteriaBuilder.like(root.get("extensao"), "%"+extensao.trim()+"%"));
          }

          if (inicioData != null) {
              predicates.add(criteriaBuilder.between(root.get("dataUpload"), inicioData, Objects.requireNonNullElseGet(fimData, LocalDateTime::now)));
          }

          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
    }
}
