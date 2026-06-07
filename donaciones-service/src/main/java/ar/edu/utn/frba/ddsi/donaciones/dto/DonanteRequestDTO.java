package ar.edu.utn.frba.ddsi.donaciones.dto;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Genero;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoDocumento;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoPersona;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class DonanteRequestDTO {
    @NotBlank(message = "El nombre o razón social es requerido")
    private String nombreRazonSocial;

    @NotBlank(message = "El documento es requerido")
    private String documento;

    @NotNull(message = "El tipo de documento es requerido")
    private TipoDocumento tipoDocumento;

    @NotNull(message = "El tipo de persona es requerido")
    private TipoPersona tipoPersona;

    @NotNull(message = "La fecha de nacimiento o inicio de actividad es requerida")
    private LocalDate fechaNacimientoInicioActividad;

    @NotEmpty(message = "Debe indicarse al menos un medio de contacto")
    @Valid
    private List<MedioDeContactoDTO> mediosDeContacto;

    @NotNull(message = "Debe indicarse un medio de contacto predeterminado")
    @Valid
    private MedioDeContactoDTO medioPredeterminado;

    private List<RepresentanteDTO> representantes;
    private String rubro;
    private Genero genero;

    @NotNull(message = "La dirección es requerida")
    @Valid
    private DireccionDTO direccion;
}