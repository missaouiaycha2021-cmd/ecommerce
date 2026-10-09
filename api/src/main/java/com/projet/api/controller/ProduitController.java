package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projet.api.entities.Produit;
import com.projet.api.service.ProduitService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public List<Produit> getAll() {
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Long id) {
        Produit p = produitService.getProduit(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Produit> create(@RequestBody Produit p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.saveProduit(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Long id, @RequestBody Produit p) {
        if (produitService.getProduit(id) == null) {
            return ResponseEntity.notFound().build();
        }
        p.setId(id);
        return ResponseEntity.ok(produitService.updateProduit(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (produitService.getProduit(id) == null) {
            return ResponseEntity.notFound().build();
        }
        produitService.deleteProduitById(id);
        return ResponseEntity.noContent().build();
    }
}