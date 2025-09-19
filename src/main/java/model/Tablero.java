package model;

public class Tablero {

    private Ficha[][] casillas;

    public Tablero (int tam) {
        casillas = new Ficha[tam][tam];
    }

    protected Boolean ponerFicha(Ficha ficha, int x, int y) {
        if (casillas[x][y] == null) {
            casillas[x][y] = ficha;
            return true;
        } else {
            System.out.println("La casilla elegida está ocupado por: " + casillas[x][y]);
            return false;
        }
    }

    protected Boolean estaLleno() {
        int conteo = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                if (casillas[i][j] == null) {
                    System.out.println("El trablero no está lleno, siguiente jugada.");
                    i = casillas.length;
                    j = casillas.length;
                    return false;
                }
            }
        }
        return true;
    }

    protected Boolean gana(Ficha ficha) {
        return ganaHorizontal(ficha) || ganaVertical(ficha) || ganaDiagonalAnversa(ficha) || ganaDiagonalInvertida(ficha);
    }

    private Boolean ganaHorizontal(Ficha ficha) {
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
        int cont = 0;
        for (int i = 0; i < casillas.length; i++) {
            cont = (casillas[i][i] == ficha) ? cont + 1 : 0;
            if (cont == 3) return true;
        }
        return false;
    }

    private Boolean ganaDiagonalInvertida(Ficha ficha) {
        int cont = 0;
        for (int i = casillas.length; i > 0; i--) {
            cont = (casillas[i][i] == ficha) ? cont + 1 : 0;
            if (cont == 3) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                sb.append(
                        (casillas[i][j] == Ficha.O) ? "O" :
                        (casillas[i][j] == Ficha.X) ? "X" : "-"
                );
                if (j < casillas[i].length - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Object valueOf(Ficha ficha) {
        return (ficha == Ficha.O) ? "O" :
                (ficha == Ficha.X) ? "X" : "-";
    }
}