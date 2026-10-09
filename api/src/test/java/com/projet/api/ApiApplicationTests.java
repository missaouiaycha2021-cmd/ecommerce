package com.projet.api;

import java.math.BigDecimal;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projet.api.entities.Categorie;
import com.projet.api.entities.Commande;
import com.projet.api.entities.LigneCommande;
import com.projet.api.entities.Panier;
import com.projet.api.entities.Produit;
import com.projet.api.entities.Role;
import com.projet.api.entities.StatutCommande;
import com.projet.api.entities.Utilisateur;
import com.projet.api.repos.CategorieRepository;
import com.projet.api.repos.CommandeRepository;
import com.projet.api.repos.PanierRepository;
import com.projet.api.repos.ProduitRepository;
import com.projet.api.repos.UtilisateurRepository;
import com.projet.api.service.CategorieService;
import com.projet.api.service.CommandeService;
import com.projet.api.service.PanierService;
import com.projet.api.service.ProduitService;
import com.projet.api.service.UtilisateurService;

@SpringBootTest
class ApiApplicationTests {

    // ===== Tests des repositories (comme dans le TP) =====
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private CommandeRepository commandeRepository;

    // ===== Tests des services =====
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PanierService panierService;
    @Autowired
    private CommandeService commandeService;

    // nom / email uniques pour pouvoir relancer les tests plusieurs fois
    private String unique(String base) {
        return base + System.currentTimeMillis();
    }

    private Categorie creerCategorie() {
        return categorieRepository.save(new Categorie(unique("Cat")));
    }

    private Produit creerProduit() {
        return produitRepository.save(new Produit("PC Dell", new BigDecimal("2200.50"),
                null, "Nouveau", "images/pc.jpg", 10, creerCategorie()));
    }

    private Utilisateur creerUtilisateur() {
        Utilisateur u = new Utilisateur(unique("client") + "@mail.com", "hash", "Ben Ali",
                "Ahmed", "20123456", "Tunis", Role.CLIENT);
        u.setPanier(new Panier(u));          // le panier est enregistré par cascade
        return utilisateurRepository.save(u);
    }

    // ================= CATEGORIE =================
    @Test
    public void testCreateCategorie() {
        Categorie c = categorieRepository.save(new Categorie(unique("Informatique")));
        System.out.println(c);
    }

    @Test
    public void testFindCategorie() {
        Categorie c = creerCategorie();
        System.out.println(categorieRepository.findById(c.getId()).get());
    }

    @Test
    public void testUpdateCategorie() {
        Categorie c = creerCategorie();
        c.setNom(unique("Modifiee"));
        categorieRepository.save(c);
    }

    @Test
    public void testDeleteCategorie() {
        Categorie c = creerCategorie();
        categorieRepository.deleteById(c.getId());
    }

    @Test
    public void testListerToutesCategories() {
        List<Categorie> cats = categorieRepository.findAll();
        for (Categorie c : cats) {
            System.out.println(c);
        }
    }

    // ================= PRODUIT =================
    @Test
    public void testCreateProduit() {
        Produit p = new Produit("PC Dell", new BigDecimal("2200.50"), null, "Nouveau",
                "images/pc.jpg", 10, creerCategorie());
        produitRepository.save(p);
    }

    @Test
    public void testFindProduit() {
        Produit p = creerProduit();
        System.out.println(produitRepository.findById(p.getId()).get());
    }

    @Test
    public void testUpdateProduit() {
        Produit p = creerProduit();
        p.setPrix(new BigDecimal("1000.00"));
        produitRepository.save(p);
    }

    @Test
    public void testDeleteProduit() {
        Produit p = creerProduit();
        produitRepository.deleteById(p.getId());
    }

    @Test
    public void testListerTousProduits() {
        List<Produit> prods = produitRepository.findAll();
        for (Produit p : prods) {
            System.out.println(p);
        }
    }

    // ================= UTILISATEUR =================
    @Test
    public void testCreateUtilisateur() {
        Utilisateur u = creerUtilisateur();
        System.out.println(u);
    }

    @Test
    public void testFindUtilisateur() {
        Utilisateur u = creerUtilisateur();
        System.out.println(utilisateurRepository.findById(u.getId()).get());
    }

    @Test
    public void testUpdateUtilisateur() {
        Utilisateur u = creerUtilisateur();
        u.setAdresse("Sfax");
        utilisateurRepository.save(u);
    }

    @Test
    public void testListerTousUtilisateurs() {
        List<Utilisateur> users = utilisateurRepository.findAll();
        for (Utilisateur u : users) {
            System.out.println(u);
        }
    }

    // ================= PANIER =================
    @Test
    public void testPanierCreeAvecUtilisateur() {
        Utilisateur u = creerUtilisateur();
        System.out.println(u.getPanier());
        System.out.println(panierRepository.findAll());
    }

    @Test
    public void testListerTousPaniers() {
        List<Panier> paniers = panierRepository.findAll();
        for (Panier p : paniers) {
            System.out.println(p);
        }
    }

    // ================= COMMANDE =================
    @Test
    public void testCreateCommande() {
        Utilisateur u = creerUtilisateur();
        Produit p = creerProduit();

        Commande c = new Commande(u, new BigDecimal("4401.00"), StatutCommande.EN_ATTENTE);
        // on renseigne les deux côtés de la relation
        LigneCommande ligne = new LigneCommande(c, p, 2, p.getPrix());
        c.getLignes().add(ligne);

        commandeRepository.save(c);   // les lignes sont enregistrées par cascade
    }

    @Test
    public void testListerToutesCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        for (Commande c : commandes) {
            System.out.println(c);
        }
    }

    // ================= COUCHE SERVICE =================
    @Test
    public void testServiceProduit() {
        Produit p = produitService.saveProduit(new Produit("Souris", new BigDecimal("25.00"),
                null, null, null, 50, creerCategorie()));

        p.setPrix(new BigDecimal("20.00"));
        produitService.updateProduit(p);

        System.out.println(produitService.getProduit(p.getId()));
        System.out.println(produitService.getAllProduits());

        produitService.deleteProduitById(p.getId());
    }

    @Test
    public void testServiceCategorie() {
        Categorie c = categorieService.saveCategorie(new Categorie(unique("Service")));
        c.setNom(unique("ServiceMaj"));
        categorieService.updateCategorie(c);

        System.out.println(categorieService.getCategorie(c.getId()));
        System.out.println(categorieService.getAllCategories());

        categorieService.deleteCategorie(c);
    }

    @Test
    public void testServiceUtilisateur() {
        Utilisateur u = utilisateurService.saveUtilisateur(creerUtilisateurSansSauvegarde());
        System.out.println(utilisateurService.getUtilisateur(u.getId()));
        System.out.println(utilisateurService.getAllUtilisateurs());
    }

    @Test
    public void testServicePanierEtCommande() {
        System.out.println(panierService.getAllPaniers());
        System.out.println(commandeService.getAllCommandes());
    }

    private Utilisateur creerUtilisateurSansSauvegarde() {
        Utilisateur u = new Utilisateur(unique("svc") + "@mail.com", "hash", "Trabelsi",
                "Sara", "55123456", "Sousse", Role.CLIENT);
        u.setPanier(new Panier(u));
        return u;
    }
}