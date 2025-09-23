package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Tablero;
import model.Ficha;

class TableroTest {

    Tablero tablero;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tablero = new Tablero(3);
    }

    @Test
    void testPonerFichaValida() {
        assertTrue(tablero.ponerFicha(Ficha.X, 0, 0));
        assertFalse(tablero.ponerFicha(Ficha.O, 0, 0)); // ya ocupada
    }

    @Test
    void testPonerFichaFuera() {
        assertFalse(tablero.ponerFicha(Ficha.X, -1, 0));
        assertFalse(tablero.ponerFicha(Ficha.X, 0, 3));
    }

    @Test
    void testEstaLleno() {
        assertFalse(tablero.estaLleno());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero.ponerFicha(Ficha.X, i, j);
            }
        }
        assertTrue(tablero.estaLleno());
    }

    @Test
    void testGanaHorizontal() {
        tablero.ponerFicha(Ficha.O, 0, 0);
        tablero.ponerFicha(Ficha.O, 0, 1);
        tablero.ponerFicha(Ficha.O, 0, 2);
        assertTrue(tablero.gana(Ficha.O));
    }

    @Test
    void testValueOf() {
        assertEquals("❌", tablero.valueOf(Ficha.X));
        assertEquals("⭕", tablero.valueOf(Ficha.O));
        assertEquals("〰️", tablero.valueOf(null));
    }
}