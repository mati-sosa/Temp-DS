package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DonacionesTest {

    // Alta Categoria
    CategoriaBien categoria1 = new CategoriaBien(
            "Alimentos"
    );

    CategoriaBien categoria2 = new CategoriaBien(
            "Vehiculos"
    );

    // Alta Subcategoria
    SubcategoriaBien subCategoria1 = new SubcategoriaBien(
            "Arroz",
            false,
            categoria1,
            UnidadDeMedida.KILOGRAMOS
    );

    SubcategoriaBien subCategoria2 = new SubcategoriaBien(
            "Bicicletas",
            false,
            categoria2,
            UnidadDeMedida.UNIDADES
    );

    @Test
    void altaBienPerecibleNoVencido(){
        BienPerecible bienPerecible1 = new BienPerecible(
                "Arroz",
                subCategoria1,
                10.0,
                "fotoArroz.jpg",
                LocalDate.of(2026,12,31)
        );

        System.out.println(bienPerecible1);
        System.out.println(bienPerecible1.estaVencido());
    }

    @Test
    void altaBienPerecibleVencido(){
        BienPerecible bienPerecible1 = new BienPerecible(
                "Arroz",
                subCategoria1,
                10.0,
                "fotoArroz.jpg",
                LocalDate.of(2026,01,31)
        );

        System.out.println(bienPerecible1);
        System.out.println(bienPerecible1.estaVencido());
    }

    @Test
    void altaBienNoPerecible(){
        BienNoPerecible bienNoPerecible1 = new BienNoPerecible(
                "Bicicleta tipo inglesa",
                subCategoria2,
                2.0,
                "fotoBicis.jpg"
        );
        System.out.println(bienNoPerecible1);
    }

    @Test
    void altaBienNoPerecibleConEstado(){
        BienConEstado bienConEstado = new BienConEstado(
                "Bicicleta tipo inglesa",
                subCategoria2,
                2.0,
                "fotoBicis.jpg",
                true
        );
        System.out.println(bienConEstado);
    }

}
