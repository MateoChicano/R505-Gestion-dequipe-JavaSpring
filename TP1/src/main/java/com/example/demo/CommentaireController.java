package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private final CommentaireRepository commentaireRepository;
    private final JoueurRepository joueurRepository;

    public CommentaireController(CommentaireRepository commentaireRepository, JoueurRepository joueurRepository) {
        this.commentaireRepository = commentaireRepository;
        this.joueurRepository = joueurRepository;
    }

    @PostMapping
    public ResponseEntity<CommentaireResponse> ajouterCommentaire(@RequestBody CommentaireRequest request) {
        Integer joueurId;
        try {
            joueurId = Integer.valueOf(request.joueurId());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "joueurId invalide");
        }

        Joueur joueur = joueurRepository.findById(joueurId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Joueur introuvable"));

        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(request.contenu());
        commentaire.setDate(LocalDate.now());
        commentaire.setJoueur(joueur);

        return ResponseEntity.ok(CommentaireResponse.from(commentaireRepository.save(commentaire)));
    }

    @GetMapping("/joueur/{joueurId}")
    public List<CommentaireResponse> getCommentairesDuJoueur(@PathVariable Integer joueurId) {
        return commentaireRepository.findByJoueurJoueurId(joueurId).stream()
                .map(CommentaireResponse::from)
                .toList();
    }

    @DeleteMapping("/{commentaireId}")
    public ResponseEntity<Void> supprimerCommentaire(@PathVariable Integer commentaireId) {
        if (!commentaireRepository.existsById(commentaireId)) {
            return ResponseEntity.notFound().build();
        }
        commentaireRepository.deleteById(commentaireId);
        return ResponseEntity.ok().build();
    }
}
