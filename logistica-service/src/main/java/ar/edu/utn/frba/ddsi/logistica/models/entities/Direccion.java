package ar.edu.utn.frba.ddsi.logistica.models.entities;

@lombok.Getter
public class Direccion {
    private String calle;
    private String ciudad;
    private String provincia;
    private String codigoPostal;

    public Direccion(String calle, String ciudad, String provincia, String codigoPostal) {
        if (calle == null || ciudad == null || provincia == null) {
            throw new IllegalArgumentException("¡Debe ingresar una dirección completa!");
        }
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return calle + ", " + ciudad + ", " + provincia + ", " + codigoPostal;
    }
}
