package com.projet.api.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.api.entities.Utilisateur;
import com.projet.api.repos.UtilisateurRepository;
import com.projet.api.service.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public void deleteUtilisateurById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public Utilisateur getUtilisateur(Long id) {
        return utilisateurRepository.findById(id).get();
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
}