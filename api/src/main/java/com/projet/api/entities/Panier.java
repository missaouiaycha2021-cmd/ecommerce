package com.projet.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paniers")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToOne(optional = false)
    @JoinColumn(name = "utilisateur_id", unique = true)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignes = new ArrayList<>();

    public Panier() {
        super();
    }

    public Panier(Utilisateur utilisateur) {
        super();
        this.utilisateur = utilisateur;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) { this.lignes = lignes; }

    // ===== méthodes utilitaires =====
    public void ajouterLigne(LignePanier ligne) {
        lignes.add(ligne);
        ligne.setPanier(this);
    }

    public void supprimerLigne(LignePanier ligne) {
        lignes.remove(ligne);
        ligne.setPanier(null);
    }

    @Override
    public String toString() {
        return "Panier [id=" + id + ", dateCreation=" + dateCreation + "]";
    }
}