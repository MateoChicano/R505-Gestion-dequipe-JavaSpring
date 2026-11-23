package com.example.demo;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rencontres")
public class RencontreController {

    private final RencontreRepository rencontreRepository;

    public RencontreController(RencontreRepository rencontreRepository) {
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping
    public List<RencontreResponse> getRencontres() {
        return rencontreRepository.findAll().stream().map(RencontreResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RencontreResponse> getRencontre(@PathVariable Integer id) {
        return rencontreRepository.findById(id)
                .map(rencontre -> ResponseEntity.ok(RencontreResponse.from(rencontre)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RencontreResponse> createRencontre(@RequestBody RencontreRequest request) {
        Rencontre rencontre = new Rencontre();
        applyRequest(rencontre, request);
        Rencontre saved = rencontreRepository.save(rencontre);
        return ResponseEntity.created(URI.create("/rencontres/" + saved.getRencontreId()))
                .body(RencontreResponse.from(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RencontreResponse> updateRencontre(@PathVariable Integer id,
            @RequestBody RencontreRequest request) {
        return rencontreRepository.findById(id)
                .map(rencontre -> {
                    applyRequest(rencontre, request);
                    return ResponseEntity.ok(RencontreResponse.from(rencontreRepository.save(rencontre)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RencontreResponse> enregistrerResultat(@PathVariable Integer id,
            @RequestBody ResultatRequest request) {
        return rencontreRepository.findById(id)
                .map(rencontre -> {
                    rencontre.setResultat(request.parsedResultat());
                    return ResponseEntity.ok(RencontreResponse.from(rencontreRepository.save(rencontre)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRencontre(@PathVariable Integer id) {
        if (!rencontreRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rencontreRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void applyRequest(Rencontre rencontre, RencontreRequest request) {
        rencontre.setDateHeure(request.dateHeure());
        rencontre.setEquipeAdverse(request.equipeAdverse());
        rencontre.setAdresse(request.adresse());
        rencontre.setLieu(request.parsedLieu());
    }
}
