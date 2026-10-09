package com.projet.api.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.api.entities.Categorie;
import com.projet.api.repos.CategorieRepository;
import com.projet.api.service.CategorieService;

@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie updateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void deleteCategorie(Categorie categorie) {
        categorieRepository.delete(categorie);
    }

    @Override
    public void deleteCategorieById(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    public Categorie getCategorie(Long id) {
        return categorieRepository.findById(id).get();
    }

    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }
}