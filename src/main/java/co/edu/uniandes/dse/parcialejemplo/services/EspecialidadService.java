package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository;

    /**
	 * Guardar una nueva especialidad
	 *
	 * @param EspecialidadEntity La entidad de tipo EspecialidadEntity de la nueva especialidad a persistir.
	 * @return La entidad luego de persistirla.
	 * @throws IllegalOperationException
	 */
    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity) throws IllegalOperationException
    {
        log.info("Inicia proceso de creacion de especialidad");

        if(especialidadEntity.getDescripcion() == null)
        {
            throw new IllegalOperationException("Debe exisiir una descripcion de la especialidad");
        }
        else if (especialidadEntity.getDescripcion().length() < 10)
        {
            throw new IllegalOperationException("La descripcion debe tener al menos 10 caracteres");
        }

        log.info("Termina la creacion de medico");
        return especialidadRepository.save(especialidadEntity);
    }

}