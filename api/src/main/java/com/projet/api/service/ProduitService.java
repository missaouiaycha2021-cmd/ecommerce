package com.projet.api.service;

import java.util.List;

import com.projet.api.entities.Produit;

public interface ProduitService {
    Produit saveProduit(Produit produit);
    Produit updateProduit(Produit produit);
    void deleteProduit(Produit produit);
    void deleteProduitById(Long id);
    Produit getProduit(Long id);
    List<Produit> getAllProduits();
}