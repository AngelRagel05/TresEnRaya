package model;

public class Tablero {

//    Atributo que es el tablero
    private Ficha[][] casillas;

//    Constructor
    public Tablero(int tam) {
        casillas = new Ficha[tam][tam];
    }

/*
    Métodos para aplicar la lógica del Tres en Raya
 */

    protected Boolean ponerFicha(Ficha ficha, int x, int y) {
//        Compruebo si está dentro de los límites del tablero
        if (x < 0 || x >= casillas.length || y < 0 || y >= casillas.length) {
            System.err.println("⚠️ Posición fuera del tablero.");
            return false;
        }
//        Si la casilla está vacía pongo la ficha
        if (casillas[x][y] == null) {
            casillas[x][y] = ficha;
            return true;
//            Sino le indico al usuario que está ocupada.
        } else {
            System.out.println("La casilla elegida está ocupado por: " + casillas[x][y] + ".");
            return false;
        }
    }

    protected Boolean estaLleno() {
//        Recorro el tablero y si encuentro un null significa que hay casillas libres
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                if (casillas[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    protected Boolean gana(Ficha ficha) {
//        Verifico todas direcciones para ver si hay un ganador
        return ganaHorizontal(ficha) || ganaVertical(ficha) || ganaDiagonalAnversa(ficha) || ganaDiagonalInvertida(ficha);
    }

    private Boolean ganaHorizontal(Ficha ficha) {
//        Compruebo las filas en forma horizontal y si las tres son iguales retorno true
        for (int i = 0; i < casillas.length; i++) {
            int cont = 0;
            for (int j = 0; j < casillas.length; j++) {
                cont = (casillas[i][j] == ficha) ? cont + 1 : 0;
                if (cont == 3) return true;
            }
        }
        return false;
    }

    private Boolean ganaVertical(Ficha ficha) {
//        Compruebo las filas en forma vertical y si las tres son iguales retorno true
        for (int j = 0; j < casillas.length; j++) {
            int cont = 0;
            for (int i = 0; i < casillas.length; i++) {
                cont = (casillas[i][j] == ficha) ? cont + 1 : 0;
                if (cont == 3) return true;
            }
        }
        return false;
    }

    private Boolean ganaDiagonalAnversa(Ficha ficha) {
//        Compruebo las filas en forma diagonal anversa y si las tres son iguales retorno true
        int cont = 0;
        for (int i = 0; i < casillas.length; i++) {
            cont = (casillas[i][i] == ficha) ? cont + 1 : 0;
            if (cont == 3) return true;
        }
        return false;
    }

    private Boolean ganaDiagonalInvertida(Ficha ficha) {
//        Compruebo las filas en forma diagonal invertida y si las tres son iguales retorno true
        int cont = 0;
        for (int i = 0; i < casillas.length; i++) {
            cont = (casillas[i][casillas.length - 1 - i] == ficha) ? cont + 1 : 0;
            if (cont == 3) return true;
        }
        return false;
    }

    @Override
    public String toString() {
//        Muestra el estado del tablero
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                sb.append(
                        (casillas[i][j] == Ficha.O) ? "⭕" :
                                (casillas[i][j] == Ficha.X) ? "❌" : "〰️"
                );
                if (j < casillas[i].length - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    protected Object valueOf(Ficha ficha) {
//        Devuelve el valor de la ficha ⭕, ❌ o 〰️ si es null
        return (ficha == Ficha.O) ? "⭕" :
                (ficha == Ficha.X) ? "❌" : "〰️";
    }
}