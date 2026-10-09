package com.projet.api.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.api.entities.Produit;
import com.projet.api.repos.ProduitRepository;
import com.projet.api.service.ProduitService;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public Produit updateProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public void deleteProduit(Produit produit) {
        produitRepository.delete(produit);
    }

    @Override
    public void deleteProduitById(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit getProduit(Long id) {
        return produitRepository.findById(id).orElse(null);   // <-- corrigé
    }

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
}