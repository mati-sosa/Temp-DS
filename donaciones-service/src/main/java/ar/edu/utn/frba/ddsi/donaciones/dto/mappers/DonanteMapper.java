package ar.edu.utn.frba.ddsi.donaciones.dto.mappers;

import ar.edu.utn.frba.ddsi.donaciones.dto.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Rubro;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DonanteMapper {

    public Donante toEntity(DonanteRequestDTO dto) {
        List<MedioDeContacto> medios = dto.getMediosDeContacto().stream()
                .map(m -> new MedioDeContacto(m.getTipo(), m.getValor()))
                .toList();

        MedioDeContacto medioPredeterminado = new MedioDeContacto(
                dto.getMedioPredeterminado().getTipo(),
                dto.getMedioPredeterminado().getValor()
        );

        List<Representante> representantes = dto.getRepresentantes() == null
                ? new ArrayList<>()
                : dto.getRepresentantes().stream()
                        .map(r -> new Representante(r.getNombre(),
                                new MedioDeContacto(TipoMedioContacto.EMAIL, r.getEmail())))
                        .toList();

        Rubro rubro = dto.getRubro() != null ? new Rubro(dto.getRubro()) : null;

        Direccion direccion = new Direccion(
                dto.getDireccion().getCalle(),
                dto.getDireccion().getCiudad(),
                dto.getDireccion().getProvincia(),
                dto.getDireccion().getCodigoPostal()
        );

        return new Donante(
                dto.getNombreRazonSocial(),
                dto.getDocumento(),
                dto.getTipoDocumento(),
                dto.getTipoPersona(),
                dto.getFechaNacimientoInicioActividad(),
                medios,
                medioPredeterminado,
                representantes,
                rubro,
                dto.getGenero(),
                direccion
        );
    }

    public DonanteResponseDTO toResponseDTO(Donante donante) {
        List<MedioDeContactoDTO> medios = donante.getMediosDeContacto().stream()
                .map(m -> new MedioDeContactoDTO(m.getTipoMedioContacto(), m.getDireccion()))
                .toList();

        MedioDeContactoDTO medioPredeterminado = new MedioDeContactoDTO(
                donante.getMedioPredeterminado().getTipoMedioContacto(),
                donante.getMedioPredeterminado().getDireccion()
        );

        DireccionDTO direccion = new DireccionDTO(
                donante.getDireccion().getCalle(),
                donante.getDireccion().getCiudad(),
                donante.getDireccion().getProvincia(),
                donante.getDireccion().getCodigoPostal()
        );

        return new DonanteResponseDTO(
                donante.getId(),
                donante.getNombre_razonSocial(),
                donante.getDocumento(),
                donante.getTipoDocumento(),
                donante.getTipoPersona(),
                donante.getFechaDeNacimiento_inicioActividad(),
                medios,
                medioPredeterminado,
                donante.getGenero(),
                direccion
        );
    }
}