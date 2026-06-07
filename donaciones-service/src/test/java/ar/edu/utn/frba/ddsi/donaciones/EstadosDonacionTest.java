package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Administrador;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Deposito;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.DonacionTotal;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.AuditoriaTransicion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EstadosDonacionTest {

    private Donacion donacion;

    @BeforeEach
    void setUp() {
        SubcategoriaBien subcategoria = new SubcategoriaBien(
                "Arroz", false, new CategoriaBien("Alimentos"), UnidadDeMedida.KILOGRAMOS
        );
        DonacionTotal donacionTotal = new DonacionTotal(
                LocalDate.now(), "Donación de prueba", new ArrayList<>(),
                new Administrador("Admin", "001"), null, new Deposito()
        );
        donacion = new Donacion(subcategoria, new ArrayList<>(), donacionTotal);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Estado inicial
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    void estadoInicialEsEnDeposito() {
        assertEquals("En depósito", donacion.getEstado().nombre());
    }

    @Test
    void historialInicialEstaVacio() {
        assertTrue(donacion.getHistorialEstados().isEmpty());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Transiciones válidas — flujo feliz
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    void asignarCambiaAAsignacionRealizada() {
        donacion.asignar();
        assertEquals("Asignación realizada", donacion.getEstado().nombre());
    }

    @Test
    void planificarRutaCambiaAListaParaEntregar() {
        donacion.asignar();
        donacion.planificarRuta();
        assertEquals("Lista para entregar", donacion.getEstado().nombre());
    }

    @Test
    void despacharCambiaAEnTraslado() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        assertEquals("En traslado", donacion.getEstado().nombre());
    }

    @Test
    void entregarCambiaAEntregada() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        donacion.entregar();
        assertEquals("Entregada", donacion.getEstado().nombre());
    }

    @Test
    void flujoCompletoHappyPath() {
        assertEquals("En depósito",         donacion.getEstado().nombre());
        donacion.asignar();
        assertEquals("Asignación realizada", donacion.getEstado().nombre());
        donacion.planificarRuta();
        assertEquals("Lista para entregar",  donacion.getEstado().nombre());
        donacion.despachar();
        assertEquals("En traslado",          donacion.getEstado().nombre());
        donacion.entregar();
        assertEquals("Entregada",            donacion.getEstado().nombre());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Entrega fallida
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    void fallarEntregaCambiaAEntregaFallida() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        donacion.fallarEntrega("Nadie abrió la puerta");
        assertEquals("Entrega fallida", donacion.getEstado().nombre());
    }

    @Test
    void entregaFallidaGuardaJustificacionEnAuditoria() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        donacion.fallarEntrega("Nadie abrió la puerta");

        AuditoriaTransicion ultimaTransicion = donacion.getHistorialEstados()
                .get(donacion.getHistorialEstados().size() - 1);
        assertEquals("Nadie abrió la puerta", ultimaTransicion.getJustificacion());
    }

    @Test
    void almacenarDesdeFallidaVuelveAlDeposito() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        donacion.fallarEntrega("Dirección incorrecta");
        donacion.almacenar();
        assertEquals("En depósito", donacion.getEstado().nombre());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Vencida (el admin puede vencer desde varios estados)
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    void vencerDesdeEnDeposito() {
        donacion.vencer();
        assertEquals("Vencida", donacion.getEstado().nombre());
    }

    @Test
    void vencerDesdeAsignacionRealizada() {
        donacion.asignar();
        donacion.vencer();
        assertEquals("Vencida", donacion.getEstado().nombre());
    }

    @Test
    void vencerDesdeListaParaEntregar() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.vencer();
        assertEquals("Vencida", donacion.getEstado().nombre());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Auditoría
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    void cadaTransicionAgregaUnaEntradaAlHistorial() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        assertEquals(3, donacion.getHistorialEstados().size());
    }

    @Test
    void auditoriaRegistraEstadoAnteriorYNuevoCorrecto() {
        donacion.asignar();
        AuditoriaTransicion transicion = donacion.getHistorialEstados().get(0);
        assertEquals("En depósito",          transicion.getEstadoAnterior());
        assertEquals("Asignación realizada", transicion.getEstadoNuevo());
    }

    @Test
    void auditoriaRegistraTimestamp() {
        donacion.asignar();
        assertNotNull(donacion.getHistorialEstados().get(0).getTimestamp());
    }

    @Test
    void transicionSinJustificacionTieneJustificacionNula() {
        donacion.asignar();
        assertNull(donacion.getHistorialEstados().get(0).getJustificacion());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Transiciones inválidas
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    void noSePuedeEntregarDirectamenteDesdeElDeposito() {
        assertThrows(IllegalStateException.class, () -> donacion.entregar());
    }

    @Test
    void noSePuedeDespacharDesdeAsignacionRealizada() {
        donacion.asignar();
        assertThrows(IllegalStateException.class, () -> donacion.despachar());
    }

    @Test
    void noSePuedeAsignarDesdeFallidaSinAlmacenarPrimero() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        donacion.fallarEntrega("No estaban");
        assertThrows(IllegalStateException.class, () -> donacion.asignar());
    }

    @Test
    void entregadaEsEstadoTerminal() {
        donacion.asignar();
        donacion.planificarRuta();
        donacion.despachar();
        donacion.entregar();
        assertThrows(IllegalStateException.class, () -> donacion.asignar());
    }

    @Test
    void vencidaEsEstadoTerminal() {
        donacion.vencer();
        assertThrows(IllegalStateException.class, () -> donacion.asignar());
    }
}