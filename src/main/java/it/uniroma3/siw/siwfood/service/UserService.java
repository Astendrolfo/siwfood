package it.uniroma3.siw.siwfood.service;

import it.uniroma3.siw.siwfood.exceptions.AuthException;
import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new AuthException("Utente non trovato con l'email: " + email, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public UserDetails loadById(Long id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new AuthException("Utente non trovato con id: " + id, HttpStatus.NOT_FOUND);
        }
        return user;
    }
}
