package ar.edu.utn.frba.ddsi.logistica;

import ar.edu.utn.frba.ddsi.logistica.models.entities.Camion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Chofer;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Destino;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EstadosRutaTest {

    private Ruta ruta;
    private Entrega entrega;

    @BeforeEach
    void setUp() {
        Chofer chofer = new Chofer("CH-1", "Juan Pérez");
        Camion camion = new Camion("AB123CD", 2.5f, 20f, 1500f, true);
        Direccion direccion = new Direccion("Calle Falsa 123", "CABA", "Buenos Aires", "1000");
        entrega = new Entrega("donacion-1", null);
        Destino destino = new Destino(direccion, "entidad-1", 1, List.of(entrega));
        ruta = new Ruta(chofer, camion, null, List.of(destino));
    }

    @Test
    void estadoInicialEsPlanificada() {
        assertEquals("Planificada", ruta.getEstado().descripcion());
    }

    @Test
    void iniciarCambiaAEnCurso() {
        ruta.iniciar();
        assertEquals("En curso", ruta.getEstado().descripcion());
    }

    @Test
    void iniciarCascadeaInicioDeTrasladoALasEntregas() {
        ruta.iniciar();
        assertEquals("En traslado", entrega.getEstado().descripcion());
    }

    @Test
    void finalizarCambiaAFinalizada() {
        ruta.iniciar();
        ruta.finalizar();
        assertEquals("Finalizada", ruta.getEstado().descripcion());
    }

    @Test
    void noSePuedeFinalizarDesdePlanificada() {
        assertThrows(IllegalStateException.class, () -> ruta.finalizar());
    }

    @Test
    void noSePuedeIniciarDosVeces() {
        ruta.iniciar();
        assertThrows(IllegalStateException.class, () -> ruta.iniciar());
    }

    @Test
    void finalizadaEsEstadoTerminal() {
        ruta.iniciar();
        ruta.finalizar();
        assertThrows(IllegalStateException.class, () -> ruta.iniciar());
    }

    @Test
    void cadaTransicionAgregaUnaEntradaAlHistorial() {
        ruta.iniciar();
        ruta.finalizar();
        assertEquals(2, ruta.getHistorialEstados().size());
    }
}
