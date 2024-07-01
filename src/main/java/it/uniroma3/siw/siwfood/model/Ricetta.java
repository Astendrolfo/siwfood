package it.uniroma3.siw.siwfood.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.*;

@EnableAutoConfiguration
@Entity
@Table(name = "ricette")
public class Ricetta {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identificatore

    private String title; // titolo della ricetta

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_autore")
    private User author;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> listaIngredienti = new ArrayList<>();

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL)
    @OrderColumn
    private List<Image> images;

    public Ricetta() {
        this.listaIngredienti = new ArrayList<>();
        this.images = new ArrayList<>();
        Image image1 = new Image();
        Image image2 = new Image();
        Image image3 = new Image();
        image1.setRicetta(this);
        image2.setRicetta(this);
        image3.setRicetta(this);
        this.images.add(image1);
        this.images.add(image2);
        this.images.add(image3);
    }

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
