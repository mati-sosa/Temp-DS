package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
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
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.TipoEntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.NecesidadRecurrente;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Periodo;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.TipoPeriodo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchmakingTest {
    private static final List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
    static MedioDeContacto medioDeContactoSms = new MedioDeContacto(TipoMedioContacto.SMS, "011 1234-5678");
    static MedioDeContacto medioDeContactoEmail = new MedioDeContacto(TipoMedioContacto.EMAIL, "homersimpson@outlook.com");

    ArrayList<Donacion> donaciones = new ArrayList<>();
    ArrayList<EntidadBeneficiaria> entidades = new ArrayList<>();
    List<Bien> listaDeBienes = new ArrayList<>();

    SubcategoriaBien subcategoriaArroz = new SubcategoriaBien(
            "Arroz", true, new CategoriaBien("Alimentos"), UnidadDeMedida.KILOGRAMOS);

    Donacion donacion1;
    Bien bien1;
    EntidadBeneficiaria entidad1;
    NecesidadRecurrente necesidadRecurrente1;
    NecesidadRecurrente necesidadRecurrente2;
    NecesidadRecurrente necesidadRecurrente3;

    @BeforeAll
    static void setupClass() {
        mediosDeContacto.add(medioDeContactoSms);
        mediosDeContacto.add(medioDeContactoEmail);
    }

    @BeforeEach
    void setup() {
        bien1 = new BienPerecible("Arroz", subcategoriaArroz, 5.0, "", LocalDate.of(2027, 6, 1));

        donacion1 = new Donacion(
                subcategoriaArroz,
                listaDeBienes,
                new DonacionTotal(
                        LocalDate.of(2016, 6, 1),
                        "Donacion",
                        listaDeBienes,
                        new Administrador("Ulises", "01"),
                        new Donante("", "", TipoDocumento.DNI, TipoPersona.FISICA,
                                LocalDate.of(1997, 8, 2), mediosDeContacto, medioDeContactoSms,
                                new ArrayList<>(), null, Genero.MASCULINO,
                                new Direccion("", "", "", "")),
                        new Deposito()
                )
        );

        necesidadRecurrente1 = new NecesidadRecurrente("Arroz", subcategoriaArroz, 20.0, 5.0, new Periodo(TipoPeriodo.SEMANA, 5));
        necesidadRecurrente2 = new NecesidadRecurrente("Arroz", subcategoriaArroz, 20.0, 5.0, new Periodo(TipoPeriodo.SEMANA, 5));
        necesidadRecurrente3 = new NecesidadRecurrente("Fideos",
                new SubcategoriaBien("Fideos", true, new CategoriaBien("Alimentos"), UnidadDeMedida.KILOGRAMOS),
                20.0, 5.0, new Periodo(TipoPeriodo.SEMANA, 5));

        List<Necesidad> necesidades = new ArrayList<>();
        necesidades.add(necesidadRecurrente1);
        necesidades.add(necesidadRecurrente2);

        entidad1 = new EntidadBeneficiaria(
                new TipoEntidadBeneficiaria("Comedor", "Comedor comunitario"),
                "Comedor Esperanza",
                new Direccion("Av. Siempreviva", "Springfield", "Buenos Aires", "1234"),
                medioDeContactoSms,
                List.of(new Representante("Homero", medioDeContactoEmail)),
                necesidades
        );
    }

    @Test
    void pruebaDeGeneradorDeMatches() {
        listaDeBienes.add(bien1);
        donaciones.add(donacion1);
        entidades.add(entidad1);

        GeneradorDeMatches generador = new GeneradorDeMatches();
        ArrayList<PosibleMatch> posiblesMatches = generador.generarMatches(donaciones, entidades);
        System.out.println(posiblesMatches);
    }

    @Test
    void pruebaMatchmakingPrioridadSubatendidos() {
        listaDeBienes.add(bien1);
        MatchmakingPrioridadSubatendidos mm = new MatchmakingPrioridadSubatendidos(0.1);
        EvaluacionMatch unaEvaluacion = mm.evaluar(new PosibleMatch(donacion1, necesidadRecurrente1, entidad1));
        System.out.println(unaEvaluacion);
    }

    @Test
    void pruebaMatchmakingSemantico() {
        listaDeBienes.add(bien1);
        MatchmakingCompatibilidadSemantica mm = new MatchmakingCompatibilidadSemantica(0.5, 0.3, 0.2);
        EvaluacionMatch unaEvaluacion = mm.evaluar(new PosibleMatch(donacion1, necesidadRecurrente1, entidad1));
        System.out.println(unaEvaluacion);
    }

    @Test
    void pruebaMotorDeMatchmaking() {
        listaDeBienes.add(bien1);
        donaciones.add(donacion1);
        entidades.add(entidad1);

        MotorDeMatchmaking motor = new MotorDeMatchmaking(List.of(
                new MatchmakingCompatibilidadSemantica(0.4, 0.4, 0.2),
                new MatchmakingPrioridadSubatendidos(0.1)
        ));
        List<List<EvaluacionMatch>> resultado = motor.ejecutar(donaciones, entidades);
        System.out.println(resultado);
    }
}
