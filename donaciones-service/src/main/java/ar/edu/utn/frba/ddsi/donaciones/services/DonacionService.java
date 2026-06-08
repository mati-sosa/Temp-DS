package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.DonacionTotal;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.donaciones.models.repositories.DonacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;

    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    public List<Donacion> registrar(DonacionTotal donacionTotal) {
        donacionTotal.segmentarDonacion();
        return donacionTotal.getDonacionesSegmentadas().stream()
                .map(donacionRepository::guardar)
                .toList();
    }

    public List<Donacion> listar() {
        return donacionRepository.buscarTodas();
    }

    public Donacion buscarPorId(Long id) {
        return donacionRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Donación no encontrada: " + id));
    }

    public Donacion actualizar(Long id, Long entidadBeneficiariaId) {
        Donacion donacion = buscarPorId(id);
        donacion.setEntidadBeneficiariaId(entidadBeneficiariaId);
        return donacionRepository.guardar(donacion);
    }

    public void eliminar(Long id) {
        if (!donacionRepository.existePorId(id))
            throw new NoSuchElementException("Donación no encontrada: " + id);
        donacionRepository.eliminar(id);
    }

    public Donacion cambiarEstado(Long id, String accion, String justificacion) {
        Donacion donacion = buscarPorId(id);
        switch (accion.toUpperCase()) {
            case "ASIGNAR"         -> donacion.asignar();
            case "PLANIFICAR_RUTA" -> donacion.planificarRuta();
            case "DESPACHAR"       -> donacion.despachar();
            case "ENTREGAR"        -> donacion.entregar();
            case "FALLAR"          -> donacion.fallarEntrega(justificacion);
            case "VENCER"          -> donacion.vencer();
            case "ALMACENAR"       -> donacion.almacenar();
            default -> throw new IllegalArgumentException("Acción desconocida: " + accion);
        }
        return donacionRepository.guardar(donacion);
    }

    public List<AuditoriaTransicion> obtenerHistorial(Long id) {
        return buscarPorId(id).getHistorialEstados();
    }

    public List<Donacion> buscarPorDonante(Long donanteId) {
        return donacionRepository.buscarTodas().stream()
                .filter(d -> {
                    var donante = d.getDonacionDeOrigen().getDonante();
                    return donante != null && donanteId.equals(donante.getId());
                })
                .toList();
    }

    public long contarOrganizacionesAyudadas(Long donanteId) {
        return donacionRepository.buscarTodas().stream()
                .filter(d -> d.getEntidadBeneficiariaId() != null)
                .filter(d -> {
                    var donante = d.getDonacionDeOrigen().getDonante();
                    return donante != null && donanteId.equals(donante.getId());
                })
                .map(Donacion::getEntidadBeneficiariaId)
                .distinct()
                .count();
    }
}