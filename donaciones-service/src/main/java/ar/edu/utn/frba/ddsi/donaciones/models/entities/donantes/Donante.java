package ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Donante {
    private String nombre_razonSocial;
    private String documento;
    private TipoDocumento tipoDocumento;
    private TipoPersona tipoPersona;
    private LocalDate fechaDeNacimiento_inicioActividad;
    public List<MedioDeContacto> mediosDeContacto;
    @Getter
    private MedioDeContacto medioPredeterminado;
    public List<Representante> representantes;
    private Rubro rubro;
    private Genero genero;
    private Direccion direccion;


    /********** CONSTRUCTOR **********/
    public Donante(
            String unNombre_razonSocial,
            String unDocumento,
            TipoDocumento unTipoDocumento,
            TipoPersona unTipoPersona,
            LocalDate unaFechaDeNacimiento_inicioActividad,
            List<MedioDeContacto> mediosDeContacto,
            MedioDeContacto unMedioPredeterminado,
            List<Representante> representantes,
            Rubro unRubro,
            Genero unGenero,
            Direccion unaDireccion
    ) {
        // LISTA DE MEDIOS DE CONTACTO VACÍA
        if (mediosDeContacto == null || mediosDeContacto.isEmpty()) {
            throw new IllegalArgumentException("¡El donante debe tener al menos un medio de contacto!");
        }

        if (unTipoPersona == TipoPersona.FISICA){
            boolean tieneEmail = mediosDeContacto.stream()
                    .anyMatch(m -> m.getTipoMedioContacto() == TipoMedioContacto.EMAIL);
            if (!tieneEmail) {
                throw new IllegalArgumentException("¡El donante debe tener al menos un email!");
            }
        }

        if (unTipoPersona == TipoPersona.EMPRESA){
            if (representantes.isEmpty()) {
                throw new IllegalArgumentException("¡La empresa debe tener al menos un representante!");
            }
        }

        nombre_razonSocial = unNombre_razonSocial;
        documento = unDocumento;
        tipoDocumento = unTipoDocumento;
        tipoPersona = unTipoPersona;
        fechaDeNacimiento_inicioActividad = unaFechaDeNacimiento_inicioActividad;
        this.mediosDeContacto = new ArrayList<>(mediosDeContacto);
        medioPredeterminado = unMedioPredeterminado;
        this.representantes = new ArrayList<>(representantes);
        rubro = unRubro;
        genero = unGenero;
        direccion = unaDireccion;
    }

    /********** MODIFICACIÓN DE LA LISTA DE MEDIOS DE CONTACTO **********/
    public void agregarMedio(MedioDeContacto nuevoMedioDeContacto) {
        if (nuevoMedioDeContacto == null) {
            throw new IllegalArgumentException("¡El medio a agregar no puede ser NULL!");
        }
        if (this.mediosDeContacto.contains(nuevoMedioDeContacto)) {
            throw new IllegalArgumentException("¡El donante ya tiene este medio de contacto!");
        }
        
        mediosDeContacto.add(nuevoMedioDeContacto);
    }
    public void eliminarMedio(MedioDeContacto medioDeContactoEliminado) {
        // Esto debería arrojar una excepción? 
        // Si el medio de contacto no existe en la lista de medios, 
        // no se eliminaría nada, y no tendría ningún impacto no deseado en el sistema...
        if (!this.mediosDeContacto.contains(medioDeContactoEliminado)) {
            throw new IllegalArgumentException("¡El donante no tiene ese medio de contacto!");
        }

        mediosDeContacto.remove(medioDeContactoEliminado);
    }

    public void cambiarMedioPredeterminado(MedioDeContacto nuevoMedioPredeterminado) {
        medioPredeterminado = nuevoMedioPredeterminado;
    }

    /********** REPRESENTANTES **********/
    public void agregarRepresentante(Representante nuevoRepresentante) {
        if (this.representantes.contains(nuevoRepresentante)) {
            throw new IllegalArgumentException("¡El representante ya está en la lista!");
        }

        this.representantes.add(nuevoRepresentante);
    }
    public void eliminarRepresentante(Representante representanteEliminado) {
        if (!this.representantes.contains(representanteEliminado)) {
            throw new IllegalArgumentException("¡El representante no está en la lista!");
        }
        if (this.representantes.size() == 1) {
            // No me gusta que tire la misma excepción que arriba...
            throw new IllegalArgumentException("¡El donante jurídico debe tener al menos un representante!");
        }

        this.representantes.remove(representanteEliminado);
    }

    @Override
    public String toString() {
        return "Donante: " + nombre_razonSocial + ", " + tipoDocumento + ": " + documento + ", tipo de persona:" + tipoPersona;
    }
}