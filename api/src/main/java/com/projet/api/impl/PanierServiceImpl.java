package com.projet.api.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.api.entities.Panier;
import com.projet.api.repos.PanierRepository;
import com.projet.api.service.PanierService;

@Service
public class PanierServiceImpl implements PanierService {

    private final PanierRepository panierRepository;

    public PanierServiceImpl(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    @Override
    public Panier savePanier(Panier panier) {
        return panierRepository.save(panier);
    }

    @Override
    public Panier updatePanier(Panier panier) {
        return panierRepository.save(panier);
    }

    @Override
    public void deletePanier(Panier panier) {
        panierRepository.delete(panier);
    }

    @Override
    public void deletePanierById(Long id) {
        panierRepository.deleteById(id);
    }

    @Override
    public Panier getPanier(Long id) {
    return panierRepository.findById(id).orElse(null);
}

    @Override
    public List<Panier> getAllPaniers() {
        return panierRepository.findAll();
    }
    @Override
public Panier getPanierByUtilisateur(Long utilisateurId) {
    return panierRepository.findAll().stream()
            .filter(p -> p.getUtilisateur() != null
                      && p.getUtilisateur().getId().equals(utilisateurId))
            .findFirst().orElse(null);
}

@Override
public Panier ajouterProduit(Long utilisateurId, Long produitId, int quantite) {
    throw new UnsupportedOperationException("À implémenter");
}

@Override
public Panier retirerProduit(Long utilisateurId, Long produitId) {
    throw new UnsupportedOperationException("À implémenter");
}

@Override
public void viderPanier(Long utilisateurId) {
    throw new UnsupportedOperationException("À implémenter");
}
}