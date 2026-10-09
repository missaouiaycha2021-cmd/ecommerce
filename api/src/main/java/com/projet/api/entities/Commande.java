package com.projet.api.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande = LocalDateTime.now();

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut = StatutCommande.EN_ATTENTE;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignes = new ArrayList<>();

    public Commande() {
        super();
    }

    public Commande(Utilisateur utilisateur, BigDecimal total, StatutCommande statut) {
        super();
        this.utilisateur = utilisateur;
        this.total = total;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public StatutCommande getStatut() { return statut; }
    public void setStatut(StatutCommande statut) { this.statut = statut; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }

    // ===== méthodes utilitaires =====
    public void ajouterLigne(LigneCommande ligne) {
        lignes.add(ligne);
        ligne.setCommande(this);
    }

    public void supprimerLigne(LigneCommande ligne) {
        lignes.remove(ligne);
        ligne.setCommande(null);
    }

    @Override
    public String toString() {
        return "Commande [id=" + id + ", dateCommande=" + dateCommande
                + ", total=" + total + ", statut=" + statut + "]";
    }
}