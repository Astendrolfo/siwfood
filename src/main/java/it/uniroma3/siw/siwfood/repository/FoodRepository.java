package it.uniroma3.siw.siwfood.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siwfood.model.Ricetta;

public interface FoodRepository extends CrudRepository<Ricetta, Long> {
}