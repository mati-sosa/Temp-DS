package ar.edu.utn.frba.ddsi.donaciones.models.entities;
import java.util.ArrayList;
import java.util.List;

public abstract class Donante {
    private String nombre;
    public List<MedioDeContacto> mediosDeContacto;

    public Donante(String nombre, List<MedioDeContacto> mediosDeContacto) {
        // LISTA DE MEDIOS DE CONTACTO VACÍA
        if (mediosDeContacto == null || mediosDeContacto.isEmpty()) {
            throw new IllegalArgumentException("¡El donante debe tener al menos un medio de contacto!");
        }

        this.nombre = nombre;
        this.mediosDeContacto = new ArrayList<>(mediosDeContacto);
    }

    // MODIFICACIÓN DE LA LISTA DE MEDIOS DE CONTACTO
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
}