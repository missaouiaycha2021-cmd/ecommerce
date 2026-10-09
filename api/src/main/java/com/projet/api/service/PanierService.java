package com.projet.api.service;

import java.util.List;

import com.projet.api.entities.Panier;

public interface PanierService {
    Panier savePanier(Panier panier);
    Panier updatePanier(Panier panier);
    void deletePanier(Panier panier);
    void deletePanierById(Long id);
    Panier getPanier(Long id);
    List<Panier> getAllPaniers();
    Panier getPanierByUtilisateur(Long utilisateurId);
    Panier ajouterProduit(Long utilisateurId, Long produitId, int quantite);
    Panier retirerProduit(Long utilisateurId, Long produitId);
    void viderPanier(Long utilisateurId);
}