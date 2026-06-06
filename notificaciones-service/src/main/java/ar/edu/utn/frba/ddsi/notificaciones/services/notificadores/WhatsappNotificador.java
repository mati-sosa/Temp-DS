package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsappNotificador implements Notificador{
    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsappNumber;

    public void enviar(String destino, String mensaje){
        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + destino),
                new PhoneNumber("whatsapp:" + twilioWhatsappNumber),
                mensaje
        ).create();

        System.out.println("[SMS SERVICE] Enviando SMS a " + destino);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("[SMS SERVICE] COMPLETADO");
        System.out.println("SID: " + message.getSid());
    }
}
