package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.repository.UserRepository;
import it.uniroma3.siw.siwfood.response.RicettaResponse;
import it.uniroma3.siw.siwfood.service.UserService;
import it.uniroma3.siw.siwfood.service.JwtService;
import it.uniroma3.siw.siwfood.response.UserResponse;
import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.service.foodservices.RicettaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {

    final UserService userService;
    final UserRepository userRepository;
    final JwtService jwtService;
    private final RicettaService ricettaService;

    public UserController(UserService userService, UserRepository userRepository, JwtService jwtService, RicettaService ricettaService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.ricettaService = ricettaService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getListaUtenti() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(new UserResponse(user.getUsername(), user.getNome(), user.getCognome(), user.getDob(), user.getId()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = (User) userService.loadById(id);
        UserResponse response = new UserResponse(user.getUsername(), user.getNome(), user.getCognome(), user.getDob(), user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        User user = (User) userService.loadById(id);
        List<Ricetta> ricette = new ArrayList<>(ricettaService.getAllRicette());
        for (Ricetta ricetta : ricette) {
            if (ricetta.getAuthor() == user) {
                ricettaService.deleteRicettaById(ricetta.getId());
            }
        }
        this.userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser(@RequestHeader("Authorization") String authorizationHeader) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String token = authorizationHeader.substring(7);
        System.out.println("Token: " + token);
        if (principal instanceof User currentUser && this.jwtService.isTokenValid(token, currentUser)) {
            System.out.println("Token valido.");
            UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
            return ResponseEntity.ok(userResponse);
        }
        else {
            System.out.println("Richiesta non valida.");
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
