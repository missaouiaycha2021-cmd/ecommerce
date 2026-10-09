package com.projet.api.service;

import java.util.List;

import com.projet.api.entities.Commande;

public interface CommandeService {
    Commande saveCommande(Commande commande);
    Commande updateCommande(Commande commande);
    void deleteCommande(Commande commande);
    void deleteCommandeById(Long id);
    Commande getCommande(Long id);
    List<Commande> getAllCommandes();
}