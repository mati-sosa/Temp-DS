package ar.edu.utn.frba.ddsi.logistica.dto.mappers;

import ar.edu.utn.frba.ddsi.logistica.dto.AuditoriaTransicionDTO;
import ar.edu.utn.frba.ddsi.logistica.dto.EntregaRequestDTO;
import ar.edu.utn.frba.ddsi.logistica.dto.EntregaResponseDTO;
import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import org.springframework.stereotype.Component;

@Component
public class EntregaMapper {

    public Entrega toEntity(EntregaRequestDTO dto) {
        return new Entrega(dto.getDonacionID(), dto.getFechaHoraEntrega());
    }

    public EntregaResponseDTO toResponseDTO(Entrega entrega) {
        return new EntregaResponseDTO(
                entrega.getId(),
                entrega.getEstado().descripcion(),
                entrega.getFechaHoraEntrega(),
                entrega.getFotos(),
                entrega.getDonacionID()
        );
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
