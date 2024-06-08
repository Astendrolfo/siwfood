package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.service.UserService;
import it.uniroma3.siw.siwfood.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<String> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User currentUser = (User) principal;
            return ResponseEntity.ok(currentUser.getUsername());
        } else {
            // Gestire il caso in cui l'utente non è autenticato o l'oggetto principale non è un'istanza di User
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autenticato");
        }
    }
}
