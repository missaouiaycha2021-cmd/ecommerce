package com.projet.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.api.entities.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}