package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Administrador;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Deposito;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.DonacionTotal;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Genero;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoDocumento;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoPersona;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.NecesidadRecurrente;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Periodo;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.TipoPeriodo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchmakingTest {
    private static final List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
    static MedioDeContacto medioDeContacto1 = new MedioDeContacto(TipoMedioContacto.TELEFONO,"011 1234-5678");
    static MedioDeContacto medioDeContacto2 = new MedioDeContacto(TipoMedioContacto.EMAIL, "homersimpson@outlook.com");

    ArrayList<Donacion> donaciones = new ArrayList<>();
    List<Bien> listaDeBienes = new ArrayList<>();
    Donacion donacion1 = new Donacion(
            new SubcategoriaBien(
                    "Arroz",
                    true,
                    new CategoriaBien("Alimentos"),
                    UnidadDeMedida.KILOGRAMOS
            ),
                    listaDeBienes,
                    new DonacionTotal(
                            LocalDate.of(2016,6,1),
                            "Donacion",
                            listaDeBienes,
                            new Administrador("Ulises", "01"),
                            new Donante(
                                    "",
                                    "",
                                    TipoDocumento.DNI,
                                    TipoPersona.FISICA,
                                    LocalDate.of(1997,8,2),
                                    mediosDeContacto,
                                    medioDeContacto1,
                                    new ArrayList<>(),
                                    null,
                                    Genero.MASCULINO,
                                    new Direccion("","","","")),
                            new Deposito()
                    )
    );

    Bien bien1 = new BienPerecible(
            "Arroz",
            new SubcategoriaBien(
                    "Arroz",
                    true,
                    new CategoriaBien("Alimentos"),
                    UnidadDeMedida.KILOGRAMOS
            ),
            5.0,
            "",
            LocalDate.of(2027,6,1)
    );

    ArrayList<Necesidad> necesidades = new ArrayList<>();

    NecesidadRecurrente necesidadRecurrente1 = new NecesidadRecurrente(
            "Arroz",
            new SubcategoriaBien(
                    "Arroz",
                    true,
                    new CategoriaBien("Alimentos"),
                    UnidadDeMedida.KILOGRAMOS
            ),
            20.0,
            5.0,
            new Periodo(TipoPeriodo.SEMANA,5)
    );

    NecesidadRecurrente necesidadRecurrente2 = new NecesidadRecurrente(
            "Arroz",
            new SubcategoriaBien(
                    "Arroz",
                    true,
                    new CategoriaBien("Alimentos"),
                    UnidadDeMedida.KILOGRAMOS
            ),
            20.0,
            5.0,
            new Periodo(TipoPeriodo.SEMANA,5)
    );
    NecesidadRecurrente necesidadRecurrente3 = new NecesidadRecurrente(
            "Fideos",
            new SubcategoriaBien(
                    "Fideos",
                    true,
                    new CategoriaBien("Alimentos"),
                    UnidadDeMedida.KILOGRAMOS
            ),
            20.0,
            5.0,
            new Periodo(TipoPeriodo.SEMANA,5)
    );

    @BeforeAll
    static void setup(){
        mediosDeContacto.add(medioDeContacto1);
        mediosDeContacto.add(medioDeContacto2);
    }

    @Test
    void pruebaDeGeneradorDeMatches(){
        donaciones.add(donacion1);
        listaDeBienes.add(bien1);
        necesidades.add(necesidadRecurrente1);
        necesidades.add(necesidadRecurrente2);
        necesidades.add(necesidadRecurrente3);

        GeneradorDeMatches generador = new GeneradorDeMatches();

        ArrayList<PosibleMatch> posiblesMatches = generador.generarMatches(donaciones, necesidades);
        System.out.println(posiblesMatches);
    }
    @Test
    void pruebaMatchmakingPrioridadSubatendidos(){
        listaDeBienes.add(bien1);
        MatchmakingPrioridadSubatendidos matchmakingPrioridadSubatendidos = new MatchmakingPrioridadSubatendidos(0.1,5);
        EvaluacionMatch unaEvaluacion = matchmakingPrioridadSubatendidos.evaluar(
                new PosibleMatch(donacion1,necesidadRecurrente1)
        );
        System.out.println(unaEvaluacion);
    }
    @Test
    void pruebaMatchmakingSemantico(){
        listaDeBienes.add(bien1);
        MatchmakingCompatibilidadSemantica matchmakingCompatibilidadSemantica = new MatchmakingCompatibilidadSemantica(0.5,0.3,0.2);
        EvaluacionMatch unaEvaluacion = matchmakingCompatibilidadSemantica.evaluar(
                new PosibleMatch(donacion1,necesidadRecurrente1)
        );
        System.out.println(unaEvaluacion);
    }
}
