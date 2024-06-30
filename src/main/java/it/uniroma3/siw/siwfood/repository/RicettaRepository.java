package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.model.User;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siwfood.model.Ricetta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
    Ricetta findByTitle(String title);
    List<Ricetta> findByAuthor(User user);
}