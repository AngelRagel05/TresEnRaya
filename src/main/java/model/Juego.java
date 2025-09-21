package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Juego {

    private static Scanner sc = new Scanner(System.in);

    public static void arrancarJuego() {
        System.out.println("Inicio del partida de Tres en Raya. ❌⭕");
        Partida partida = new Partida();
        while (!partida.terminada()) {
            System.out.println(partida);
            partida.jugar(introducirFila(), introducirColumna());
        }

    }

    private static int introducirFila() {
        int fila = 0;
        Boolean check = false;
        while (!check) {
            try {
                System.out.println("Introduce la posición de la fila.");
                fila = sc.nextInt();
                while (fila < 0) {
                    System.err.println("Error. Debe introducir un número entero positivo.");
                    fila = sc.nextInt();
                }
                check = true;
            } catch (InputMismatchException e) {
                System.err.println("Error. Debe introducir un número entero positivo.");
                sc.next();
            }
        }
        return fila;
    }

    private static int introducirColumna() {
        int columna = 0;
        Boolean check = false;
        while (!check) {
            try {
                System.out.println("Introduce la posición de la columna.");
                columna = sc.nextInt();
                while (columna < 0) {
                    System.err.println("Error. Debe introducir un número entero positivo.");
                    columna = sc.nextInt();
                }
                check = true;
            } catch (InputMismatchException e) {
                System.err.println("Error. Debe introducir un número entero positivo.");
                sc.next();
            }
        }
        return columna;
    }
}
