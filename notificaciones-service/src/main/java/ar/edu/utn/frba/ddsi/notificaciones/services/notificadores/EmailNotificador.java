package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificador implements Notificador{
    @Value("${resend.api.key}")
    private String resendApiKey;

    private Resend resend;

    @PostConstruct
    public void initialize(){
        resend = new Resend(resendApiKey);
    }

    public void enviar(String destino, String mensaje){
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Acme <onboarding@resend.dev>")
                .to(destino)
                .subject("SERVICIO DE NOTIFICACIONES")
                .text(mensaje)
                .build();

        try{
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch(ResendException e) {
            e.printStackTrace();
        }

        System.out.println("[EMAIL SERVICE] Enviando email a " + destino);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("[EMAIL SERVICE] COMPLETADO");
    }
}
