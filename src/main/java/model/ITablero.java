package model;

public interface ITablero {
    Boolean ponerFicha(Ficha ficha, int x, int y);
    Boolean estaLleno();
    Boolean gana(Ficha ficha);
    String toString();
    Object valueOf(Ficha ficha);
}