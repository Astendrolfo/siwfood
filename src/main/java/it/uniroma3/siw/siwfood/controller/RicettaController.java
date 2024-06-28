package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.response.RicettaResponse;
import it.uniroma3.siw.siwfood.service.foodservices.RicettaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    private final RicettaService ricettaService;

    public RicettaController(RicettaService ricettaService) {
        this.ricettaService = ricettaService;
    }

    @PostMapping("/addricettasium")
    public ResponseEntity<Ricetta> createRicetta(@RequestBody Ricetta ricetta) {
        System.out.println("Aggiungo una nuova ricetta");
        try {
            Ricetta savedRicetta = new Ricetta();
            savedRicetta.setTitle(ricetta.getTitle());
            savedRicetta.setAuthor(ricetta.getAuthor());
            savedRicetta.setImage(ricetta.getImage());
            savedRicetta.setListaIngredienti(ricetta.getListaIngredienti());
            savedRicetta.setDescription(ricetta.getDescription());

            // Salva la ricetta nel database usando il servizio ricettaService
            ricettaService.saveRicetta(savedRicetta);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedRicetta);
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
