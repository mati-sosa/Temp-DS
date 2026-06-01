package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.models.repositories.DonanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonanteService {

    private final DonanteRepository donanteRepository;

    public DonanteService(DonanteRepository donanteRepository) {
        this.donanteRepository = donanteRepository;
    }

    public Donante crear(Donante donante) {
        return donanteRepository.guardar(donante);
    }

    public List<Donante> listar() {
        return donanteRepository.buscarTodos();
    }

    public Donante buscarPorId(Long id) {
        return donanteRepository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Donante no encontrado: " + id));
    }

    public Donante actualizar(Long id, Donante donante) {
        if (!donanteRepository.existePorId(id))
            throw new NoSuchElementException("Donante no encontrado: " + id);
        donante.setId(id);
        return donanteRepository.guardar(donante);
    }

    public void eliminar(Long id) {
        if (!donanteRepository.existePorId(id))
            throw new NoSuchElementException("Donante no encontrado: " + id);
        donanteRepository.eliminar(id);
    }
}