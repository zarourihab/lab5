package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    @NotNull
    private Integer capacite;

    private String batiment;
    private Integer etage;

    @ManyToMany
    @JoinTable(
            name = "salle_equipement",
            joinColumns = @JoinColumn(name = "salle_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    private Set<Equipement> equipements = new HashSet<>();

    public Salle() {}

    public Salle(String nom, Integer capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    public void addEquipement(Equipement e) {
        equipements.add(e);
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public Integer getCapacite() { return capacite; }
    public String getBatiment() { return batiment; }
    public Integer getEtage() { return etage; }

    public void setNom(String nom) { this.nom = nom; }
    public void setCapacite(Integer capacite) { this.capacite = capacite; }
    public void setBatiment(String batiment) { this.batiment = batiment; }
    public void setEtage(Integer etage) { this.etage = etage; }
}