package it.uniroma3.siw.siwfood.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bytea")
    private byte[] data;

    // Uno a uno con User
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Uno a uno con RicettaModel
    @ManyToOne
    @JoinColumn(name = "ricetta_id")
    private Ricetta ricetta;

    /* Getter and setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Lob
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }
}


