package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}