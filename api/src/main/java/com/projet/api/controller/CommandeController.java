package com.projet.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.api.entities.Commande;
import com.projet.api.entities.StatutCommande;
import com.projet.api.service.CommandeService;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public List<Commande> getAll() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getById(@PathVariable Long id) {
        Commande c = commandeService.getCommande(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Commande> create(@RequestBody Commande c) {
        // on renseigne le côté propriétaire de chaque ligne
        if (c.getLignes() != null) {
            c.getLignes().forEach(l -> l.setCommande(c));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeService.saveCommande(c));
    }

    // changer seulement le statut : PUT /api/commandes/1/statut?valeur=EN_ATTENTE
    @PutMapping("/{id}/statut")
    public ResponseEntity<Commande> changerStatut(@PathVariable Long id, @RequestParam StatutCommande valeur) {
        Commande c = commandeService.getCommande(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        c.setStatut(valeur);
        return ResponseEntity.ok(commandeService.updateCommande(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (commandeService.getCommande(id) == null) {
            return ResponseEntity.notFound().build();
        }
        commandeService.deleteCommandeById(id);
        return ResponseEntity.noContent().build();
    }
}