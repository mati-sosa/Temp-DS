package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Donacion {
    private SubcategoriaBien subcategoriaBien;
    private List<Bien> bienes;
    private DonacionTotal donacionDeOrigen;

    public Donacion(SubcategoriaBien unaSubcategoriaBien, List<Bien> unosBienes, DonacionTotal unaDonacionDeOrigen) {
        subcategoriaBien = unaSubcategoriaBien;
        bienes = unosBienes;
        donacionDeOrigen = unaDonacionDeOrigen;
    }

    @Override
    public String toString(){
        return "SubCategoriaBien: " + subcategoriaBien.getDescripcion() + "\n" +
                "Lista de bienes: " + bienes + "\n" +
                "DonacionDeOrigen: " + donacionDeOrigen.getDescripcion() + "\n";
    }
}