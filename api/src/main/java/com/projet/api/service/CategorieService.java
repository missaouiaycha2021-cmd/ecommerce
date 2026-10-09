package com.projet.api.service;

import java.util.List;

import com.projet.api.entities.Categorie;

public interface CategorieService {
    Categorie saveCategorie(Categorie categorie);
    Categorie updateCategorie(Categorie categorie);
    void deleteCategorie(Categorie categorie);
    void deleteCategorieById(Long id);
    Categorie getCategorie(Long id);
    List<Categorie> getAllCategories();
}