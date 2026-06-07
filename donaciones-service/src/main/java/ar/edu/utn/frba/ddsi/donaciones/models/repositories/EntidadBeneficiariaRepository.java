package ar.edu.utn.frba.ddsi.donaciones.models.repositories;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EntidadBeneficiariaRepository {

    private final Map<Long, EntidadBeneficiaria> entidades = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public EntidadBeneficiaria guardar(EntidadBeneficiaria entidad) {
        if (entidad.getId() == null) entidad.setId(contador.getAndIncrement());
        entidades.put(entidad.getId(), entidad);
        return entidad;
    }

    public Optional<EntidadBeneficiaria> buscarPorId(Long id) {
        return Optional.ofNullable(entidades.get(id));
    }

    public List<EntidadBeneficiaria> buscarTodas() {
        return new ArrayList<>(entidades.values());
    }

    public boolean existePorId(Long id) {
        return entidades.containsKey(id);
    }

    public void eliminar(Long id) {
        entidades.remove(id);
    }
}
