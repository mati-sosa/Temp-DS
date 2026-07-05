package ar.edu.utn.frba.ddsi.logistica.jobs;


import ar.edu.utn.frba.ddsi.logistica.services.PlanificacionRutasService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlanificacionRutasJob {
    private final PlanificacionRutasService planificacionRutasService;

    public PlanificacionRutasJob(PlanificacionRutasService planificacionRutasService) {
        this.planificacionRutasService = planificacionRutasService;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void ejecutarPlanificacion() {
        planificacionRutasService.planificar();
    }
}
