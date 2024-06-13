package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.service.foodservices.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    // Endpoint per creare una nuova ricetta
    @PostMapping
    public ResponseEntity<Ricetta> createRicetta(@RequestBody Ricetta ricetta) {
        Ricetta savedRicetta = ricettaService.saveRicetta(ricetta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRicetta);
    }

    // Endpoint per ottenere tutte le ricette
    @GetMapping
    public ResponseEntity<List<Ricetta>> getAllRicette() {
        List<Ricetta> ricette = ricettaService.getAllRicette();
        return ResponseEntity.ok(ricette);
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
