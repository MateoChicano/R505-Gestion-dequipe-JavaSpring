package com.example.demo;

public record ParticipationRequest(
        Integer joueurId,
        Integer rencontreId,
        String poste,
        String titulaireOuRemplacant) {
}
