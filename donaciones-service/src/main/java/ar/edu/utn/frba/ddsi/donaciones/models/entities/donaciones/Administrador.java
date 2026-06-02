package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;

import java.util.ArrayList;

public class Administrador {
    private String nombre;
    private String id;

    public ArrayList<DonacionTotal> donacionesTotales;
    public ArrayList<EntidadBeneficiaria> entidadesBeneficiarias;

    public Administrador(String unNombre, String unId){
        nombre = unNombre;
        id = unId;
    }

    public void registrarDonacion(DonacionTotal unaDonacionTotal){
        donacionesTotales.add(unaDonacionTotal);
    }

    public void registrarEntidadBeneficiaria(EntidadBeneficiaria unaEntidadBeneficiaria) {
        entidadesBeneficiarias.add(unaEntidadBeneficiaria);
    }
}
