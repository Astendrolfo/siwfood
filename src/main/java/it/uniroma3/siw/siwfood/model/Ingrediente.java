package it.uniroma3.siw.siwfood.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredienti")
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_ricetta")
    private Ricetta ricetta;
}
