package it.uniroma3.siw.siwfood.service.foodservices;

import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RicettaService {

    @Autowired
    private RicettaRepository ricettaRepository;

    public Ricetta saveRicetta(Ricetta ricetta) {
        return ricettaRepository.save(ricetta);
    }

    public List<Ricetta> getAllRicette() {
        List<Ricetta> ricette = (List<Ricetta>) ricettaRepository.findAll();
        System.out.println("Numero di ricette trovate: " + ricette.size());
        return ricette;
    }

    public Ricetta getRicettaById(Long id) {
        return ricettaRepository.findById(id).orElse(null);
    }

    public Ricetta getRicettaByName(String name) {
        return ricettaRepository.findByTitle(name);
    }
}
