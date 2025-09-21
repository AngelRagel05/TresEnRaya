package model;

public class Partida {

    private static final int TAM = 3;
    private Tablero tablero;
    protected static Ficha turno;

    public Partida() {
        tablero = new Tablero(TAM);
        turno = Math.random() < 0.5 ? Ficha.X : Ficha.O;
    }

    public void jugar(int fila, int columna) {
        if (!terminada() && tablero.ponerFicha(turno, fila, columna)) {
            if (tablero.gana(turno)) {
                System.out.println(tablero);
                System.out.println("🎉 ¡Victoria de " + turno + "! 🎉");
            } else {
                turno = turno.siguiente();
            }
        }
    }

    public Boolean terminada() {
        return tablero.estaLleno() || ganador() != null;
    }

    protected Ficha ganador() {
        if (tablero.gana(Ficha.O) == true) return Ficha.O;
        if (tablero.gana(Ficha.X) == true) return Ficha.X;
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Turno: ").append(turno).append("\n");
        sb.append("Tablero:\n").append(tablero).append("\n");
        Ficha g = ganador();
        if (g != null) {
            sb.append("Ganador: ").append(g).append("\n");
        } else if (terminada()) {
            sb.append("Empate\n");
        }
        return sb.toString();
    }
}