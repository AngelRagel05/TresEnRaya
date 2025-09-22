package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Juego {

//    Atributos
    private static Scanner sc = new Scanner(System.in);

    public static void arrancarJuego() {
//        Muestra mensaje inicial e incia la partida
        System.out.println("Inicio del partida de Tres en Raya. ❌⭕");
        Partida partida = new Partida();
        while (!partida.terminada()) {
            System.out.println(partida);
            partida.jugar(introducirFila(), introducirColumna());
        }
        System.out.println("El juego ha acabado.");
    }

    private static int introducirFila() {
//        Depura que el usuario introduzca un número
        int fila = 0;
        Boolean check = false;
        while (!check) {
            try {
                System.out.print("Introduce la posición de la fila.");
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
//        Depura que el usuario introduzca un número
        int columna = 0;
        Boolean check = false;
        while (!check) {
            try {
                System.out.print("Introduce la posición de la columna.");
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
