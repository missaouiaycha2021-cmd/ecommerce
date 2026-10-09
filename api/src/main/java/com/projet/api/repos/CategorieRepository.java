package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}