package com.projet.api.service;

import java.util.List;

import com.projet.api.entities.Utilisateur;

public interface UtilisateurService {
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateurById(Long id);
    Utilisateur getUtilisateur(Long id);
    List<Utilisateur> getAllUtilisateurs();
    
}