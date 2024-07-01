package it.uniroma3.siw.siwfood.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.response.RicettaResponse;
import it.uniroma3.siw.siwfood.response.UserResponse;
import it.uniroma3.siw.siwfood.service.UserService;
import it.uniroma3.siw.siwfood.service.foodservices.ImageService;
import it.uniroma3.siw.siwfood.service.foodservices.RicettaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RicettaService ricettaService;
    private final UserService userService;
    private final ImageService imageService;

    public RicettaController(RicettaService ricettaService, UserService userService, ImageService imageService) {
        this.ricettaService = ricettaService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @PostMapping("/addricetta")
    public ResponseEntity<?> createRicetta(@RequestBody JsonNode requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        String token = (String) authentication.getCredentials();

        try {
            Ricetta savedRicetta = new Ricetta();
            String title = requestBody.get("title").asText();
            String description = requestBody.get("description").asText();
            Long userId = requestBody.get("authorId").asLong();


            User autore = (User)userService.loadById(userId);

            if (autore == null) {
                return ResponseEntity.notFound().build();
            }

            // Gestione della lista degli ingredienti
            List<Ingrediente> listaIngredienti = new ArrayList<>();
            JsonNode ingredientiNode = requestBody.get("listaIngredienti");
            if (ingredientiNode != null && ingredientiNode.isArray()) {
                for (final JsonNode ingredientNode : ingredientiNode) {
                    Ingrediente ingrediente = objectMapper.treeToValue(ingredientNode, Ingrediente.class);
                    ingrediente.setRicetta(savedRicetta);
                    listaIngredienti.add(ingrediente);
                }
            } else {
                // Se listaIngredienti non è presente o non è un array, la lista degli ingredienti sarà vuota
                System.out.println("Nessun ingrediente fornito nel JSON.");
                // Puoi scegliere di aggiungere un messaggio di log o altre azioni appropriate
            }


            /*
            Inizializzazione effettiva dei parametri della ricetta
             */

            savedRicetta.setTitle(title);
            savedRicetta.setListaIngredienti(listaIngredienti);
            savedRicetta.setDescription(description);
            savedRicetta.setAuthor(autore);
            ricettaService.saveRicetta(savedRicetta);

            RicettaResponse response = new RicettaResponse(savedRicetta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log dell'errore
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RicettaResponse>> getAllRicette() {
        List<Ricetta> ricette = ricettaService.getAllRicette();
        List<RicettaResponse> ricetteResponse = ricette.stream()
                .map(RicettaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ricetteResponse);
    }

    @GetMapping("/madeby/{id}")
    public ResponseEntity<List<RicettaResponse>> getRicetteMadeByUser(@PathVariable Long id) {
        User user = (User) userService.loadById(id);
        if (user != null) {
            List<Ricetta> ricette = ricettaService.getRicetteByAuthor(user);
            List<RicettaResponse> ricetteResponse = ricette.stream()
                    .map(RicettaResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ricetteResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<RicettaResponse> getRicettaById(@PathVariable Long id) {
        Ricetta ricetta = ricettaService.getRicettaById(id);
        System.out.println(ricetta.getTitle());
        RicettaResponse response = new RicettaResponse(ricetta);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRicettaById(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        User user = (User) authentication.getPrincipal();

        boolean isDeleted = ricettaService.deleteRicettaIfAuthor(id, user);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato a eliminare questa ricetta.");
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<?> modifyRicettaById(@PathVariable Long id, @RequestBody JsonNode requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        Ricetta savedRicetta = this.ricettaService.getRicettaById(id);

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        User author = savedRicetta.getAuthor();

        if (!author.equals(currentUser) && !(currentUser.getRoleList().get(0).getId() == 2)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato a modificare questa ricetta.");
        }

        try {
            String title = requestBody.get("title").asText();
            String description = requestBody.get("description").asText();
            Long userId = requestBody.get("authorId").asLong();


            User autore = (User) userService.loadById(userId);

            if (autore == null) {
                return ResponseEntity.notFound().build();
            }

            // Gestione della lista degli ingredienti

            List<Ingrediente> listaIngredienti = new ArrayList<>();
            JsonNode ingredientiNode = requestBody.get("listaIngredienti");
            if (ingredientiNode != null && ingredientiNode.isArray()) {
                for (final JsonNode ingredientNode : ingredientiNode) {
                    Ingrediente ingrediente = objectMapper.treeToValue(ingredientNode, Ingrediente.class);
                    ingrediente.setRicetta(savedRicetta);
                    listaIngredienti.add(ingrediente);
                }
            } else {
                // Se listaIngredienti non è presente o non è un array, la lista degli ingredienti sarà vuota
                System.out.println("Nessun ingrediente fornito nel JSON.");
                // Puoi scegliere di aggiungere un messaggio di log o altre azioni appropriate
            }


            /*
            Inizializzazione effettiva dei parametri della ricetta
             */

            savedRicetta.setTitle(title);

            // Rimuovi ingredienti non più presenti
            savedRicetta.getListaIngredienti().removeIf(ingrediente -> !listaIngredienti.contains(ingrediente));

            // Aggiungi o aggiorna ingredienti esistenti
            for (Ingrediente nuovoIngrediente : listaIngredienti) {
                if (!savedRicetta.getListaIngredienti().contains(nuovoIngrediente)) {
                    nuovoIngrediente.setRicetta(savedRicetta);
                    savedRicetta.getListaIngredienti().add(nuovoIngrediente);
                }
            }

            savedRicetta.setDescription(description);
            ricettaService.saveRicetta(savedRicetta);

            RicettaResponse response = new RicettaResponse(savedRicetta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log dell'errore
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint per ottenere una ricetta per titolo
    @GetMapping("/byTitle")
    public ResponseEntity<Ricetta> getRicettaByTitle(@RequestParam String title) {
        Ricetta ricetta = ricettaService.getRicettaByName(title);
        if (ricetta != null) {
            return ResponseEntity.ok(ricetta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
