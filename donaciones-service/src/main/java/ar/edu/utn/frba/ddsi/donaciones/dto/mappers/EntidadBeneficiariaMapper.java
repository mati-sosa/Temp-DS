package ar.edu.utn.frba.ddsi.donaciones.dto.mappers;

import ar.edu.utn.frba.ddsi.donaciones.dto.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.TipoEntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntidadBeneficiariaMapper {

    public EntidadBeneficiaria toEntity(EntidadBeneficiariaRequestDTO dto) {
        TipoEntidadBeneficiaria tipo = new TipoEntidadBeneficiaria(dto.getTipoEntidad(), dto.getDescripcionTipo());

        Direccion direccion = new Direccion(
                dto.getDireccion().getCalle(),
                dto.getDireccion().getCiudad(),
                dto.getDireccion().getProvincia(),
                dto.getDireccion().getCodigoPostal()
        );

        MedioDeContacto telefono = new MedioDeContacto(
                dto.getTelefono().getTipo(),
                dto.getTelefono().getValor()
        );

        List<Representante> representantes = dto.getRepresentantes().stream()
                .map(r -> new Representante(r.getNombre(),
                        new MedioDeContacto(TipoMedioContacto.EMAIL, r.getEmail())))
                .toList();

        return new EntidadBeneficiaria(tipo, dto.getRazonSocial(), direccion, telefono,
                representantes, new ArrayList<>());
    }

    public EntidadBeneficiariaResponseDTO toResponseDTO(EntidadBeneficiaria entidad) {
        DireccionDTO direccion = new DireccionDTO(
                entidad.getDireccion().getCalle(),
                entidad.getDireccion().getCiudad(),
                entidad.getDireccion().getProvincia(),
                entidad.getDireccion().getCodigoPostal()
        );

        MedioDeContactoDTO telefono = new MedioDeContactoDTO(
                entidad.getTelefono().getTipoMedioContacto(),
                entidad.getTelefono().getDireccion()
        );

        List<RepresentanteDTO> representantes = entidad.getRepresentantes().stream()
                .map(r -> new RepresentanteDTO(r.getNombre(), r.getEmail().getDireccion()))
                .toList();

        List<NecesidadResponseDTO> necesidades = entidad.getNecesidades().stream()
                .map(this::necesidadToResponseDTO)
                .toList();

        return new EntidadBeneficiariaResponseDTO(
                entidad.getId(),
                entidad.getTipo().toString(),
                entidad.getRazonSocial(),
                direccion,
                telefono,
                representantes,
                necesidades
        );
    }

    public Necesidad necesidadToEntity(NecesidadRequestDTO dto) {
        SubcategoriaBien subcategoria = new SubcategoriaBien(
                dto.getSubcategoriaDescripcion(),
                dto.isPerecible(),
                new CategoriaBien(dto.getCategoriaDescripcion()),
                UnidadDeMedida.valueOf(dto.getUnidadDeMedida())
        );

        if ("RECURRENTE".equalsIgnoreCase(dto.getTipo())) {
            Periodo periodo = new Periodo(
                    TipoPeriodo.valueOf(dto.getTipoPeriodo()),
                    dto.getCantidadPeriodo()
            );
            return new NecesidadRecurrente(
                    dto.getDescripcion(), subcategoria, dto.getCantidadRequerida(),
                    dto.getCantidadPorPeriodo(), periodo
            );
        }
        return new NecesidadExtraordinaria(dto.getDescripcion(), subcategoria, dto.getCantidadRequerida());
    }

    public NecesidadResponseDTO necesidadToResponseDTO(Necesidad necesidad) {
        String tipo = necesidad instanceof NecesidadRecurrente ? "RECURRENTE" : "EXTRAORDINARIA";
        return new NecesidadResponseDTO(
                tipo,
                necesidad.getDescripcion(),
                necesidad.getSubcategoria().getDescripcion(),
                necesidad.getCantidadRequerida(),
                necesidad.getCantidadCubierta(),
                necesidad.estaSatisfecha()
        );
    }
}
