package ar.edu.utn.frba.ddsi.logistica.models.entities.rutas;

import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.estados_entrega.EstadoEntrega;
import ar.edu.utn.frba.ddsi.logistica.models.entities.estados_entrega.EstadoEntregaPendiente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Entrega {
    @Setter
    private Long id;
    private EstadoEntrega estado;
    @Setter
    private LocalDateTime fechaHoraEntrega;
    private final List<AuditoriaTransicion> historialEstados;
    @Setter
    private List<String> fotos;
    private final String donacionID;

    public Entrega(String donacionID, LocalDateTime fechaHoraEntrega) {
        if (donacionID == null || donacionID.isBlank()) {
            throw new IllegalArgumentException("La entrega debe estar asociada a una donación");
        }
        this.donacionID = donacionID;
        this.fechaHoraEntrega = fechaHoraEntrega;
        this.estado = new EstadoEntregaPendiente();
        this.historialEstados = new ArrayList<>();
        this.fotos = new ArrayList<>();
    }

    public void cambiarEstado(EstadoEntrega nuevo, String justificacion) {
        historialEstados.add(new AuditoriaTransicion(estado.descripcion(), nuevo.descripcion(), justificacion));
        estado = nuevo;
    }

    public void iniciarTraslado()                          { estado.iniciarTraslado(this); }
    public void confirmarRecepcion(List<String> fotos)     { estado.confirmarRecepcion(this, fotos); }
    public void marcarNoRecibida(String motivo)             { estado.marcarNoRecibida(this, motivo); }
    public void reingresarDeposito()                        { estado.reingresarDeposito(this); }
}
