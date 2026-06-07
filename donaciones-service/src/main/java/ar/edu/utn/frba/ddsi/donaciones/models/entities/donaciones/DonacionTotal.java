package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones.EstadoDonacion;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DonacionTotal {
    private LocalDate fechaHora;
    @Getter
    private String descripcion;
    public List<Bien> bienes;
    private Administrador administrador;
    @Getter
    private Donante donante;
    private Deposito deposito;

    @Getter
    List<Donacion> donacionesSegmentadas = new ArrayList<>();

    /********** CONSTRUCTOR **********/
    public DonacionTotal(
            LocalDate unaFechaHora,
            String unaDescripcion,
            List<Bien> unaListadeBienes,
            Administrador unAdministrador,
            Donante unDonante,
            Deposito unDeposito
    ) {
        fechaHora = unaFechaHora;
        descripcion = unaDescripcion;
        bienes = unaListadeBienes;
        administrador = unAdministrador;
        donante = unDonante;
        deposito = unDeposito;
    }

    public void segmentarDonacion(){
        donacionesSegmentadas = bienes.stream().
                collect(Collectors.groupingBy(Bien::getSubcategoria)).
                entrySet().
                stream().
                map(entry ->
                        new Donacion(
                                entry.getKey(),
                                entry.getValue(),
                                this
                        )
                ).
                toList();
    }

    @Override
    public String toString() {
        return "DonacionTotal{" +
                "fechaHora=" + fechaHora +
                "\n descripcion='" + descripcion + '\'' +
                "\n bienes=" + bienes +
                "\n administrador=" + administrador +
                "\n donante=" + donante +
                "\n deposito=" + deposito +
                "\n donacionesSegmentadas=\n" + donacionesSegmentadas +
                '}';
    }
}
