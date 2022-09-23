package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

    /**
	 * Guardar un nuevo medico
	 *
	 * @param MedicoEntity La entidad de tipo MedicoEntity del nuevo medico a persistir.
	 * @return La entidad luego de persistirla.
	 * @throws IllegalOperationException
	 */
    @Transactional
    public MedicoEntity createMedico(MedicoEntity medicoEntity) throws IllegalOperationException
    {
        log.info("Inicia proceso de creacion de medico");

        if(medicoEntity.getRegistroMedico() == null)
        {
            throw new IllegalOperationException("El registro medico es obligatorio");
        }
        else if (!medicoEntity.getRegistroMedico().startsWith("RM"))
        {
            throw new IllegalOperationException("El registro medico debe iniciar con RM");
        }

        log.info("Termina la creacion de medico");
        return medicoRepository.save(medicoEntity);
    }

}
