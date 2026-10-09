package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.LigneCommande;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}