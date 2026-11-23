package com.example.demo;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ParticipationController {

    private final ParticipationRepository participationRepository;
    private final JoueurRepository joueurRepository;
    private final RencontreRepository rencontreRepository;

    public ParticipationController(ParticipationRepository participationRepository,
            JoueurRepository joueurRepository, RencontreRepository rencontreRepository) {
        this.participationRepository = participationRepository;
        this.joueurRepository = joueurRepository;
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping("/participations")
    public List<ParticipationResponse> getParticipations() {
        return participationRepository.findAll().stream().map(ParticipationResponse::from).toList();
    }

    @GetMapping("/participations/rencontre/{rencontreId}")
    public List<ParticipationResponse> getFeuilleDeMatch(@PathVariable Integer rencontreId) {
        return participationRepository.findByRencontreRencontreId(rencontreId).stream()
                .map(ParticipationResponse::from)
                .toList();
    }

    @GetMapping("/ParticipationAPI.php")
    public ResponseEntity<Boolean> joueurEstSurLaFeuilleDeMatch(@RequestParam("id") Integer rencontreId,
            @RequestParam("idJoueur") Integer joueurId) {
        return ResponseEntity.ok(
                participationRepository.existsByRencontreRencontreIdAndJoueurJoueurId(rencontreId, joueurId));
    }

    @PostMapping("/participation")
    public ResponseEntity<ParticipationResponse> assignerUnParticipant(
            @RequestBody ParticipationRequest request) {
        Participation participation = new Participation();
        participation.setJoueur(findJoueur(request.joueurId()));
        participation.setRencontre(findRencontre(request.rencontreId()));
        participation.setPoste(Poste.valueOf(request.poste()));
        participation.setTitulaireOuRemplacant(
                TitulaireOuRemplacant.valueOf(request.titulaireOuRemplacant()));

        Participation saved = participationRepository.save(participation);
        return ResponseEntity.created(URI.create("/participation/" + saved.getParticipationId()))
                .body(ParticipationResponse.from(saved));
    }

    @PutMapping("/participation/{id}")
    public ResponseEntity<ParticipationResponse> modifierParticipation(@PathVariable Integer id,
            @RequestBody ParticipationUpdateRequest request) {
        return participationRepository.findById(id)
                .map(participation -> {
                    participation.setJoueur(findJoueur(request.joueurId()));
                    participation.setPoste(Poste.valueOf(request.poste()));
                    participation.setTitulaireOuRemplacant(
                            TitulaireOuRemplacant.valueOf(request.titulaireOuRemplacant()));
                    return ResponseEntity.ok(
                            ParticipationResponse.from(participationRepository.save(participation)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/participation/{id}")
    public ResponseEntity<Void> supprimerParticipation(@PathVariable Integer id) {
        if (!participationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        participationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/performances/{participationId}")
    public ResponseEntity<ParticipationResponse> mettreAJourLaPerformance(@PathVariable Integer participationId,
            @RequestBody PerformanceRequest request) {
        return participationRepository.findById(participationId)
                .map(participation -> {
                    participation.setNotePerformance(Performance.valueOfName(request.performance()));
                    return ResponseEntity.ok(
                            ParticipationResponse.from(participationRepository.save(participation)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/performances/{participationId}")
    public ResponseEntity<ParticipationResponse> supprimerLaPerformance(@PathVariable Integer participationId) {
        return participationRepository.findById(participationId)
                .map(participation -> {
                    participation.setNotePerformance(null);
                    return ResponseEntity.ok(
                            ParticipationResponse.from(participationRepository.save(participation)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private Joueur findJoueur(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "joueurId manquant");
        }
        return joueurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Joueur introuvable"));
    }

    private Rencontre findRencontre(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rencontreId manquant");
        }
        return rencontreRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rencontre introuvable"));
    }
}
