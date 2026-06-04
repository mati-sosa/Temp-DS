package ar.edu.utn.frba.ddsi.notificaciones.services;

import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.EmailNotificador;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.Notificador;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.SmsNotificador;
import ar.edu.utn.frba.ddsi.notificaciones.services.notificadores.WhatsappNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    @Autowired
    private EmailNotificador emailNotificador;
    @Autowired
    private SmsNotificador smsNotificador;
    @Autowired
    private WhatsappNotificador whatsappNotificador;

    public void enviar(NotificacionRequest request){
        Notificador notificador;
        MedioDeContacto medioDeContacto = request.getMedioDeContacto();

        String destino = medioDeContacto.getDireccion();
        TipoMedioContacto tipo = medioDeContacto.getTipoMedioContacto();
        String mensaje = request.getMensaje();

        switch (tipo) {
            case EMAIL:
                notificador = emailNotificador;
                break;
            case SMS:
                notificador = smsNotificador;
                break;
            case WHATSAPP:
                notificador = whatsappNotificador;
                break;
            default:
                throw new RuntimeException("Medio de contacto desconocido.");
        }

        validarDestino(tipo, destino);
        notificador.enviar(destino, mensaje);
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