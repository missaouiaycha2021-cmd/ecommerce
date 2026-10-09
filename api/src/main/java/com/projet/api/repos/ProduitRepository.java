package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}