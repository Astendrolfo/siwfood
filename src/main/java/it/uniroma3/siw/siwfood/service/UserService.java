package it.uniroma3.siw.siwfood.service;

import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        if (repository.findByEmail(email) != null) {
            return repository.findByEmail(email);
        }
        throw new UsernameNotFoundException("No user with email :" + email);
    }
}
