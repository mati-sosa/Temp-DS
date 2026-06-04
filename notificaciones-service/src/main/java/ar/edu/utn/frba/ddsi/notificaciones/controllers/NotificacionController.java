package ar.edu.utn.frba.ddsi.notificaciones.controllers;

import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import ar.edu.utn.frba.ddsi.notificaciones.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/enviar-notificacion")
    public String enviarNotificacion(@RequestBody NotificacionRequest request){
        notificacionService.enviar(request);
        return "Notificacion enviada.";
    }
}
