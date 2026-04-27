package ar.edu.utn.frba.ddsi.notificaciones;

import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.notificaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.notificaciones.services.NotificacionService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NotificacionesTest {
    private final NotificacionService service = new NotificacionService();

    @Test
    void deberiaEnviarEmailCorrectamente() {
        NotificacionRequest request = new NotificacionRequest();

        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.EMAIL, "test@email.com");
        request.setMedioDeContacto(medio);
        request.setDestinatario("test@email.com");
        request.setMensaje("HOLA EMAIL!");

        assertDoesNotThrow(() -> service.enviar(request));
    }

    @Test
    void deberiaFallarSiEmailEsInvalido() {
        NotificacionRequest request = new NotificacionRequest();

        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.EMAIL, "asdasd123123");
        request.setMedioDeContacto(medio);
        request.setDestinatario("test@email.com");
        request.setMensaje("HOLA EMAIL!");

        assertThrows(RuntimeException.class, () -> {
            service.enviar(request);
        });
    }

    @Test
    void deberiaFallarSiTelefonoEsInvalido() {
        NotificacionRequest request = new NotificacionRequest();

        MedioDeContacto medio = new MedioDeContacto(TipoMedioContacto.SMS, "asdasd123");
        request.setMedioDeContacto(medio);
        request.setDestinatario("asdasd123");
        request.setMensaje("HOLA TELÉFONO!");

        assertThrows(RuntimeException.class, () -> {
            service.enviar(request);
        });
    }
}
