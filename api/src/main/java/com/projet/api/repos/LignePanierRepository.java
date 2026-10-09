package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.LignePanier;

public interface LignePanierRepository extends JpaRepository<LignePanier, Long> {
}