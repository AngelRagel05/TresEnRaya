package model;

public class Partida {

//    Atributos
    private static final int TAM = 3;
    private Tablero tablero;
    public static Ficha turno;

//    Constructor
    public Partida() {
        tablero = new Tablero(TAM);
        turno = Math.random() < 0.5 ? Ficha.X : Ficha.O;
    }

/*
    Métodos para saber el estado de la partida
 */

    public void jugar(int fila, int columna) {
//        Hace que el jugdor pueda poner su ficha y si ha ganado o no
        if (!terminada() && tablero.ponerFicha(turno, fila, columna)) {
            if (tablero.gana(turno)) {
                System.out.println(tablero);
                System.out.println("🎉 ¡Victoria de " + turno + "! 🎉");
            } else {
//                Sino gana cambia el turno de la ficha
                turno = turno.siguiente();
            }
        }
    }

    public Boolean terminada() {
        return tablero.estaLleno() || ganador() != null;
    }

    public Ficha ganador() {
//        Comprueba si hay un ganador
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