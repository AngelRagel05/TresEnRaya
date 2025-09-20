package model;

public enum Ficha {
    X("X"),
    O("O");

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