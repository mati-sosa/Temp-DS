package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.repositories.DonanteRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InactividadScheduler {

    private final DonanteRepository donanteRepository;
    private final NotificacionClient notificacionClient;

    private static final int DIAS_INACTIVIDAD = 20;

    public InactividadScheduler(DonanteRepository donanteRepository, NotificacionClient notificacionClient) {
        this.donanteRepository = donanteRepository;
        this.notificacionClient = notificacionClient;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void notificarDonantesInactivos() {
        LocalDate limite = LocalDate.now().minusDays(DIAS_INACTIVIDAD);
        donanteRepository.buscarTodos().stream()
                .filter(d -> d.getUltimaInteraccion() != null && d.getUltimaInteraccion().isBefore(limite))
                .forEach(d -> notificacionClient.enviar(
                        d.getMedioPredeterminado(),
                        "¡Te extrañamos! Hace más de " + DIAS_INACTIVIDAD
                                + " días que no realizás una donación. ¿Querés volver a donar?"
                ));
    }
}
