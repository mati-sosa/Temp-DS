package ar.edu.utn.frba.ddsi.notificaciones.config;

import com.resend.Resend;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    @Value("${twilio.account.sid}")
    private String twilioSID;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @PostConstruct
    public void InitializeTwilio() {
        Twilio.init(twilioSID, twilioAuthToken);
    }
}
