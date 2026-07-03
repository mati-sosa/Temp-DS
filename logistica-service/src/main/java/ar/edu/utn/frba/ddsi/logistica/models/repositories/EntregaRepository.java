package ar.edu.utn.frba.ddsi.logistica.models.repositories;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EntregaRepository {

    private final Map<Long, Entrega> entregas = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public Entrega guardar(Entrega entrega) {
        if (entrega.getId() == null) entrega.setId(contador.getAndIncrement());
        entregas.put(entrega.getId(), entrega);
        return entrega;
    }

    public Optional<Entrega> buscarPorId(Long id) {
        return Optional.ofNullable(entregas.get(id));
    }

    public List<Entrega> buscarTodas() {
        return new ArrayList<>(entregas.values());
    }

    public boolean existePorId(Long id) {
        return entregas.containsKey(id);
    }

    public void eliminar(Long id) {
        entregas.remove(id);
    }
}
