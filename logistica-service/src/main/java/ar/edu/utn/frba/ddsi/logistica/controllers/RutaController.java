package ar.edu.utn.frba.ddsi.logistica.controllers;

import ar.edu.utn.frba.ddsi.logistica.dto.*;
import ar.edu.utn.frba.ddsi.logistica.dto.mappers.RutaMapper;
import ar.edu.utn.frba.ddsi.logistica.services.RutaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    private final RutaService rutaService;
    private final RutaMapper rutaMapper;

    public RutaController(RutaService rutaService, RutaMapper rutaMapper) {
        this.rutaService = rutaService;
        this.rutaMapper = rutaMapper;
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
