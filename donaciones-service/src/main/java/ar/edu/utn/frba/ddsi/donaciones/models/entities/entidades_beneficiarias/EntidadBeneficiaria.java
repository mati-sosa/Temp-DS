package ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;

import java.util.List;
import java.util.ArrayList;


public class EntidadBeneficiaria {
    private TipoEntidadBeneficiaria tipo;
    private String razonSocial;
    private Direccion direccion;
    private MedioDeContacto telefono;
    private List<Representante> representantes;
    private List<Necesidad> necesidades = null;

    public EntidadBeneficiaria(TipoEntidadBeneficiaria tipo, String razonSocial, Direccion direccion, MedioDeContacto telefono, List<Representante> representantes, List<Necesidad> necesidades) {
        if (tipo == null) {
            throw new IllegalArgumentException("¡Se debe ingresar un tipo de entidad!");
        }
        if (razonSocial == null) {
            throw new IllegalArgumentException("¡La entidad debe tener una razón social!");
        }
        if (direccion == null) {
            throw new IllegalArgumentException("¡La entidad debe tener una dirección asociada!");
        }
        if (telefono == null || telefono.getTipoMedioContacto() != TipoMedioContacto.TELEFONO) {
            throw new IllegalArgumentException("¡El medio de contacto debe ser un teléfono válido!");
        }
        if (representantes == null || representantes.isEmpty()) {
            throw new IllegalArgumentException("¡La entidad beneficiaria debe tener al menos 1 representante!");
        }
        
        this.tipo = tipo;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.representantes = representantes;
        this.necesidades = necesidades;
    }

    public void registrarNecesidad(Necesidad nuevaNecesidad) {
        if (nuevaNecesidad == null) {
            throw new IllegalArgumentException("¡Se debe ingresar una necesidad válida!");
        }
        if (necesidades.contains(nuevaNecesidad)) {
            throw new IllegalArgumentException("¡Esa necesidad ya está registrada!");
        }

        necesidades.add(nuevaNecesidad);
    }
}