package it.uniroma3.siw.siwfood.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredienti")
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "quantita", nullable = false)
    private Long quantita;

    @Column(name = "tipoQuantita")
    private String tipoQuantita;

    @ManyToOne
    @JoinColumn(name = "id_ricetta")
    private Ricetta ricetta;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setQuantita(Long quantita) {
        this.quantita = quantita;
    }

    public Long getQuantita() {
        return quantita;
    }

    public Long getId() {
        return id;
    }

    public void setTipoQuantita(String tipoQuantita) {
        this.tipoQuantita = tipoQuantita;
    }

    public String getTipoQuantita() {
        return tipoQuantita;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }
}
