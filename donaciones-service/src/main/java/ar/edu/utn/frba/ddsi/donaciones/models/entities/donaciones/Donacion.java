package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.EstadoDonacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.EstadoEnDeposito;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Donacion {
    private SubcategoriaBien subcategoriaBien;
    private List<Bien> bienes;
    private DonacionTotal donacionDeOrigen;
    // Entrega 2
    private EstadoDonacion estado;
    public List<EstadoDonacion> historialEstados;

    public Donacion(
            SubcategoriaBien unaSubcategoriaBien,
            List<Bien> unosBienes,
            DonacionTotal unaDonacionDeOrigen
    ) {
        subcategoriaBien = unaSubcategoriaBien;
        bienes = unosBienes;
        donacionDeOrigen = unaDonacionDeOrigen;
        // Entrega 2: Al crearse la donacion va directo al deposito
        estado = new EstadoEnDeposito();
    }

    public void cambiarEstado(EstadoDonacion nuevoEstado){
        estado = nuevoEstado;
    }

    @Override
    public String toString(){
        return "SubCategoriaBien: " + subcategoriaBien.getDescripcion() + "\n" +
                "Lista de bienes: " + bienes + "\n" +
                "DonacionDeOrigen: " + donacionDeOrigen.getDescripcion() + "\n";
    }
}