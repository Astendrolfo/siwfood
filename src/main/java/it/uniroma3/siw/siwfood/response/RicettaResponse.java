package it.uniroma3.siw.siwfood.response;

import it.uniroma3.siw.siwfood.model.Ingrediente;
import it.uniroma3.siw.siwfood.model.Ricetta;
import java.util.List;

public class RicettaResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final Long authorId;
    private final String authorName;
    private final List<Ingrediente> listaIngredienti;
    private final byte[] immagine;

    public RicettaResponse(Ricetta ricetta) {
        this.id = ricetta.getId();
        this.title = ricetta.getTitle();
        this.description = ricetta.getDescription();
        this.authorId = ricetta.getAuthor().getId();
        this.authorName = ricetta.getAuthor().getNome();
        this.listaIngredienti = ricetta.getListaIngredienti();
        this.immagine = (ricetta.getImage() != null) ? ricetta.getImage().getData() : new byte[0];
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public List<Ingrediente> getListaIngredienti() {
        return listaIngredienti;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public Long getId() {
        return id;
    }
}

