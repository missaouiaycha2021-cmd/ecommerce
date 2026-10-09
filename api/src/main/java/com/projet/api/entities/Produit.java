package com.projet.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    @Column(precision = 10, scale = 2)
    private BigDecimal ancienPrix;

    private String tag;      // "Nouveau", "-20 %"
    private String image;    // "images/produit-1.jpg"
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categorie_id")
    @JsonIgnoreProperties({"produits", "hibernateLazyInitializer", "handler"})
    private Categorie categorie;

    public Produit() {
        super();
    }

    public Produit(String nom, BigDecimal prix, BigDecimal ancienPrix, String tag,
                   String image, int stock, Categorie categorie) {
        super();
        this.nom = nom;
        this.prix = prix;
        this.ancienPrix = ancienPrix;
        this.tag = tag;
        this.image = image;
        this.stock = stock;
        this.categorie = categorie;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }

    public BigDecimal getAncienPrix() { return ancienPrix; }
    public void setAncienPrix(BigDecimal ancienPrix) { this.ancienPrix = ancienPrix; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit [id=" + id + ", nom=" + nom + ", prix=" + prix
                + ", ancienPrix=" + ancienPrix + ", tag=" + tag
                + ", image=" + image + ", stock=" + stock + "]";
    }
}