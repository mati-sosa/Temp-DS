package ar.edu.utn.frba.ddsi.notificaciones;

import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.notificaciones.services.NotificacionService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificacionesTest {
//    @Autowired
//    private NotificacionService service;
//
//    @Test
//    void deberiaEnviarEmailCorrectamente() {
//        NotificacionRequest request = new NotificacionRequest();
//
//        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.EMAIL, "rasilva@frba.utn.edu.ar");
//        request.setMedioDeContacto(medio);
//        request.setMensaje("HOLA EMAIL!");
//
//        assertDoesNotThrow(() -> service.enviar(request));
//    }
//
//    @Test
//    void deberiaEnviarSMSCorrectamente() {
//        NotificacionRequest request = new NotificacionRequest();
//        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.SMS, "+541122536843");
//        request.setMedioDeContacto(medio);
//        request.setMensaje("HOLA SMS!");
//
//        assertDoesNotThrow(() -> service.enviar(request));
//    }
//
//    @Test
//    void deberiaEnviarWhatsappCorrectamente() {
//        NotificacionRequest request = new NotificacionRequest();
//
//        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.SMS, "+5491122536843");
//        request.setMedioDeContacto(medio);
//        request.setMensaje("HOLA WHATSAPP!");
//
//        assertDoesNotThrow(() -> service.enviar(request));
//    }
//
//    @Test
//    void deberiaFallarSiEmailEsInvalido() {
//        NotificacionRequest request = new NotificacionRequest();
//
//        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.EMAIL, "asdasd123123");
//        request.setMedioDeContacto(medio);
//        request.setMensaje("HOLA EMAIL!");
//
//        assertThrows(RuntimeException.class, () -> {
//            service.enviar(request);
//        });
//    }
//
//    @Test
//    void deberiaFallarSiTelefonoEsInvalido() {
//        NotificacionRequest request = new NotificacionRequest();
//
//        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.SMS, "asdasd123");
//        request.setMedioDeContacto(medio);
//        request.setMensaje("HOLA TELÉFONO!");
//
//        assertThrows(RuntimeException.class, () -> {
//            service.enviar(request);
//        });
//    }
}
