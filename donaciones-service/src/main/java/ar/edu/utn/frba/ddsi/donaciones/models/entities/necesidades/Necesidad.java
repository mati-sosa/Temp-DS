package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import lombok.Getter;

@Getter
public abstract class Necesidad {
    private String descripcion;
    private SubcategoriaBien subcategoria;
    private Double cantidadRequerida;
    private double cantidadCubierta = 0;

    public Necesidad(String descripcion, SubcategoriaBien subcategoria, Double cantidadRequerida) {
        if(descripcion == null) {
            throw new IllegalArgumentException("¡La necesidad debe tener una descripción!");
        }
        if (subcategoria == null) {
            throw new IllegalArgumentException("¡La necesidad debe tener una categoría asociada!");
        }
        if (cantidadRequerida == null || cantidadRequerida <= 0) {
            throw new IllegalArgumentException("¡Se debe ingresar una cantidad válida de bienes requeridos!");
        }

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadRequerida = cantidadRequerida;
    }

    public void recibirDonacion(Bien bienDonado) {
        if (this.subcategoria != bienDonado.getSubcategoria()) {
            throw new IllegalArgumentException("¡El bien no pertenece a la subcategoría de la necesidad!");
        }
        if (bienDonado.getYaFueDonado()) {
            throw new IllegalArgumentException("¡El bien ya fue donado previamente!");
        }

        sumarBienes(bienDonado.getCantidad());
    }

    private void sumarBienes(Double cantidadDonada) {
        this.cantidadCubierta += cantidadDonada;
    }

    public abstract Boolean estaSatisfecha();
}