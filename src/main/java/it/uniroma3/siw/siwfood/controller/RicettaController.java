package it.uniroma3.siw.siwfood.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.uniroma3.siw.siwfood.model.Image;
import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.response.RicettaResponse;
import it.uniroma3.siw.siwfood.service.UserService;
import it.uniroma3.siw.siwfood.service.foodservices.ImageService;
import it.uniroma3.siw.siwfood.service.foodservices.RicettaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/addricettasium")
    public ResponseEntity<RicettaResponse> createRicetta(@RequestBody JsonNode requestBody) {
        System.out.println("Aggiungo una nuova ricetta");
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


    // Endpoint per ottenere tutte le ricette
    @GetMapping
    public ResponseEntity<List<RicettaResponse>> getAllRicette() {
        List<Ricetta> ricette = ricettaService.getAllRicette();
        List<RicettaResponse> ricetteResponse = ricette.stream()
                .map(RicettaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ricetteResponse);
    }

    // Endpoint per ottenere una ricetta per ID
    @GetMapping("/{id}")
    public ResponseEntity<Ricetta> getRicettaById(@PathVariable Long id) {
        Ricetta ricetta = ricettaService.getRicettaById(id);
        if (ricetta != null) {
            return ResponseEntity.ok(ricetta);
        } else {
            return ResponseEntity.notFound().build();
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
