package model;

public class Tablero {

    private Ficha[][] casillas;

    public Tablero (int n) {
        casillas = new Ficha[n][n];
    }
}
