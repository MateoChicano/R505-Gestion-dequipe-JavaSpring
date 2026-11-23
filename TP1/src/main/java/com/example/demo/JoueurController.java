package com.example.demo;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joueurs")
public class JoueurController {

    private final JoueurRepository joueurRepository;

    public JoueurController(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    @GetMapping
    public List<Joueur> getJoueurs() {
        return joueurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joueur> getJoueur(@PathVariable Integer id) {
        return joueurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Joueur> createJoueur(@RequestBody Joueur joueur) {
        joueur.setJoueurId(null);
        Joueur saved = joueurRepository.save(joueur);
        return ResponseEntity.created(URI.create("/joueurs/" + saved.getJoueurId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joueur> updateJoueur(@PathVariable Integer id, @RequestBody Joueur joueur) {
        return joueurRepository.findById(id)
                .map(existing -> {
                    joueur.setJoueurId(id);
                    return ResponseEntity.ok(joueurRepository.save(joueur));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoueur(@PathVariable Integer id) {
        if (!joueurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        joueurRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
