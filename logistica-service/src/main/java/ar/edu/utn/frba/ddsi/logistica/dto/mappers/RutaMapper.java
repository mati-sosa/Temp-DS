package ar.edu.utn.frba.ddsi.logistica.dto.mappers;

import ar.edu.utn.frba.ddsi.logistica.dto.*;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Camion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Chofer;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Destino;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RutaMapper {

    private final EntregaMapper entregaMapper;

    public RutaMapper(EntregaMapper entregaMapper) {
        this.entregaMapper = entregaMapper;
    }

    public Ruta toEntity(@org.jetbrains.annotations.UnknownNullability @Valid RutaRequestDTO dto) {
        Chofer chofer = toChofer(dto.getChofer());
        Camion camion = toCamion(dto.getCamion());
        List<Destino> destinos = dto.getDestinos().stream().map(this::toDestino).toList();
        return new Ruta(chofer, camion, dto.getFechaReparto(), destinos);
    }

    public Chofer toChofer(ChoferDTO dto) {
        return new Chofer(dto.getId(), dto.getNombre());
    }

    public Camion toCamion(CamionDTO dto) {
        return new Camion(dto.getPatente(), dto.getAltura(), dto.getCapacidadVolumen(),
                dto.getCapacidadCarga(), dto.getDisponible());
    }

    private Destino toDestino(DestinoRequestDTO dto) {
        Direccion direccion = new Direccion(
                dto.getDireccion().getCalle(),
                dto.getDireccion().getCiudad(),
                dto.getDireccion().getProvincia(),
                dto.getDireccion().getCodigoPostal()
        );
        List<Entrega> entregas = dto.getEntregas().stream().map(entregaMapper::toEntity).toList();
        return new Destino(direccion, dto.getEntidadBeneficiariaID(), dto.getOrden(), entregas);
    }

    public RutaResponseDTO toResponseDTO(Ruta ruta) {
        return new RutaResponseDTO(
                ruta.getId(),
                new ChoferDTO(ruta.getChofer().getId(), ruta.getChofer().getNombre()),
                camionToDTO(ruta.getCamion()),
                ruta.getFechaReparto(),
                ruta.getEstado().descripcion(),
                ruta.getDestinos().stream().map(this::toDestinoResponseDTO).toList()
        );
    }

    private DestinoResponseDTO toDestinoResponseDTO(Destino destino) {
        DireccionDTO direccionDTO = new DireccionDTO(
                destino.getDireccion().getCalle(),
                destino.getDireccion().getCiudad(),
                destino.getDireccion().getProvincia(),
                destino.getDireccion().getCodigoPostal()
        );
        return new DestinoResponseDTO(
                direccionDTO,
                destino.getEntidadBeneficiariaID(),
                destino.getOrden(),
                destino.getEntregas().stream().map(entregaMapper::toResponseDTO).toList()
        );
    }

    private CamionDTO camionToDTO(Camion camion) {
        return new CamionDTO(camion.getPatente(), camion.getAltura(), camion.getCapacidadVolumen(),
                camion.getCapacidadCarga(), camion.getDisponible());
    }

    public AuditoriaTransicionDTO toAuditoriaDTO(AuditoriaTransicion transicion) {
        return new AuditoriaTransicionDTO(
                transicion.getEstadoAnterior(),
                transicion.getEstadoNuevo(),
                transicion.getTimestamp(),
                transicion.getJustificacion()
        );
    }
}
