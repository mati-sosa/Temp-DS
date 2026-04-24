package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CantidadDeBien;
import lombok.Getter;

@Getter
public abstract class Necesidad {
    private String descripcion;
    private CategoriaBien categoria;
    private CantidadDeBien cantidadDeBien;
    private double cantidadCubierta = 0;

    public Necesidad(String descripcion, CategoriaBien categoria, CantidadDeBien cantidadDeBien) {
        if(descripcion == null) {
            throw new IllegalArgumentException("¡La necesidad debe tener una descripción!");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("¡La necesidad debe tener una categoría asociada!");
        }

        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cantidadDeBien = cantidadDeBien;
    }

    public void registrarDonacion(Bien bienDonado) {
        if (!this.categoria.contieneSubcategoria(bienDonado.getSubcategoria())) {
            throw new IllegalArgumentException("¡El bien no pertenece a la categoría de la necesidad!");
        }

        sumarBienes(bienDonado.getCantidad());
    }

    private void sumarBienes(Double cantidadDonada) {
        this.cantidadCubierta += cantidadDonada;
    }

    public abstract Boolean estaSatisfecha();
}
