package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Integer participationId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "joueur_id")
    private Joueur joueur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rencontre_id")
    private Rencontre rencontre;

    @Enumerated(EnumType.STRING)
    @Column(name = "titulaire_ou_remplacant")
    private TitulaireOuRemplacant titulaireOuRemplacant;

    @Enumerated(EnumType.STRING)
    private Poste poste;

    @Column(name = "note_performance")
    private Integer notePerformance;

    public Integer getParticipationId() {
        return participationId;
    }

    public void setParticipationId(Integer participationId) {
        this.participationId = participationId;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Rencontre getRencontre() {
        return rencontre;
    }

    public void setRencontre(Rencontre rencontre) {
        this.rencontre = rencontre;
    }

    public TitulaireOuRemplacant getTitulaireOuRemplacant() {
        return titulaireOuRemplacant;
    }

    public void setTitulaireOuRemplacant(TitulaireOuRemplacant titulaireOuRemplacant) {
        this.titulaireOuRemplacant = titulaireOuRemplacant;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Integer getNotePerformance() {
        return notePerformance;
    }

    public void setNotePerformance(Integer notePerformance) {
        this.notePerformance = notePerformance;
    }
}
