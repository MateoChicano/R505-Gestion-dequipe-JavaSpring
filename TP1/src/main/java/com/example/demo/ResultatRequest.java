package com.example.demo;

public record ResultatRequest(String resultat) {

    public RencontreResultat parsedResultat() {
        if (resultat == null || resultat.isBlank()) {
            return null;
        }
        return RencontreResultat.valueOf(resultat);
    }
}
