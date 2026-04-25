package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BienPerecible extends Bien {
    private LocalDate fechaDeVencimiento;

    public BienPerecible(String descripcion, SubcategoriaBien subcategoria, Double cantidadDeBien, String foto, LocalDate fechaDeVencimiento) {
        super(descripcion, subcategoria, cantidadDeBien, foto);

        if (fechaDeVencimiento == null) {
            throw new IllegalArgumentException("¡Se debe ingresar una fecha de vencimiento!");
        }
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaDeVencimiento);
    }
}
