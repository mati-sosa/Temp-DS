package ar.edu.utn.frba.ddsi.logistica.services;

import ar.edu.utn.frba.ddsi.logistica.models.entities.Camion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Chofer;
import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;
import ar.edu.utn.frba.ddsi.logistica.models.repositories.EntregaRepository;
import ar.edu.utn.frba.ddsi.logistica.models.repositories.RutaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final EntregaRepository entregaRepository;

    public RutaService(RutaRepository rutaRepository, EntregaRepository entregaRepository) {
        this.rutaRepository = rutaRepository;
        this.entregaRepository = entregaRepository;
    }

    public Ruta crear(Ruta ruta) {
        ruta.getDestinos().forEach(destino ->
                destino.getEntregas().forEach(entregaRepository::guardar));
        return rutaRepository.guardar(ruta);
    }

    public List<Ruta> listar() {
        return rutaRepository.buscarTodas();
    }

    public Ruta buscarPorId(Long id) {
        return rutaRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Ruta no encontrada: " + id));
    }

    public Ruta actualizar(Long id, Chofer chofer, Camion camion, LocalDateTime fechaReparto) {
        Ruta ruta = buscarPorId(id);
        ruta.setChofer(chofer);
        ruta.setCamion(camion);
        ruta.setFechaReparto(fechaReparto);
        return rutaRepository.guardar(ruta);
    }

    public void eliminar(Long id) {
        if (!rutaRepository.existePorId(id))
            throw new NoSuchElementException("Ruta no encontrada: " + id);
        rutaRepository.eliminar(id);
    }

    public Ruta cambiarEstado(Long id, String accion, String justificacion) {
        Ruta ruta = buscarPorId(id);
        switch (accion.toUpperCase()) {
            case "INICIAR"   -> ruta.iniciar();
            case "FINALIZAR" -> ruta.finalizar();
            default -> throw new IllegalArgumentException("Acción desconocida: " + accion);
        }
        return rutaRepository.guardar(ruta);
    }

    public List<AuditoriaTransicion> obtenerHistorial(Long id) {
        return buscarPorId(id).getHistorialEstados();
    }
}
