package ar.edu.utn.frba.ddsi.donaciones.models.entities;

public class Direccion {
    private String calle; // calle + número 
    private String ciudad;
    private String provincia;
    private String codigoPostal;

    public Direccion(String calle, String ciudad, String provincia, String codigoPostal) {
        validarDireccion(calle, ciudad, provincia, codigoPostal);
        
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }

    private void validarDireccion(String calle, String ciudad, String provincia, String codigoPostal) {
        // El código postal no es obligatorio, podría calcularse a partir de la calle, la ciudad y la provincia
        if(calle == null || ciudad == null || provincia == null) {
            throw new IllegalArgumentException("¡Debe ingresar una dirección completa!");
        }
    }
}
