package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rencontre")
public class Rencontre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rencontre_id")
    private Integer rencontreId;

    @Column(name = "date_heure")
    private LocalDateTime dateHeure;

    @Column(name = "equipe_adverse")
    private String equipeAdverse;

    private String adresse;

    @Enumerated(EnumType.STRING)
    private RencontreLieu lieu;

    @Enumerated(EnumType.STRING)
    private RencontreResultat resultat;

    public Integer getRencontreId() {
        return rencontreId;
    }

    public void setRencontreId(Integer rencontreId) {
        this.rencontreId = rencontreId;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getEquipeAdverse() {
        return equipeAdverse;
    }

    public void setEquipeAdverse(String equipeAdverse) {
        this.equipeAdverse = equipeAdverse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public RencontreLieu getLieu() {
        return lieu;
    }

    public void setLieu(RencontreLieu lieu) {
        this.lieu = lieu;
    }

    public RencontreResultat getResultat() {
        return resultat;
    }

    public void setResultat(RencontreResultat resultat) {
        this.resultat = resultat;
    }
}
