package it.uniroma3.siw.siwfood.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@Entity
@Table(name = "ricette")
public class Ricetta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //identificatore
    private String title; //titolo della ricetta

    @ManyToOne
    @JoinColumn (name = "id_autore")
    private User author;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> listaIngredienti = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingrediente> getIngredients() {
        return listaIngredienti;
    }

    public void setIngredients(List<Ingrediente> ingredients) {
        this.listaIngredienti = ingredients;
    }

    /*@Override
    public int hashCode() {
        return Objects.hash(title, year);
    }*/

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        return Objects.equals(title, other.title) && year.equals(other.year);
    }*/}