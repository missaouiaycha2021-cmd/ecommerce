package com.projet.api.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse;   // hash BCrypt, jamais en clair

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CLIENT;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Panier panier;

    @OneToMany(mappedBy = "utilisateur")
    private List<Commande> commandes = new ArrayList<>();

    public Utilisateur() {
        super();
    }

    public Utilisateur(String email, String motDePasse, String nom, String prenom,
                       String telephone, String adresse, Role role) {
        super();
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }

    public List<Commande> getCommandes() { return commandes; }
    public void setCommandes(List<Commande> commandes) { this.commandes = commandes; }

    // le mot de passe n'est volontairement pas affiché
    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", email=" + email + ", nom=" + nom
                + ", prenom=" + prenom + ", telephone=" + telephone
                + ", adresse=" + adresse + ", role=" + role + "]";
    }
}