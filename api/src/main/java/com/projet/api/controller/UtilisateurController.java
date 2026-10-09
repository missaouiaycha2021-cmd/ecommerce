package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.api.entities.Panier;
import com.projet.api.entities.Utilisateur;
import com.projet.api.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        Utilisateur u = utilisateurService.getUtilisateur(id);
        return u != null ? ResponseEntity.ok(u) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur u) {
        u.setPanier(new Panier(u));   // le panier est créé avec l'utilisateur
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurService.saveUtilisateur(u));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @RequestBody Utilisateur u) {
        Utilisateur existant = utilisateurService.getUtilisateur(id);
        if (existant == null) {
            return ResponseEntity.notFound().build();
        }
        u.setId(id);
        u.setPanier(existant.getPanier());
        return ResponseEntity.ok(utilisateurService.updateUtilisateur(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (utilisateurService.getUtilisateur(id) == null) {
            return ResponseEntity.notFound().build();
        }
        utilisateurService.deleteUtilisateurById(id);
        return ResponseEntity.noContent().build();
    }
}