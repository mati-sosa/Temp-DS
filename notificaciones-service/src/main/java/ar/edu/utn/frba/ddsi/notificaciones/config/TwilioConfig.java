package ar.edu.utn.frba.ddsi.notificaciones.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account.sid}")
    private String twilioSID;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @PostConstruct
    public void InitializeTwilio() {
        Twilio.init(twilioSID, twilioAuthToken);
    }
}
