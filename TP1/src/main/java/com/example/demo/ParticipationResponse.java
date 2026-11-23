package com.example.demo;

public record ParticipationResponse(
        Integer participationId,
        Joueur joueur,
        RencontreResponse rencontre,
        String titulaireOuRemplacant,
        Integer performance,
        String poste) {

    public static ParticipationResponse from(Participation participation) {
        return new ParticipationResponse(
                participation.getParticipationId(),
                participation.getJoueur(),
                participation.getRencontre() == null ? null : RencontreResponse.from(participation.getRencontre()),
                participation.getTitulaireOuRemplacant() == null ? null : participation.getTitulaireOuRemplacant().name(),
                participation.getNotePerformance(),
                participation.getPoste() == null ? null : participation.getPoste().name());
    }
}
