package it.uniroma3.siw.siwfood.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableAutoConfiguration
@Entity
@Table(name = "ricette")
public class Ricetta {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identificatore

    private String title; // titolo della ricetta
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_autore")
    private User author;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> listaIngredienti = new ArrayList<>();

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingrediente> getListaIngredienti() {
        return listaIngredienti;
    }

    public void setListaIngredienti(List<Ingrediente> listaIngredienti) {
        this.listaIngredienti = listaIngredienti;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        this.listaIngredienti.add(ingrediente);
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image newImage) {
        this.images.add(newImage);
    }


    public void replaceImage(int index, Image image) {
        this.images.set(index, image);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setRicetta(null);
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
