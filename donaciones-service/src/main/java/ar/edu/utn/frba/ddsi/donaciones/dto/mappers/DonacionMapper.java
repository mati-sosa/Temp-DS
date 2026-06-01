package ar.edu.utn.frba.ddsi.donaciones.dto.mappers;

import ar.edu.utn.frba.ddsi.donaciones.dto.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Administrador;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Deposito;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.DonacionTotal;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.AuditoriaTransicion;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DonacionMapper {

    public DonacionTotal toEntity(DonacionTotalRequestDTO dto, Donante donante) {
        List<Bien> bienes = dto.getBienes().stream()
                .map(this::toBien)
                .toList();

        return new DonacionTotal(
                LocalDate.now(),
                dto.getDescripcion(),
                bienes,
                new Administrador("Sistema", "001"),
                donante,
                new Deposito()
        );
    }

    private Bien toBien(BienRequestDTO dto) {
        SubcategoriaBien subcategoria = new SubcategoriaBien(
                dto.getSubcategoriaDescripcion(),
                dto.getPerecedero(),
                new CategoriaBien(dto.getCategoriaDescripcion()),
                dto.getUnidadMedida()
        );

        if (Boolean.TRUE.equals(dto.getPerecedero())) {
            return new BienPerecible(
                    dto.getDescripcion(), subcategoria, dto.getCantidad(),
                    dto.getFoto(), dto.getFechaVencimiento()
            );
        } else {
            return new BienNoPerecible(
                    dto.getDescripcion(), subcategoria, dto.getCantidad(), dto.getFoto(),
                    Boolean.TRUE.equals(dto.getTieneEstado()),
                    Boolean.TRUE.equals(dto.getEsNuevo())
            );
        }
    }

    public DonacionResponseDTO toResponseDTO(Donacion donacion) {
        return new DonacionResponseDTO(
                donacion.getId(),
                donacion.getSubcategoriaBien().getDescripcion(),
                donacion.getBienes().size(),
                donacion.getEstado().nombre()
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