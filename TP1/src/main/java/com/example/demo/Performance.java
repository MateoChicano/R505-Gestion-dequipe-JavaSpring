package com.example.demo;

public enum Performance {
    CATASTROPHIQUE(1),
    MAUVAISE(2),
    MOYENNE(3),
    BONNE(4),
    EXCELLENTE(5);

    private final int value;

    Performance(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Integer valueOfName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return Performance.valueOf(name).getValue();
    }
}
