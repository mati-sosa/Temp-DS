package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import ar.edu.utn.frba.ddsi.donaciones.models.repositories.EntidadBeneficiariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EntidadBeneficiariaService {

    private final EntidadBeneficiariaRepository repositorio;

    public EntidadBeneficiariaService(EntidadBeneficiariaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public EntidadBeneficiaria crear(EntidadBeneficiaria entidad) {
        return repositorio.guardar(entidad);
    }

    public List<EntidadBeneficiaria> listar() {
        return repositorio.buscarTodas();
    }

    public EntidadBeneficiaria buscarPorId(Long id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Entidad beneficiaria no encontrada: " + id));
    }

    public EntidadBeneficiaria actualizar(Long id, EntidadBeneficiaria nueva) {
        if (!repositorio.existePorId(id))
            throw new NoSuchElementException("Entidad beneficiaria no encontrada: " + id);
        nueva.setId(id);
        return repositorio.guardar(nueva);
    }

    public void eliminar(Long id) {
        if (!repositorio.existePorId(id))
            throw new NoSuchElementException("Entidad beneficiaria no encontrada: " + id);
        repositorio.eliminar(id);
    }

    public EntidadBeneficiaria agregarNecesidad(Long entidadId, Necesidad necesidad) {
        EntidadBeneficiaria entidad = buscarPorId(entidadId);
        entidad.registrarNecesidad(necesidad);
        return repositorio.guardar(entidad);
    }

    public EntidadBeneficiaria eliminarNecesidad(Long entidadId, int indice) {
        EntidadBeneficiaria entidad = buscarPorId(entidadId);
        List<Necesidad> necesidades = entidad.getNecesidades();
        if (indice < 0 || indice >= necesidades.size())
            throw new IllegalArgumentException("Índice de necesidad inválido: " + indice);
        necesidades.remove(indice);
        return repositorio.guardar(entidad);
    }

    public EntidadBeneficiaria actualizarNecesidad(Long entidadId, int indice, Necesidad nueva) {
        EntidadBeneficiaria entidad = buscarPorId(entidadId);
        entidad.actualizarNecesidad(indice, nueva);
        return repositorio.guardar(entidad);
    }

    public List<Necesidad> listarNecesidades(Long entidadId) {
        return buscarPorId(entidadId).getNecesidades();
    }
}
