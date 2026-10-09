package com.projet.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lignes_panier",
       uniqueConstraints = @UniqueConstraint(columnNames = {"panier_id", "produit_id"}))
public class LignePanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantite = 1;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "panier_id")
    private Panier panier;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    public LignePanier() {
        super();
    }

    public LignePanier(Panier panier, Produit produit, int quantite) {
        super();
        this.panier = panier;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    @Override
    public String toString() {
        return "LignePanier [id=" + id + ", quantite=" + quantite + "]";
    }
}