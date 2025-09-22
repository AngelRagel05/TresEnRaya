package model;

public interface IPartida {
    void jugar(int fila, int columna);
    Boolean terminada();
    Ficha ganador();
    String toString();
}
