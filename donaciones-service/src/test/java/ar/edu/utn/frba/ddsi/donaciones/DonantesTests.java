package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.*;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonantesTests {

    private static final List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
    MedioDeContacto medioDeContacto1 = new MedioDeContacto(TipoMedioContacto.SMS,"011 1234-5678");
    MedioDeContacto medioDeContacto2 = new MedioDeContacto(TipoMedioContacto.EMAIL, "homersimpson@outlook.com");
    MedioDeContacto medioDeContacto3 = new MedioDeContacto(TipoMedioContacto.SMS, "011 1234-1234");

    @Test
    void altaDonanteHumano(){
        mediosDeContacto.add(medioDeContacto1);
        mediosDeContacto.add(medioDeContacto2);
        Donante unDonante = new Donante(
                "Homero",
                "30.000.000",
                TipoDocumento.DNI,
                TipoPersona.FISICA,
                LocalDate.of(1997,8,2),
                mediosDeContacto,
                medioDeContacto2,
                new ArrayList<Representante>(),
                null,
                Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );
        System.out.println(unDonante);
    }

    @Test
    void altaDonanteHumanoConExcepcionEmail(){
        mediosDeContacto.add(medioDeContacto1);
        assertThrows(IllegalArgumentException.class, () -> {
            Donante unDonante = new Donante(
                    "Homero",
                    "30.000.000",
                    TipoDocumento.DNI,
                    TipoPersona.FISICA,
                    LocalDate.of(1997,8,2),
                    mediosDeContacto,
                    medioDeContacto1,
                    new ArrayList<Representante>(),
                    null,
                    Genero.MASCULINO,
                    new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
            );
        });
    }

    @Test
    void modificacionDonanteHumano(){
        mediosDeContacto.add(medioDeContacto1);
        mediosDeContacto.add(medioDeContacto2);

        Donante unDonante = new Donante(
                "Homero",
                "30.000.000",
                TipoDocumento.DNI,
                TipoPersona.FISICA,
                LocalDate.of(1997,8,2),
                mediosDeContacto,
                medioDeContacto1,
                new ArrayList<Representante>(),
                null,
                Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );
        System.out.println("Predeterminado antes del cambio: " + unDonante.getMedioPredeterminado());
        unDonante.cambiarMedioPredeterminado(medioDeContacto2);
        System.out.println("Predeterminado despues del cambio: " + unDonante.getMedioPredeterminado());
    }

    //-------------------------------------------------------------------------------------------------------//

    private static final List<Representante> representantes = new ArrayList<>();
    Representante representante1 = new Representante(
            "Marge",
            new MedioDeContacto(
                    TipoMedioContacto.EMAIL,
                    "margesimpson@outlook.com"
            )
    );
    Representante representante2 = new Representante(
            "Abraham",
            new MedioDeContacto(
                    TipoMedioContacto.EMAIL,
                    "011 1111-1111"
            )
    );

    @Test
    void altaDonanteJuridico(){
        representantes.add(representante1);
        mediosDeContacto.add(medioDeContacto1);

        Donante unDonanteJuridico = new Donante(
                "Los Simpsons S.A.",
                "10-11000111-1",
                TipoDocumento.CUIT,
                TipoPersona.EMPRESA,
                LocalDate.of(1990,1,15),
                mediosDeContacto,
                medioDeContacto1,
                representantes,
                new Rubro("Limpieza"),
                Genero.NO_APLICA,
                new Direccion("El jilguero","Springfield","Oregon","2026")
        );

        System.out.println(unDonanteJuridico);
    }

    @Test
    void altaDonanteJuridicoSinRepresentante(){
        mediosDeContacto.add(medioDeContacto1);

        assertThrows(IllegalArgumentException.class, () -> {
                new Donante(
                "Los Simpsons S.A.",
                "10-11000111-1",
                TipoDocumento.CUIT,
                TipoPersona.EMPRESA,
                LocalDate.of(1990,1,15),
                mediosDeContacto,
                medioDeContacto1,
                new ArrayList<Representante>(),
                new Rubro("Limpieza"),
                Genero.NO_APLICA,
                new Direccion("El jilguero","Springfield","Oregon","2026")
            );
        });
    }
}
