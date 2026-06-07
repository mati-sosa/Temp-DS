package ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;


public class EntidadBeneficiaria {
    @Getter @Setter
    private Long id;
    @Getter
    private TipoEntidadBeneficiaria tipo;
    @Getter
    private String razonSocial;
    @Getter
    private Direccion direccion;
    @Getter
    private MedioDeContacto telefono;
    @Getter
    private List<Representante> representantes;
    private List<Necesidad> necesidades = null;
    private int donacionesRecibidasEnTrimestre = 0;

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
        if (telefono == null || telefono.getTipoMedioContacto() != TipoMedioContacto.SMS) {
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

    public List<Necesidad> getNecesidades() { return necesidades; }

    public int getDonacionesRecibidasEnTrimestre() { return donacionesRecibidasEnTrimestre; }

    public void registrarDonacionRecibida() { donacionesRecibidasEnTrimestre++; }

    public void registrarNecesidad(Necesidad nuevaNecesidad) {
        if (nuevaNecesidad == null) {
            throw new IllegalArgumentException("¡Se debe ingresar una necesidad válida!");
        }
        if (necesidades.contains(nuevaNecesidad)) {
            throw new IllegalArgumentException("¡Esa necesidad ya está registrada!");
        }

        necesidades.add(nuevaNecesidad);
    }

    public void actualizarNecesidad(int indice, Necesidad nueva) {
        if (nueva == null) {
            throw new IllegalArgumentException("¡Se debe ingresar una necesidad válida!");
        }
        if (indice < 0 || indice >= necesidades.size()) {
            throw new IllegalArgumentException("Índice de necesidad inválido: " + indice);
        }
        necesidades.set(indice, nueva);
    }

    @Override
    public String toString(){
        return tipo + "\n" +
               razonSocial + "\n" +
                direccion + "\n" +
                telefono + "\n" +
                representantes  + "\n" +
                necesidades;
    }
}