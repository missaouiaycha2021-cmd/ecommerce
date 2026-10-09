package com.projet.api.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lignes_commande")
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantite;

    @Column(precision = 10, scale = 2)
    private BigDecimal prixUnitaire;   // prix au moment de l'achat

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    public LigneCommande() {
        super();
    }

    public LigneCommande(Commande commande, Produit produit, int quantite, BigDecimal prixUnitaire) {
        super();
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    @Override
    public String toString() {
        return "LigneCommande [id=" + id + ", quantite=" + quantite
                + ", prixUnitaire=" + prixUnitaire + "]";
    }
}