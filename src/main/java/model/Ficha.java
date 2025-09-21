package model;

public enum Ficha {
    X("❌"),
    O("⭕");

    private final String simbolo;

    Ficha(String simbolo) {
        this.simbolo = simbolo;
    }

    public Ficha siguiente() {
        return this == X ? O : X;
    }

    @Override
    public String toString() {
        return simbolo;
    }
}