package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.EstadoEnDeposito;
import ar.edu.utn.frba.ddsi.donaciones.models.repositories.DonacionRepository;
import ar.edu.utn.frba.ddsi.donaciones.models.repositories.EntidadBeneficiariaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchmakingService {

    private final DonacionRepository donacionRepository;
    private final EntidadBeneficiariaRepository entidadRepository;
    private final MotorDeMatchmaking motor;
    private final DonacionService donacionService;
    private final NotificacionClient notificacionClient;

    private List<List<EvaluacionMatch>> ultimosRankings = new ArrayList<>();

    public MatchmakingService(DonacionRepository donacionRepository,
                              EntidadBeneficiariaRepository entidadRepository,
                              DonacionService donacionService,
                              NotificacionClient notificacionClient) {
        this.donacionRepository = donacionRepository;
        this.entidadRepository = entidadRepository;
        this.donacionService = donacionService;
        this.notificacionClient = notificacionClient;
        this.motor = new MotorDeMatchmaking(List.of(
                new MatchmakingCompatibilidadSemantica(0.4, 0.4, 0.2),
                new MatchmakingPrioridadSubatendidos(0.1)
        ));
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void ejecutarMatchmakingProgramado() {
        ultimosRankings = calcularRankings();
    }

    public List<List<EvaluacionMatch>> ejecutarAhora() {
        ultimosRankings = calcularRankings();
        return ultimosRankings;
    }

    public List<List<EvaluacionMatch>> obtenerUltimosRankings() {
        return ultimosRankings;
    }

    public Donacion confirmarAsignacion(Long donacionId, Long entidadId) {
        Donacion donacion = donacionService.buscarPorId(donacionId);
        EntidadBeneficiaria entidad = entidadRepository.buscarPorId(entidadId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Entidad no encontrada: " + entidadId));

        donacion.setEntidadBeneficiariaId(entidadId);
        donacion.asignar();

        entidad.registrarDonacionRecibida();
        entidadRepository.guardar(entidad);

        donacionRepository.guardar(donacion);

        var donante = donacion.getDonacionDeOrigen().getDonante();
        notificacionClient.enviar(
                donante.getMedioPredeterminado(),
                "Tu donación de " + donacion.getSubcategoriaBien().getDescripcion()
                        + " fue asignada a " + entidad.getRazonSocial() + "."
        );

        return donacion;
    }

    private List<List<EvaluacionMatch>> calcularRankings() {
        ArrayList<Donacion> donacionesEnDeposito = donacionRepository.buscarTodas().stream()
                .filter(d -> d.getEstado() instanceof EstadoEnDeposito)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        ArrayList<EntidadBeneficiaria> entidades = new ArrayList<>(entidadRepository.buscarTodas());
        return motor.ejecutar(donacionesEnDeposito, entidades);
    }
}
