package ar.edu.utn.frba.ddsi.donaciones.models.repositories;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DonanteRepository {

    private final Map<Long, Donante> donantes = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public Donante guardar(Donante donante) {
        if (donante.getId() == null) donante.setId(contador.getAndIncrement());
        donantes.put(donante.getId(), donante);
        return donante;
    }

    public Optional<Donante> buscarPorId(Long id) {
        return Optional.ofNullable(donantes.get(id));
    }

    public List<Donante> buscarTodos() {
        return new ArrayList<>(donantes.values());
    }

    public boolean existePorId(Long id) {
        return donantes.containsKey(id);
    }

    public void eliminar(Long id) {
        donantes.remove(id);
    }
}