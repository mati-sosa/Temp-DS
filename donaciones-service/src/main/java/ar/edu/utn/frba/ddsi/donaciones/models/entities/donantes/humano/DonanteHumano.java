package ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.humano;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import lombok.Getter;

import java.util.List;

@Getter
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

        boolean tieneEmail = mediosDeContacto.stream()
                .anyMatch(m -> m.getTipoMedioContacto() == TipoMedioContacto.EMAIL);
        if (!tieneEmail) {
            throw new IllegalArgumentException("¡El donante debe tener al menos un email!");
        }
        if (edad < 18) {
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
            throw new IllegalArgumentException("¡El medio predeterminado debe pertenecer a la lista!");
        }
    }

    @Override
    public void eliminarMedio(MedioDeContacto medioDeContactoEliminado) {
        if (this.medioPredeterminado == medioDeContactoEliminado) {
            throw new IllegalArgumentException("¡No se puede eliminar el medio de contacto predeterminado!");
        }
        long cantidadEmails = this.mediosDeContacto.stream()
                .filter(m -> m.getTipoMedioContacto() == TipoMedioContacto.EMAIL)
                .count();
        if (medioDeContactoEliminado.getTipoMedioContacto() == TipoMedioContacto.EMAIL && cantidadEmails == 1) {
            throw new IllegalArgumentException("¡No es posible eliminar el único email del donante!");
        }

        super.eliminarMedio(medioDeContactoEliminado);
    }

    public void cambiarMedioPredeterminado(MedioDeContacto nuevoMedioPredeterminado) {
        validarMedioPredeterminado(this.mediosDeContacto, nuevoMedioPredeterminado);
        medioPredeterminado = nuevoMedioPredeterminado;
    }


    @Override
    public String toString(){
        return nombre + " " + mediosDeContacto;
    }
}
