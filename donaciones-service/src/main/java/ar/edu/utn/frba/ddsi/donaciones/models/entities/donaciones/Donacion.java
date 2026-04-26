package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Donacion {
    private String descripcion;
    private List<Bien> bienes;
    @Setter
//    private EstadoDonacion estado;
    private Donante donante;

    public Donacion(String descripcion, List<Bien> bienes, Donante donante) {
        if(descripcion == null) {
            throw new IllegalArgumentException("¡Se debe ingresar una descripción!");
        }
        if (bienes == null || bienes.isEmpty()) {
            throw new IllegalArgumentException("¡La donación debe tener bienes asociados!");
        }
        if (donante == null) {
            throw new IllegalArgumentException("¡La donación debe tener un donante asociado!");
        }
        
        this.descripcion = descripcion;
        this.bienes = bienes;
//        this.estado = EstadoDonacion.EN_DEPOSITO;
        this.donante = donante;
    }

    public Double cantidadDeUnidadesTotales() {
        return bienes.stream().mapToDouble(Bien::getCantidad).sum();
    }



    @Override
    public String toString(){
        return "Descripcion: " + descripcion + "\n" +
                "Lista de bienes: " + bienes + "\n" +
//                "Estado: " + estado + "\n" +
                "Donante: " + donante;
    }
}