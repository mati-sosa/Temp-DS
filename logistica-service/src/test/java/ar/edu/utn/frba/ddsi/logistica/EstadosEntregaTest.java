package ar.edu.utn.frba.ddsi.logistica;

import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EstadosEntregaTest {

    private Entrega entrega;

    @BeforeEach
    void setUp() {
        entrega = new Entrega("donacion-1", null);
    }

    @Test
    void estadoInicialEsPendiente() {
        assertEquals("Pendiente", entrega.getEstado().descripcion());
    }

    @Test
    void historialInicialEstaVacio() {
        assertTrue(entrega.getHistorialEstados().isEmpty());
    }

    @Test
    void iniciarTrasladoCambiaAEnTraslado() {
        entrega.iniciarTraslado();
        assertEquals("En traslado", entrega.getEstado().descripcion());
    }

    @Test
    void confirmarRecepcionCambiaAEntregadaYGuardaFotos() {
        entrega.iniciarTraslado();
        entrega.confirmarRecepcion(List.of("foto1.jpg", "foto2.jpg"));
        assertEquals("Entregada", entrega.getEstado().descripcion());
        assertEquals(List.of("foto1.jpg", "foto2.jpg"), entrega.getFotos());
        assertNotNull(entrega.getFechaHoraEntrega());
    }

    @Test
    void marcarNoRecibidaCambiaANoRecibidaConMotivo() {
        entrega.iniciarTraslado();
        entrega.marcarNoRecibida("Tocamos timbre pero nadie respondió");
        assertEquals("No recibida", entrega.getEstado().descripcion());

        AuditoriaTransicion ultima = entrega.getHistorialEstados().get(entrega.getHistorialEstados().size() - 1);
        assertEquals("Tocamos timbre pero nadie respondió", ultima.getJustificacion());
    }

    @Test
    void reingresarDepositoDesdeNoRecibidaVuelveAPendiente() {
        entrega.iniciarTraslado();
        entrega.marcarNoRecibida("No había nadie");
        entrega.reingresarDeposito();
        assertEquals("Pendiente", entrega.getEstado().descripcion());
    }

    @Test
    void flujoCompletoHappyPath() {
        assertEquals("Pendiente", entrega.getEstado().descripcion());
        entrega.iniciarTraslado();
        assertEquals("En traslado", entrega.getEstado().descripcion());
        entrega.confirmarRecepcion(List.of("foto.jpg"));
        assertEquals("Entregada", entrega.getEstado().descripcion());
    }

    @Test
    void cadaTransicionAgregaUnaEntradaAlHistorial() {
        entrega.iniciarTraslado();
        entrega.confirmarRecepcion(List.of("foto.jpg"));
        assertEquals(2, entrega.getHistorialEstados().size());
    }

    @Test
    void noSePuedeConfirmarRecepcionDesdePendiente() {
        assertThrows(IllegalStateException.class, () -> entrega.confirmarRecepcion(List.of("foto.jpg")));
    }

    @Test
    void noSePuedeIniciarTrasladoDosVeces() {
        entrega.iniciarTraslado();
        assertThrows(IllegalStateException.class, () -> entrega.iniciarTraslado());
    }

    @Test
    void entregadaEsEstadoTerminal() {
        entrega.iniciarTraslado();
        entrega.confirmarRecepcion(List.of("foto.jpg"));
        assertThrows(IllegalStateException.class, () -> entrega.iniciarTraslado());
    }

    @Test
    void noSePuedeReingresarDepositoDesdePendiente() {
        assertThrows(IllegalStateException.class, () -> entrega.reingresarDeposito());
    }
}
