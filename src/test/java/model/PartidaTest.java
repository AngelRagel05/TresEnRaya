import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Partida;
import model.Ficha;

class PartidaTest {

    Partida partida;

    @BeforeEach
    void setUp() {
        partida = new Partida();
        // Fijamos el turno inicial a X para tests predecibles
        Partida.turno = Ficha.X;
    }

    @Test
    void testPonerFichaValidaCambiaTurno() {
        partida.jugar(0, 0); // X juega
        assertEquals(Ficha.O, Partida.turno);
    }

    @Test
    void testPonerFichaEnCasillaOcupadaNoCambiaTurno() {
        partida.jugar(0, 0); // X juega
        Ficha turnoAntes = Partida.turno;
        partida.jugar(0, 0); // O intenta jugar en la misma casilla
        assertEquals(turnoAntes, Partida.turno);
    }

    @Test
    void testGanadorHorizontal() {
        partida.jugar(0, 0); // X
        partida.jugar(1, 0); // O
        partida.jugar(0, 1); // X
        partida.jugar(1, 1); // O
        partida.jugar(0, 2); // X gana
        assertEquals(Ficha.X, partida.ganador());
        assertTrue(partida.terminada());
    }

    @Test
    void testEmpate() {
        // Llenamos el tablero sin ganador
        Ficha[] secuencia = {
                Ficha.X, Ficha.O, Ficha.X,
                Ficha.X, Ficha.O, Ficha.O,
                Ficha.O, Ficha.X, Ficha.X
        };
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Partida.turno = secuencia[k];
                partida.jugar(i, j);
                k++;
            }
        }
        assertNull(partida.ganador());
        assertTrue(partida.terminada());
    }
}
