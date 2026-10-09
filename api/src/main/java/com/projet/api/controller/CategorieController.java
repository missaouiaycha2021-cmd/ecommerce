package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.api.entities.Categorie;
import com.projet.api.service.CategorieService;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public List<Categorie> getAll() {
        return categorieService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getById(@PathVariable Long id) {
        Categorie c = categorieService.getCategorie(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Categorie> create(@RequestBody Categorie c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.saveCategorie(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> update(@PathVariable Long id, @RequestBody Categorie c) {
        if (categorieService.getCategorie(id) == null) {
            return ResponseEntity.notFound().build();
        }
        c.setId(id);
        return ResponseEntity.ok(categorieService.updateCategorie(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Categorie c = categorieService.getCategorie(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        categorieService.deleteCategorie(c);
        return ResponseEntity.noContent().build();
    }
}