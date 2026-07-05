package ar.edu.utn.frba.ddsi.logistica.models.repositories;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RutaRepository {

    private final Map<Long, Ruta> rutas = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public Ruta guardar(Ruta ruta) {
        if (ruta.getId() == null) ruta.setId(contador.getAndIncrement());
        rutas.put(ruta.getId(), ruta);
        return ruta;
    }

    public Optional<Ruta> buscarPorId(Long id) {
        return Optional.ofNullable(rutas.get(id));
    }

    public List<Ruta> buscarTodas() {
        return new ArrayList<>(rutas.values());
    }

    public boolean existePorId(Long id) {
        return rutas.containsKey(id);
    }

    public void eliminar(Long id) {
        rutas.remove(id);
    }

    public Optional<Ruta> buscarPorRequestId(String requestId) {
        return rutas.values().stream()
                .filter(r -> requestId.equals(r.getRequestId()))
                .findFirst();
    }
}
