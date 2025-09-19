package model;

public class Partida {

    private Tablero tablero;
    private Ficha turno;

    public Partida() {
        tablero = new Tablero();
        turno = Math.random() < 0.5 ? Ficha.X : Ficha.O;
    }
}
