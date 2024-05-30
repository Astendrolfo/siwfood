package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.model.Ricetta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.siwfood.service.FoodService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/ricette")
    public List<Ricetta> getListaRicette() {
        return (List<Ricetta>) this.foodService.findAll();
    }

    @GetMapping("/ricetta/{id}")
    public Ricetta getRicettaById(@PathVariable("id") int id) {
        return (Ricetta) this.foodService.findById(id);
    }
}
