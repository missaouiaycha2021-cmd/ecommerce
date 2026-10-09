package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.Panier;

public interface PanierRepository extends JpaRepository<Panier, Long> {
}