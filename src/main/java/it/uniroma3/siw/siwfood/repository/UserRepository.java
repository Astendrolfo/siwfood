package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByEmail(String email);
}