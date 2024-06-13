package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siwfood.model.Ricetta;
import org.springframework.stereotype.Repository;

@Repository
public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
    Ricetta findByTitle(String title);
}