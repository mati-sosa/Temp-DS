package ar.edu.utn.frba.ddsi.donaciones.models.repositories;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DonacionRepository {

    private final Map<Long, Donacion> donaciones = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public Donacion guardar(Donacion donacion) {
        if (donacion.getId() == null) donacion.setId(contador.getAndIncrement());
        donaciones.put(donacion.getId(), donacion);
        return donacion;
    }

    public Optional<Donacion> buscarPorId(Long id) {
        return Optional.ofNullable(donaciones.get(id));
    }

    public List<Donacion> buscarTodas() {
        return new ArrayList<>(donaciones.values());
    }

    public boolean existePorId(Long id) {
        return donaciones.containsKey(id);
    }

    public void eliminar(Long id) {
        donaciones.remove(id);
    }
}