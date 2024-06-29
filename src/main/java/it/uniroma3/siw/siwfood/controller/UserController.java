package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.repository.UserRepository;
import it.uniroma3.siw.siwfood.service.UserService;
import it.uniroma3.siw.siwfood.response.UserResponse;
import it.uniroma3.siw.siwfood.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof User currentUser) {
            UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
            return ResponseEntity.ok(userResponse);
        } else {
            // Gestire il caso in cui l'utente non è autenticato o l'oggetto principale non è un'istanza di User
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/modify/me")
    public ResponseEntity<UserResponse> modifyUser(@RequestBody User modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByEmail(username);

        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (modifiedUser.getNome() != null) {
            currentUser.setNome(modifiedUser.getNome());
        }

        if (modifiedUser.getCognome() != null) {
            currentUser.setCognome(modifiedUser.getCognome());
        }

        if (modifiedUser.getEmail() != null) {
            currentUser.setEmail(modifiedUser.getEmail());
        }

        if (modifiedUser.getDob() != null) {
            currentUser.setDob(modifiedUser.getDob());
        }

        userRepository.save(currentUser);

        UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
        return ResponseEntity.ok(userResponse);
    }
}
