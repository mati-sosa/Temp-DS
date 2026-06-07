package ar.edu.utn.frba.ddsi.incentivos.services.difusion;

import ar.edu.utn.frba.ddsi.incentivos.models.eventos.InsigniaObtenida;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Adapter hacia la herramienta de automatización low-code (n8n). Publica la insignia obtenida
 * haciendo un POST al webhook de n8n; n8n se encarga de generar el texto + imagen y publicar en
 * la red social.
 *
 * <p>La URL del webhook se configura en application.properties (n8n.webhook.url). Si no está
 * configurada, no rompe: simplemente registra que difundiría (útil para tests y desarrollo).
 */
@Service
public class N8nDifusorAdapter implements DifusorDeInsignias {
  private final String webhookUrl;
  private final RestClient restClient;

  public N8nDifusorAdapter(@Value("${n8n.webhook.url:}") String webhookUrl) {
    this.webhookUrl = webhookUrl;
    this.restClient = RestClient.create();
  }

  @Override
  public void difundir(InsigniaObtenida insignia) {
    if (webhookUrl == null || webhookUrl.isBlank()) {
      System.out.println("[n8n] (sin webhook configurado) Se difundiría: " + insignia.getTexto());
      return;
    }
    try {
      restClient.post()
          .uri(webhookUrl)
          .body(insignia)
          .retrieve()
          .toBodilessEntity();
    } catch (RuntimeException e) {
      // No bloquear la operación del donante si n8n falla.
      System.out.println("[n8n] Error al difundir la insignia: " + e.getMessage());
    }
  }
}
