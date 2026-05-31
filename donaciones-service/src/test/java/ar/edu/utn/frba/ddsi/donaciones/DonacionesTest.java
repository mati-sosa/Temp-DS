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
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonacionesTest {

    //-------------------- Alta Categoria --------------------//
    private CategoriaBien categoriaAlimentos = new CategoriaBien(
            "Alimentos"
    );

    private CategoriaBien categoriaVehiculos = new CategoriaBien(
            "Vehiculos"
    );

    private CategoriaBien categoriaMobiliario = new CategoriaBien(
            "Mobiliario"
    );

    private CategoriaBien categoriaHigiene = new CategoriaBien(
            "Higiene"
    );

    private CategoriaBien categoriaMedicamentos = new CategoriaBien(
            "Medicamentos"
    );

    //-------------------- Alta Subcategoria --------------------//
    private SubcategoriaBien categoriaArroz = new SubcategoriaBien(
            "Arroz",
            false,
            categoriaAlimentos,
            UnidadDeMedida.KILOGRAMOS
    );

    private SubcategoriaBien subCategoriaFrutas = new SubcategoriaBien(
            "Frutas",
            true,
            categoriaAlimentos,
            UnidadDeMedida.KILOGRAMOS
    );

    private SubcategoriaBien subCategoriaJabon = new SubcategoriaBien(
            "Jabon",
            false,
            categoriaHigiene,
            UnidadDeMedida.UNIDADES
    );

    private SubcategoriaBien subCategoriaIbuprofeno = new SubcategoriaBien(
            "Ibuprofeno 400",
            false,
            categoriaMedicamentos,
            UnidadDeMedida.UNIDADES
    );

    private SubcategoriaBien subCategoriaBicicleta = new SubcategoriaBien(
            "Bicicletas",
            false,
            categoriaVehiculos,
            UnidadDeMedida.UNIDADES
    );

    private SubcategoriaBien subCategoriaMesa = new SubcategoriaBien(
            "Mesa",
            false,
            categoriaMobiliario,
            UnidadDeMedida.UNIDADES
    );

    private SubcategoriaBien subCategoriaSilla = new SubcategoriaBien(
            "Silla",
            false,
            categoriaMobiliario,
            UnidadDeMedida.UNIDADES
    );

    //-------------------- Alta Donantes --------------------//
    private static final List<MedioDeContacto> mediosDeContacto = new ArrayList<>();

    MedioDeContacto medioDeContacto1 = new MedioDeContacto(
            TipoMedioContacto.TELEFONO,
            "011 1234-5678",
            false
    );
    MedioDeContacto medioDeContacto2 = new MedioDeContacto(
            TipoMedioContacto.EMAIL,
            "homersimpson@outlook.com",
            true
    );

    @Test
    void altaBienPerecibleNoVencido(){
        BienPerecible bienPerecible1 = new BienPerecible(
                "Arroz",
                categoriaArroz,
                10.0,
                "fotoArroz.jpg",
                LocalDate.of(2026,12,31)
        );

        System.out.println(bienPerecible1);
        System.out.println(bienPerecible1.estaVencido());
    }

    //-------------------- Alta Administrador --------------------//
    Administrador unAdministrador = new Administrador(
            "LaSudestada",
            "001"
    );

    //-------------------- Alta Deposito --------------------//
    Deposito unDeposito = new Deposito();



    //-------------------- TEST BIENES --------------------//

    @Test
    void altaBienPerecibleVencido(){
        BienPerecible bienPerecible1 = new BienPerecible(
                "Arroz",
                categoriaArroz,
                10.0,
                "fotoArroz.jpg",
                LocalDate.of(2026,01,31)
        );

        System.out.println(bienPerecible1);
        System.out.println(bienPerecible1.estaVencido());
    }

    @Test
    void altaBienNoPerecibleConEstado(){
        BienNoPerecible bienConEstado = new BienNoPerecible(
                "Bicicleta tipo inglesa",
                subCategoriaBicicleta,
                2.0,
                "fotoBicis.jpg",
                true,
                true
        );
        System.out.println(bienConEstado);
    }

    @Test
    void altaBienNoPerecibleSinEstado(){
        BienNoPerecible bienSinEstado = new BienNoPerecible(
                "Cajas con 1 blister de 10 comprimidos blandos c/u",
                subCategoriaIbuprofeno,
                30.0,
                "fotoBicis.jpg",
                false,
                false
        );
        System.out.println(bienSinEstado);
    }

    //-------------------- TEST DONACIONES --------------------//

    List<Bien> bienesADonarPorMudanza = new ArrayList<>();

    @Test
    void altaDonacion(){
        // Set Up Donante
        mediosDeContacto.add(medioDeContacto1);
        mediosDeContacto.add(medioDeContacto2);
        Donante homeroDonante = new Donante(
                "Homero",
                "30.000.000",
                TipoDocumento.DNI,
                TipoPersona.FISICA,
                LocalDate.of(1970, 5, 30),
                mediosDeContacto,
                new ArrayList<>(),
                null,
                Genero.MASCULINO,
                new Direccion("Av. Siempreviva", "Springfield", "Oregon", "2026")
        );

        // Set Up Bienes a donar
        bienesADonarPorMudanza.add(new BienNoPerecible(
                "Mesa para 6 personas",
                subCategoriaMesa,
                1.0,
                "fotoMesaDonar.jpg",
                true,
                true
        ));
        bienesADonarPorMudanza.add(new BienNoPerecible(
                "Sillas de madera",
                subCategoriaSilla,
                6.0,
                "fotoSillasMAderaDonar.jpg",
                true,
                false
        ));
        bienesADonarPorMudanza.add(new BienNoPerecible(
                "Sillas de plastico",
                subCategoriaSilla,
                4.0,
                "fotoSillasPlasticoDonar.jpg",
                true,
                false
        ));
        bienesADonarPorMudanza.add(new BienPerecible(
                "Cajon de naranjas",
                subCategoriaFrutas,
                10.0,
                "cajonDeNaranjas.jpg",
                LocalDate.of(2026,04,30)
        ));
        bienesADonarPorMudanza.add(new BienNoPerecible(
                "Jabon blanco",
                subCategoriaJabon,
                10.0,
                "fotoJabones.jpg",
                false,
                false
        ));

        DonacionTotal donacionTotal1 = new DonacionTotal(
                LocalDate.now(),
                "Donacion por mudanza",
                bienesADonarPorMudanza,
                unAdministrador,
                homeroDonante,
                unDeposito
        );

        //System.out.println(donacionTotal1);
        donacionTotal1.segmentarDonacion();
        System.out.println(donacionTotal1);
    }

}
