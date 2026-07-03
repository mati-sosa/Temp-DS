package ar.edu.utn.frba.ddsi.logistica.models.entities.rutas;

import ar.edu.utn.frba.ddsi.logistica.models.entities.Camion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Chofer;
import ar.edu.utn.frba.ddsi.logistica.models.entities.auditoria.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.estados_ruta.EstadoRuta;
import ar.edu.utn.frba.ddsi.logistica.models.entities.estados_ruta.EstadoRutaPlanificada;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Ruta {
    @Setter
    private Long id;
    @Setter
    private Chofer chofer;
    @Setter
    private LocalDateTime fechaReparto;
    private EstadoRuta estado;
    private final List<Destino> destinos;
    private final List<AuditoriaTransicion> historialEstados;
    @Setter
    private Camion camion;

    public Ruta(Chofer chofer, Camion camion, LocalDateTime fechaReparto, List<Destino> destinos) {
        if (chofer == null || camion == null) {
            throw new IllegalArgumentException("La ruta debe tener chofer y camión asignados");
        }
        this.chofer = chofer;
        this.camion = camion;
        this.fechaReparto = fechaReparto;
        this.destinos = destinos;
        this.estado = new EstadoRutaPlanificada();
        this.historialEstados = new ArrayList<>();
    }

    public void cambiarEstado(EstadoRuta nuevo, String justificacion) {
        historialEstados.add(new AuditoriaTransicion(estado.descripcion(), nuevo.descripcion(), justificacion));
        estado = nuevo;
    }

    public void iniciar()   { estado.iniciar(this); }
    public void finalizar() { estado.finalizar(this); }
}
