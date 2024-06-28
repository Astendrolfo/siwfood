package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.repository.UserRepository;
import it.uniroma3.siw.siwfood.service.UserService;
import it.uniroma3.siw.siwfood.response.UserResponse;
import it.uniroma3.siw.siwfood.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

    public UserController(UserService userService, UserRepository userRepository) {
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof User currentUser) {
            UserResponse userResponse = new UserResponse(currentUser.getUsername());
            return ResponseEntity.ok(userResponse);
        } else {
            // Gestire il caso in cui l'utente non è autenticato o l'oggetto principale non è un'istanza di User
            return (ResponseEntity<UserResponse>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }
    }
}
