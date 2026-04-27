package ar.edu.utn.frba.ddsi.notificaciones.services;

import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.EmailNotificador;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.Notificador;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.SmsNotificador;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.WhatsappNotificador;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    public void enviar(NotificacionRequest request){
        Notificador notificador;
        MedioDeContacto medioDeContacto = request.getMedioDeContacto();
        String destino = medioDeContacto.getDireccion();
        TipoMedioContacto tipo = medioDeContacto.getTipoMedioContacto();

        switch (tipo) {
            case EMAIL:
                notificador = new EmailNotificador();
                break;
            case SMS:
                notificador = new SmsNotificador();
                break;
            case WHATSAPP:
                notificador = new WhatsappNotificador();
                break;
            default:
                throw new RuntimeException("Medio de contacto desconocido.");
        }

        validarDestino(tipo, destino);
        notificador.enviar(request.getMedioDeContacto().getDireccion(), request.getMensaje());
    }

    private void validarDestino(TipoMedioContacto tipo, String destino) {
        switch (tipo) {
            case EMAIL:
                if (!destino.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new RuntimeException("Dirección de email inválida.");
                }
                break;

            case SMS:
            case WHATSAPP:
                if (!destino.matches("^\\+?[0-9]{8,15}$")) {
                    throw new RuntimeException("Número de teléfono inválido.");
                }
                break;

            default:
                throw new RuntimeException("Medio de contacto desconocido.");
        }
    }
}