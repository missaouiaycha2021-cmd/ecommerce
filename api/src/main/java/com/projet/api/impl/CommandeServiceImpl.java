package com.projet.api.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.api.entities.Commande;
import com.projet.api.repos.CommandeRepository;
import com.projet.api.service.CommandeService;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Commande saveCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public Commande updateCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public void deleteCommande(Commande commande) {
        commandeRepository.delete(commande);
    }

    @Override
    public void deleteCommandeById(Long id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public Commande getCommande(Long id) {
        return commandeRepository.findById(id).get();
    }

    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
}