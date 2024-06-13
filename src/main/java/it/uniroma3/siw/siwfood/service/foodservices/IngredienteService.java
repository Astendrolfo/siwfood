package it.uniroma3.siw.siwfood.service.foodservices;

import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Ingrediente saveIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> getAllIngredienti() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente getIngredienteById(Long id) {
        return ingredienteRepository.findById(id).orElse(null);
    }
}
