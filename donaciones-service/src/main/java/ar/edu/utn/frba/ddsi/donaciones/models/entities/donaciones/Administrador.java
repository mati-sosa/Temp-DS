package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import java.util.ArrayList;

public class Administrador {
    private String nombre;
    private String id;

    public ArrayList<DonacionTotal> donacionesTotales;

    public Administrador(String unNombre, String unId){
        nombre = unNombre;
        id = unId;
    }

    public void registrarDonacion(DonacionTotal unaDonacionTotal){
        donacionesTotales.add(unaDonacionTotal);
    }
}
