package com.example.demo;

import java.time.format.DateTimeFormatter;

public record RencontreResponse(
        Integer rencontreId,
        String dateHeure,
        String equipeAdverse,
        String adresse,
        String lieu,
        String resultat) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static RencontreResponse from(Rencontre rencontre) {
        return new RencontreResponse(
                rencontre.getRencontreId(),
                rencontre.getDateHeure() == null ? null : rencontre.getDateHeure().format(FORMATTER),
                rencontre.getEquipeAdverse(),
                rencontre.getAdresse(),
                rencontre.getLieu() == null ? null : rencontre.getLieu().name(),
                rencontre.getResultat() == null ? null : rencontre.getResultat().name());
    }
}
