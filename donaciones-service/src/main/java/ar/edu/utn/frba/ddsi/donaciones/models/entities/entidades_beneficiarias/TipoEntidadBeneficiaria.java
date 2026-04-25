package ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias;

public class TipoEntidadBeneficiaria {
    private String tipoEntidad;
    private String descripcion;

    public TipoEntidadBeneficiaria(String tipoEntidad, String descripcion) {
        if (tipoEntidad == null) {
            throw new IllegalArgumentException("¡Se debe especificar el tipo de entidad!");
        }
        if (descripcion == null) {
            throw new IllegalArgumentException("¡Se debe ingresar una descripción válida!");
        }

        this.tipoEntidad = tipoEntidad;
        this.descripcion = descripcion;
    }
}