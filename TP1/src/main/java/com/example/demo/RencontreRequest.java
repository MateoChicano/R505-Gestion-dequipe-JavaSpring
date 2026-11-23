package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record RencontreRequest(String dateEtHeure, String equipeAdverse, String adresse, String lieu) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime dateHeure() {
        if (dateEtHeure == null || dateEtHeure.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateEtHeure, FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(dateEtHeure);
            } catch (DateTimeParseException e2) {
                return LocalDate.parse(dateEtHeure).atStartOfDay();
            }
        }
    }

    public RencontreLieu parsedLieu() {
        if (lieu == null || lieu.isBlank()) {
            return null;
        }
        return RencontreLieu.valueOf(lieu);
    }
}
