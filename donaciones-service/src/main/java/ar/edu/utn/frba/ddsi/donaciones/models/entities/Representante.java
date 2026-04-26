package ar.edu.utn.frba.ddsi.donaciones.models.entities;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import lombok.Getter;

@Getter
public class Representante {
    private String nombre;
    private MedioDeContacto email;

    public Representante(String nombre, MedioDeContacto email) {
        if (nombre == null) {
            throw new IllegalArgumentException("¡El representante debe tener un nombre!");
        }

        //Esta validación necesita una vuelta de rosca, quizas una REGEX
        if (email == null || email.getTipoMedioContacto() != TipoMedioContacto.EMAIL) {
            throw new IllegalArgumentException("¡El medio de contacto debe ser un email válido!");
        }
        this.nombre = nombre;
        this.email = email;
    }

    @Override
    public String toString(){
        return nombre + ' ' + email;
    }
}