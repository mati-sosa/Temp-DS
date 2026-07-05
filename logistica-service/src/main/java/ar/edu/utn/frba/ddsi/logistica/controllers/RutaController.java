package ar.edu.utn.frba.ddsi.logistica.controllers;

import ar.edu.utn.frba.ddsi.logistica.dto.*;
import ar.edu.utn.frba.ddsi.logistica.dto.mappers.RutaMapper;
import ar.edu.utn.frba.ddsi.logistica.security.WebhookSignatureVerifier;
import ar.edu.utn.frba.ddsi.logistica.services.RutaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    private final RutaService rutaService;
    private final RutaMapper rutaMapper;
    private final ObjectMapper objectMapper;
    private final WebhookSignatureVerifier signatureVerifier;

    public RutaController(RutaService rutaService, RutaMapper rutaMapper, ObjectMapper objectMapper, WebhookSignatureVerifier signatureVerifier) {
        this.rutaService = rutaService;
        this.rutaMapper = rutaMapper;
        this.objectMapper = objectMapper;
        this.signatureVerifier = signatureVerifier;
    }

    @GetMapping
    public List<RutaResponseDTO> listar() {
        return rutaService.listar().stream()
                .map(rutaMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public RutaResponseDTO buscarPorId(@PathVariable Long id) {
        return rutaMapper.toResponseDTO(rutaService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RutaResponseDTO crear(@Valid @RequestBody RutaRequestDTO dto) {
        return rutaMapper.toResponseDTO(rutaService.crear(rutaMapper.toEntity(dto)));
    }

    @PostMapping("/callback")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> recibirCallBack(@RequestBody String rawBody, @RequestHeader("X-Signature") String signature) {
        // por ahora loguear para confirmar que llega
        //System.out.println("Callback recibido: " + callback.getEventType());
        //System.out.println("Routes: " + callback.getData().getRoutes().size());
        // TODO: procesar y persistir las rutas generadas;

        if(!signatureVerifier.verify(rawBody, signature)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        RoutingCallbackDTO callback;
        try{
            callback = objectMapper.readValue(rawBody, RoutingCallbackDTO.class);
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }

        rutaService.procesarCallback(callback);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public RutaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody RutaUpdateRequestDTO dto) {
        return rutaMapper.toResponseDTO(rutaService.actualizar(
                id,
                rutaMapper.toChofer(dto.getChofer()),
                rutaMapper.toCamion(dto.getCamion()),
                dto.getFechaReparto()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        rutaService.eliminar(id);
    }

    @PutMapping("/{id}/estado")
    public RutaResponseDTO cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambioEstadoRutaRequestDTO dto) {
        return rutaMapper.toResponseDTO(
                rutaService.cambiarEstado(id, dto.getAccion().name(), dto.getJustificacion()));
    }

    @GetMapping("/{id}/auditoria")
    public List<AuditoriaTransicionDTO> obtenerHistorial(@PathVariable Long id) {
        return rutaService.obtenerHistorial(id).stream()
                .map(rutaMapper::toAuditoriaDTO)
                .toList();
    }
}
