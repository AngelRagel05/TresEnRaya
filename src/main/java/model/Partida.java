package model;

public class Partida {

    private static final int TAM = 3;
    private Tablero tablero;
    private Ficha turno;

    public Partida() {
        tablero = new Tablero(TAM);
        turno = Math.random() < 0.5 ? Ficha.X : Ficha.O;
    }

    protected static void jugar() {

    }
}
