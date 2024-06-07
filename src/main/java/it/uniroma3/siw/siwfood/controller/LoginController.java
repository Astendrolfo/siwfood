package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.exceptions.AuthException;
import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.repository.UserRepository;
import it.uniroma3.siw.siwfood.response.LoginResponse;
import it.uniroma3.siw.siwfood.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(username);
        if (user != null) {

            //Controllo della password per l'utente trovato

            if (passwordEncoder.matches(password, user.getPassword())) {

                /* Caso in cui l'utente esiste e la password e' corretta */

                Map<String, Object> claims = new HashMap<>();
                claims.put("ruolo", user.getRoleList());

                /* Chiamata al generatore JWT (durata 1h) */

                String token = jwtUtil.generateTokenWithBearer(username, claims, 1000 * 60 * 60);
                return new LoginResponse(token, "Autenticazione completata con successo.", true);
            } else throw new AuthException("Le credenziali inserite sono errate.", HttpStatus.BAD_REQUEST);
        }

        throw new AuthException("Le credenziali inserite sono errate.", HttpStatus.BAD_REQUEST);
    }
}

