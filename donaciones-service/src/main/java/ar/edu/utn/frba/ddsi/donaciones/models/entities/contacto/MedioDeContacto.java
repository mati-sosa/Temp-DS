package ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto;

import lombok.Getter;

@Getter
public class MedioDeContacto {
    private TipoMedioContacto tipoMedioContacto;
    private String direccion;
    private Boolean esPredeterminado;

    public MedioDeContacto(TipoMedioContacto tipoMedioContacto, String direccion, Boolean esPredeterminado) {
        this.tipoMedioContacto = tipoMedioContacto;
        this.direccion = direccion;
        this.esPredeterminado = esPredeterminado;
    }

    @Override
    public String toString(){
        return tipoMedioContacto.name() + " " + direccion;
    }
}
