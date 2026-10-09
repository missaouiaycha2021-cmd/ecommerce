package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}