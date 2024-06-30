package it.uniroma3.siw.siwfood.service.foodservices;

import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.model.User;
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

    public List<Ricetta> getRicetteByAuthor(User user) {
        List<Ricetta> ricette = (List<Ricetta>)ricettaRepository.findByAuthor(user);
        return ricette;
    }

    public List<Ricetta> getAllRicette() {
        List<Ricetta> ricette = (List<Ricetta>) ricettaRepository.findAll();
        System.out.println("Numero di ricette trovate: " + ricette.size());
        return ricette;
    }

    public void deleteRicettaById(long id) {
        ricettaRepository.deleteById(id);
    }

    public Ricetta getRicettaById(Long id) {
        return ricettaRepository.findById(id).orElse(null);
    }

    public Ricetta getRicettaByName(String name) {
        return ricettaRepository.findByTitle(name);
    }

    public boolean deleteRicettaIfAuthor(Long ricettaId, User user) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId).orElse(null);
        System.out.println("Id ricetta: " + ricetta.getId());
        System.out.println("Autore: " + ricetta.getAuthor().getEmail());
        System.out.println("Utente: " + user.getEmail());
        if (ricetta != null && ricetta.getAuthor().equals(user)) {
            ricettaRepository.deleteById(ricettaId);
            return true;
        }
        return false;
    }
}
