package com.projet.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.api.entities.Panier;
import com.projet.api.service.PanierService;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @GetMapping
    public List<Panier> getAll() {
        return panierService.getAllPaniers();
    }

    @GetMapping("/{utilisateurId}")
    public ResponseEntity<Panier> getPanier(@PathVariable Long utilisateurId) {
        Panier p = panierService.getPanierByUtilisateur(utilisateurId);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    // POST /api/paniers/1/produits?produitId=3&quantite=2
    @PostMapping("/{utilisateurId}/produits")
    public Panier ajouterProduit(@PathVariable Long utilisateurId,
                                 @RequestParam Long produitId,
                                 @RequestParam(defaultValue = "1") int quantite) {
        return panierService.ajouterProduit(utilisateurId, produitId, quantite);
    }

    @DeleteMapping("/{utilisateurId}/produits/{produitId}")
    public Panier retirerProduit(@PathVariable Long utilisateurId, @PathVariable Long produitId) {
        return panierService.retirerProduit(utilisateurId, produitId);
    }

    @DeleteMapping("/{utilisateurId}")
    public ResponseEntity<Void> vider(@PathVariable Long utilisateurId) {
        panierService.viderPanier(utilisateurId);
        return ResponseEntity.noContent().build();
    }
}