package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.AuditoriaTransicion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.EstadoDonacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.EstadoEnDeposito;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Donacion {
    @Setter
    private Long id;
    private SubcategoriaBien subcategoriaBien;
    private List<Bien> bienes;
    private DonacionTotal donacionDeOrigen;
    private EstadoDonacion estado;
    private List<AuditoriaTransicion> historialEstados;

    public Donacion(SubcategoriaBien unaSubcategoriaBien, List<Bien> unosBienes, DonacionTotal unaDonacionDeOrigen) {
        subcategoriaBien = unaSubcategoriaBien;
        bienes = unosBienes;
        donacionDeOrigen = unaDonacionDeOrigen;
        estado = new EstadoEnDeposito();
        historialEstados = new ArrayList<>();
    }

    // Llamado por los estados para registrar la transición y cambiar el estado actual
    public void cambiarEstado(EstadoDonacion nuevo, String justificacion) {
        historialEstados.add(new AuditoriaTransicion(estado.nombre(), nuevo.nombre(), justificacion));
        estado = nuevo;
    }

    // Métodos de dominio — cada uno delega al estado actual
    public void asignar()                           { estado.asignarDestinatario(this); }
    public void planificarRuta()                    { estado.confirmarEnvio(this); }
    public void despachar()                         { estado.despachar(this); }
    public void entregar()                          { estado.entregar(this); }
    public void fallarEntrega(String justificacion) { estado.cancelar(this, justificacion); }
    public void vencer()                            { estado.desecharPorVencimiento(this); }
    public void almacenar()                         { estado.almacenar(this); }

    @Override
    public String toString() {
        return "SubCategoriaBien: " + subcategoriaBien.getDescripcion() + "\n" +
                "Lista de bienes: " + bienes + "\n" +
                "DonacionDeOrigen: " + donacionDeOrigen.getDescripcion() + "\n" +
                "Estado actual: " + estado.nombre() + "\n";
    }
}