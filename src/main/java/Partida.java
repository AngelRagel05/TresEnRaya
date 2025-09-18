public class Partida {

    private Tablero tablero;
    private Ficha turno;

    public Partida(int n) {
        tablero = new Tablero(n);
        turno = Math.random() < 0.5 ? Ficha.X : Ficha.O;
    }
}
