package ar.edu.utn.frba.ddsi.logistica.services;

import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import ar.edu.utn.frba.ddsi.logistica.models.repositories.EntregaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepository;

    public EntregaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    public Entrega crear(Entrega entrega) {
        return entregaRepository.guardar(entrega);
    }

    public List<Entrega> listar() {
        return entregaRepository.buscarTodas();
    }

    public Entrega buscarPorId(Long id) {
        return entregaRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Entrega no encontrada: " + id));
    }

    public Entrega actualizar(Long id, LocalDateTime fechaHoraEntrega) {
        Entrega entrega = buscarPorId(id);
        entrega.setFechaHoraEntrega(fechaHoraEntrega);
        return entregaRepository.guardar(entrega);
    }

    public void eliminar(Long id) {
        if (!entregaRepository.existePorId(id))
            throw new NoSuchElementException("Entrega no encontrada: " + id);
        entregaRepository.eliminar(id);
    }

    public Entrega cambiarEstado(Long id, String accion, String justificacion, List<String> fotos) {
        Entrega entrega = buscarPorId(id);
        switch (accion.toUpperCase()) {
            case "INICIAR_TRASLADO"    -> entrega.iniciarTraslado();
            case "CONFIRMAR_RECEPCION" -> entrega.confirmarRecepcion(fotos);
            case "MARCAR_NO_RECIBIDA"  -> entrega.marcarNoRecibida(justificacion);
            case "REINGRESAR_DEPOSITO" -> entrega.reingresarDeposito();
            default -> throw new IllegalArgumentException("Acción desconocida: " + accion);
        }
        return entregaRepository.guardar(entrega);
    }

    public List<AuditoriaTransicion> obtenerHistorial(Long id) {
        return buscarPorId(id).getHistorialEstados();
    }
}
