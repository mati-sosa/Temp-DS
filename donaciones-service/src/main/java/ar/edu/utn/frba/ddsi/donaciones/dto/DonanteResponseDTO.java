package ar.edu.utn.frba.ddsi.donaciones.dto;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Genero;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoDocumento;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoPersona;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DonanteResponseDTO {
    private Long id;
    private String nombreRazonSocial;
    private String documento;
    private TipoDocumento tipoDocumento;
    private TipoPersona tipoPersona;
    private LocalDate fechaNacimientoInicioActividad;
    private List<MedioDeContactoDTO> mediosDeContacto;
    private MedioDeContactoDTO medioPredeterminado;
    private Genero genero;
    private DireccionDTO direccion;
}