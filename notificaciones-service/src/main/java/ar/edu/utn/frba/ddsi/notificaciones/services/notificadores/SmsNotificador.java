package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificador implements Notificador{
    @Value("${twilio.phone.number}")
    private PhoneNumber twilioPhoneNumber;

    public void enviar(String destino, String mensaje){
        Message message = Message.creator(
                twilioPhoneNumber,
                new PhoneNumber(destino),
                mensaje
        ).create();

        System.out.println(message.getSid());

//        System.out.println("[SMS SERVICE] Enviando SMS a " + destino);
//        System.out.println("Mensaje: " + mensaje);
//        System.out.println("[SMS SERVICE] COMPLETADO");
    }
}
