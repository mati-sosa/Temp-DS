package ar.edu.utn.frba.ddsi.notificaciones.dto;

import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.MedioDeContacto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacionRequest {
    private String destinatario;
    private MedioDeContacto medioDeContacto;
    private String mensaje;
}