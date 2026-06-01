package ar.edu.utn.frba.ddsi.donaciones.controllers;

import ar.edu.utn.frba.ddsi.donaciones.dto.DonanteRequestDTO;
import ar.edu.utn.frba.ddsi.donaciones.dto.DonanteResponseDTO;
import ar.edu.utn.frba.ddsi.donaciones.dto.mappers.DonanteMapper;
import ar.edu.utn.frba.ddsi.donaciones.services.DonanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donantes")
public class DonanteController {

    private final DonanteService donanteService;
    private final DonanteMapper donanteMapper;

    public DonanteController(DonanteService donanteService, DonanteMapper donanteMapper) {
        this.donanteService = donanteService;
        this.donanteMapper = donanteMapper;
    }

    @GetMapping
    public List<DonanteResponseDTO> listar() {
        return donanteService.listar().stream()
                .map(donanteMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public DonanteResponseDTO buscarPorId(@PathVariable Long id) {
        return donanteMapper.toResponseDTO(donanteService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonanteResponseDTO crear(@Valid @RequestBody DonanteRequestDTO dto) {
        return donanteMapper.toResponseDTO(donanteService.crear(donanteMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public DonanteResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody DonanteRequestDTO dto) {
        return donanteMapper.toResponseDTO(donanteService.actualizar(id, donanteMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        donanteService.eliminar(id);
    }
}