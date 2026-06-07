package ar.edu.utn.frba.ddsi.donaciones.controllers;

import ar.edu.utn.frba.ddsi.donaciones.dto.EntidadBeneficiariaRequestDTO;
import ar.edu.utn.frba.ddsi.donaciones.dto.EntidadBeneficiariaResponseDTO;
import ar.edu.utn.frba.ddsi.donaciones.dto.NecesidadRequestDTO;
import ar.edu.utn.frba.ddsi.donaciones.dto.NecesidadResponseDTO;
import ar.edu.utn.frba.ddsi.donaciones.dto.mappers.EntidadBeneficiariaMapper;
import ar.edu.utn.frba.ddsi.donaciones.services.EntidadBeneficiariaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entidades-beneficiarias")
public class EntidadBeneficiariaController {

    private final EntidadBeneficiariaService service;
    private final EntidadBeneficiariaMapper mapper;

    public EntidadBeneficiariaController(EntidadBeneficiariaService service, EntidadBeneficiariaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<EntidadBeneficiariaResponseDTO> listar() {
        return service.listar().stream().map(mapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public EntidadBeneficiariaResponseDTO buscarPorId(@PathVariable Long id) {
        return mapper.toResponseDTO(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntidadBeneficiariaResponseDTO crear(@Valid @RequestBody EntidadBeneficiariaRequestDTO dto) {
        return mapper.toResponseDTO(service.crear(mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public EntidadBeneficiariaResponseDTO actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody EntidadBeneficiariaRequestDTO dto) {
        return mapper.toResponseDTO(service.actualizar(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/{id}/necesidades")
    public List<NecesidadResponseDTO> listarNecesidades(@PathVariable Long id) {
        return service.listarNecesidades(id).stream()
                .map(mapper::necesidadToResponseDTO)
                .toList();
    }

    @PostMapping("/{id}/necesidades")
    @ResponseStatus(HttpStatus.CREATED)
    public EntidadBeneficiariaResponseDTO agregarNecesidad(@PathVariable Long id,
                                                           @Valid @RequestBody NecesidadRequestDTO dto) {
        return mapper.toResponseDTO(service.agregarNecesidad(id, mapper.necesidadToEntity(dto)));
    }

    @PutMapping("/{id}/necesidades/{indice}")
    public EntidadBeneficiariaResponseDTO actualizarNecesidad(@PathVariable Long id,
                                                              @PathVariable int indice,
                                                              @Valid @RequestBody NecesidadRequestDTO dto) {
        return mapper.toResponseDTO(service.actualizarNecesidad(id, indice, mapper.necesidadToEntity(dto)));
    }

    @DeleteMapping("/{id}/necesidades/{indice}")
    public EntidadBeneficiariaResponseDTO eliminarNecesidad(@PathVariable Long id,
                                                            @PathVariable int indice) {
        return mapper.toResponseDTO(service.eliminarNecesidad(id, indice));
    }
}
