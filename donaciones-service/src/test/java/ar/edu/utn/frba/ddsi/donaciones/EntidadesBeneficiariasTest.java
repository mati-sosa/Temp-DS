package ar.edu.utn.frba.ddsi.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.Direccion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Representante;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.TipoEntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EntidadesBeneficiariasTest {

    final List<Representante> representantesEscuela1 = new ArrayList<>();
    Representante skinner = new Representante(
            "Seymour Skinner",
            new MedioDeContacto(
                    TipoMedioContacto.EMAIL,
                    "seymourskinner@ar.edu.springfield.com")
    );

    final List<Necesidad> necesidadesExtraordinariasEscuelaPrimariaSrpingfield = new ArrayList<>();
    NecesidadExtraordinaria necesidadComputadoras = new NecesidadExtraordinaria(
            "Computadoras para el aula de computacion",
            new SubcategoriaBien(
                    "Computadoras",
                    false,
                    new CategoriaBien("Computacion"),
                    UnidadDeMedida.UNIDADES
            ),
            10.0
    );
    NecesidadExtraordinaria necesidadJuegosParque = new NecesidadExtraordinaria(
            "La EPS solicita juegos para el recreo como toboganes, sube y baja, y hamacas",
            new SubcategoriaBien(
                    "Juegos de exterior",
                    false,
                    new CategoriaBien("Juegos"),
                    UnidadDeMedida.UNIDADES
            ),
            10.0
    );

    final List<Necesidad> necesidadesRecurrentesEscuelaPrimariaSrpingfield = new ArrayList<>();
    NecesidadRecurrente necesidadHojas = new NecesidadRecurrente(
            "Necesitamos resmas de hojas blancas",
            new SubcategoriaBien(
                    "Resma de hojas",
                    false,
                    new CategoriaBien("Insumos de oficina"),
                    UnidadDeMedida.UNIDADES
            ),
            4.0,
            1.0,
            new Periodo(TipoPeriodo.MES,2)
    );

    @Test
    void altaEntidadBeneficiaria(){
        representantesEscuela1.add(skinner);

        EntidadBeneficiaria entidadBeneficiariaEscuela1 = new EntidadBeneficiaria(
                new TipoEntidadBeneficiaria(
                        "Escuela",
                        "Escuela Primaria de Springfield"),
                "Escuela Primaria de Springfield",
                new Direccion(
                        "Plympton Street 19",
                        "Springfield",
                        "Oregon",
                        "97403"
                ),
                new MedioDeContacto(
                        TipoMedioContacto.TELEFONO,
                        "011 1231-1231"
                ),
                representantesEscuela1,
                necesidadesExtraordinariasEscuelaPrimariaSrpingfield
        );
        System.out.println(entidadBeneficiariaEscuela1);
    }

    @Test
    void altaNecesidadExtraordinaria(){
        representantesEscuela1.add(skinner);

        EntidadBeneficiaria entidadBeneficiariaEscuela1 = new EntidadBeneficiaria(
                new TipoEntidadBeneficiaria(
                        "Escuela",
                        "Escuela Primaria de Springfield"),
                "Escuela Primaria de Springfield",
                new Direccion(
                        "Plympton Street 19",
                        "Springfield",
                        "Oregon",
                        "97403"
                ),
                new MedioDeContacto(
                        TipoMedioContacto.TELEFONO,
                        "011 1231-1231"
                ),
                representantesEscuela1,
                necesidadesExtraordinariasEscuelaPrimariaSrpingfield
        );

        entidadBeneficiariaEscuela1.registrarNecesidad(necesidadJuegosParque);
        System.out.println(entidadBeneficiariaEscuela1);
    }

    @Test
    void altaNecesidadRecurrente(){
        representantesEscuela1.add(skinner);

        EntidadBeneficiaria entidadBeneficiariaEscuela1 = new EntidadBeneficiaria(
                new TipoEntidadBeneficiaria(
                        "Escuela",
                        "Escuela Primaria de Springfield"),
                "Escuela Primaria de Springfield",
                new Direccion(
                        "Plympton Street 19",
                        "Springfield",
                        "Oregon",
                        "97403"
                ),
                new MedioDeContacto(
                        TipoMedioContacto.TELEFONO,
                        "011 1231-1231"
                ),
                representantesEscuela1,
                necesidadesRecurrentesEscuelaPrimariaSrpingfield
        );

        entidadBeneficiariaEscuela1.registrarNecesidad(necesidadHojas);
        System.out.println(entidadBeneficiariaEscuela1);
    }

    @Test
    void entidadRecibeDonacion(){
        representantesEscuela1.add(skinner);

        EntidadBeneficiaria entidadBeneficiariaEscuela1 = new EntidadBeneficiaria(
                new TipoEntidadBeneficiaria(
                        "Escuela",
                        "Escuela Primaria de Springfield"),
                "Escuela Primaria de Springfield",
                new Direccion(
                        "Plympton Street 19",
                        "Springfield",
                        "Oregon",
                        "97403"
                ),
                new MedioDeContacto(
                        TipoMedioContacto.TELEFONO,
                        "011 1231-1231"
                ),
                representantesEscuela1,
                necesidadesExtraordinariasEscuelaPrimariaSrpingfield
        );

        entidadBeneficiariaEscuela1.registrarNecesidad(necesidadJuegosParque);
        System.out.println(entidadBeneficiariaEscuela1);

    }
}
