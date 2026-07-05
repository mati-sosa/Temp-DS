package ar.edu.utn.frba.ddsi.logistica;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

public class PlanificadorTest {

    @Test
    void probarCallBackURL(){
        Dotenv dotenv = Dotenv.load();

        String callbackUrl = dotenv.get("RUTAS_CALLBACK_URL");

        System.out.println("La URL de callback es: " + callbackUrl);
    }
}
