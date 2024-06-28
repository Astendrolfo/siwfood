package it.uniroma3.siw.siwfood.repository;
import it.uniroma3.siw.siwfood.model.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUserId(Long userId);
    Optional<Image> findByRicettaId(Long ricettaId);
}
