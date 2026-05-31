package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Rubro;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.TipoDocumento;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DonantesTests {
    /**
    private static final List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
    MedioDeContacto medioDeContacto1 = new MedioDeContacto(TipoMedioContacto.TELEFONO,"011 1234-5678");
    MedioDeContacto medioDeContacto2 = new MedioDeContacto(TipoMedioContacto.EMAIL, "homersimpson@outlook.com");
    MedioDeContacto medioDeContacto3 = new MedioDeContacto(TipoMedioContacto.TELEFONO, "011 1234-1234");

    @Test
    void altaDonanteHumano(){
        mediosDeContacto.add(medioDeContacto1);
        mediosDeContacto.add(medioDeContacto2);
        DonanteHumano unDonante = new DonanteHumano(
                "Homero",
                mediosDeContacto,
                medioDeContacto1,
                38,
                "30.000.000",
                TipoDocumento.DNI,
                Donante.Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );
        System.out.println(unDonante);
    }

    @Test
    void altaDonanteHumanoConExcepcionEmail(){
        mediosDeContacto.add(medioDeContacto1);

        DonanteHumano unDonante = new DonanteHumano(
                "Homero",
                mediosDeContacto,
                medioDeContacto1,
                38,
                "30.000.000",
                TipoDocumento.DNI,
                Donante.Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );
    }

    @Test
    void altaDonanteHumanoConExcepcionPreferencia(){
        mediosDeContacto.add(medioDeContacto2);

        DonanteHumano unDonante = new DonanteHumano(
                "Homero",
                mediosDeContacto,
                medioDeContacto3,
                38,
                "30.000.000",
                TipoDocumento.DNI,
                Donante.Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );
    }

    @Test
    void modificacionDonanteHumano(){
        mediosDeContacto.add(medioDeContacto1);
        mediosDeContacto.add(medioDeContacto2);

        DonanteHumano unDonante = new DonanteHumano(
                "Homero",
                mediosDeContacto,
                medioDeContacto1,
                38,
                "30.000.000",
                TipoDocumento.DNI,
                Donante.Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );
        System.out.println("Predeterminado antes del cambio " + unDonante.getMedioPredeterminado());
        unDonante.cambiarMedioPredeterminado(medioDeContacto2);
        System.out.println("Predeterminado despues del cambio " + unDonante.getMedioPredeterminado());
    }

    //-------------------------------------------------------------------------------------------------------//

    private static final List<Representante> representantes = new ArrayList<>();
    Representante representante1 = new Representante("Marge", new MedioDeContacto(TipoMedioContacto.EMAIL,"margesimpson@outlook.com"));
    Representante representante2 = new Representante("Abraham", new MedioDeContacto(TipoMedioContacto.EMAIL,"011 1111-1111"));

    @Test
    void altaDonanteJuridico(){
        representantes.add(representante1);
        mediosDeContacto.add(medioDeContacto1);

        DonanteJuridico unDonanteJuridico = new DonanteJuridico(
                "Los Simpsons S.A.",
                mediosDeContacto,
                TipoPersonaJuridica.EMPRESA,
                representantes,
                new Rubro("Actores")
        );

        System.out.println(unDonanteJuridico);
    }

    @Test
    void altaDonanteJuridicoSinRepresentante(){
        mediosDeContacto.add(medioDeContacto1);

        DonanteJuridico unDonanteJuridico = new DonanteJuridico(
                "Los Simpsons S.A.",
                mediosDeContacto,
                TipoPersonaJuridica.EMPRESA,
                representantes,
                new Rubro("Actores")
        );

        System.out.println(unDonanteJuridico);
    }

    @Test
    void altaVariosRepresentantes(){
        representantes.add(representante1);
        mediosDeContacto.add(medioDeContacto1);

        DonanteJuridico unDonanteJuridico = new DonanteJuridico(
                "Los Simpsons S.A.",
                mediosDeContacto,
                TipoPersonaJuridica.EMPRESA,
                representantes,
                new Rubro("Actores")
        );

        System.out.println(unDonanteJuridico);
        unDonanteJuridico.agregarRepresentante(representante2);
        System.out.println(unDonanteJuridico);
        unDonanteJuridico.eliminarRepresentante(representante1);
        System.out.println(unDonanteJuridico);
    }
    **/
}
