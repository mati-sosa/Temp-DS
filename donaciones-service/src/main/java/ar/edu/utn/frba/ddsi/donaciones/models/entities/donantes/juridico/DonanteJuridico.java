package ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.juridico;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.Rubro;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;

import java.util.List;
import java.util.ArrayList;

public class DonanteJuridico extends Donante {
    private TipoPersonaJuridica tipo;
    private List<Representante> representantes;
    private Rubro rubro;

    public DonanteJuridico(String nombre,
            List<MedioDeContacto> mediosDeContacto,
            TipoPersonaJuridica tipo,
            List<Representante> representantes,
            Rubro rubro) {
            super(nombre, mediosDeContacto);
        
            if (representantes == null || representantes.isEmpty()) {
                throw new IllegalArgumentException("¡El donante jurídico debe tener al menos un representante!");
            }

            this.tipo = tipo;
            this.representantes = new ArrayList<>(representantes);
            this.rubro = rubro;
        }

    public void agregarRepresentante(Representante nuevoRepresentante) {
        if (this.representantes.contains(nuevoRepresentante)) {
            throw new IllegalArgumentException("¡El representante ya está en la lista!");
        }

        this.representantes.add(nuevoRepresentante);
    }
    public void eliminarRepresentante(Representante representanteEliminado) {
        if (!this.representantes.contains(representanteEliminado)) {
            throw new IllegalArgumentException("¡El representante no está en la lista!");
        }
        if (this.representantes.size() == 1) {
            // No me gusta que tire la misma excepción que arriba...
            throw new IllegalArgumentException("¡El donante jurídico debe tener al menos un representante!");
        }

        this.representantes.remove(representanteEliminado);
    }
}

        