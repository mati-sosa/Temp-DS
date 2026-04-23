package ar.edu.utn.frba.ddsi.donaciones.models.entities;
import java.util.List;

public class DonanteHumano extends Donante {
    private int edad;
    private String numeroDocumento;
    private TipoDocumento tipoDocumento;
    private Genero genero;
    private Direccion direccion;
    private MedioDeContacto medioPredeterminado;

    public DonanteHumano(String nombre, 
            List<MedioDeContacto> mediosDeContacto, 
            MedioDeContacto medioPredeterminado, 
            int edad, 
            String numeroDocumento,
            TipoDocumento tipoDocumento,
            Genero genero,
            Direccion direccion) {
        super(nombre, mediosDeContacto);
        
        // VALIDACIÓN DE EMAIL
        boolean tieneEmail = mediosDeContacto.stream().anyMatch(m -> m instanceof Email);
        if (!tieneEmail) {
            throw new IllegalArgumentException("¡El donante debe tener al menos un email!");
        }
        if(edad < 18) {
            throw new IllegalArgumentException("¡El donante debe ser mayor de edad!");
        }
        validarMedioPredeterminado(mediosDeContacto, medioPredeterminado);

        this.edad = edad;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.genero = genero;
        this.direccion = direccion;
        this.medioPredeterminado = medioPredeterminado;
    }

    private void validarMedioPredeterminado(List<MedioDeContacto> mediosDeContacto, MedioDeContacto medioPredeterminado) {
        if (medioPredeterminado == null) {
            throw new IllegalArgumentException("¡El donante debe tener un medio predeterminado!");
        }
        if (!mediosDeContacto.contains(medioPredeterminado)) {
            // Se podría agregar el medio predeterminado a la lista de medios de contacto del donante...
            // pero podría darse el caso de que se pasa un medioPredeterminado erróneo y el sistema lo toma como válido
            throw new IllegalArgumentException("¡El medio predeterminado debe pertenecer a la lista!");
        }
    }

    @Override
    public void eliminarMedio(MedioDeContacto medioDeContactoEliminado) {
        if (this.medioPredeterminado == medioDeContactoEliminado) {
            throw new IllegalArgumentException("¡No se puede eliminar el medio de contacto predeterminado!");
        }
        // No me gusta esta línea...
        // la alternativa sería agregar un método que haga exactamente esto ¯\_(ツ)_/¯
        long cantidadEmails = this.mediosDeContacto.stream().filter(medio -> medio instanceof Email).count();
        if (medioDeContactoEliminado instanceof Email && cantidadEmails == 1) {
            throw new IllegalArgumentException("¡No es posible eliminar el único email del donante!");
        }
        
        super.eliminarMedio(medioDeContactoEliminado);
    }
    public void cambiarMedioPredeterminado(MedioDeContacto nuevoMedioPredeterminado) {
        validarMedioPredeterminado(this.mediosDeContacto, nuevoMedioPredeterminado);
        
        medioPredeterminado = nuevoMedioPredeterminado;
    }
}