package ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto;

import lombok.Getter;

@Getter
public class MedioDeContacto {
    private TipoMedioContacto tipoMedioContacto;
    private String direccion;

    public MedioDeContacto(TipoMedioContacto tipoMedioContacto, String direccion) {
        this.tipoMedioContacto = tipoMedioContacto;
        this.direccion = direccion;
    }
}
