package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService 
{
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    /**
	 * Agregar la especialidad de un medico
	 *
	 * @param medicoID El id del medico
	 * @param especialidadID El id de la especialidad
	 * @return La entidad medico.
	 * @throws EntityNotFoundException 
	 */
	
	@Transactional
	public MedicoEntity addEspecialidad(Long medicoID, Long especialidadID) throws EntityNotFoundException 
    {
		log.info("Inicia el proceso de agregarle una especialidad al medico con id = {0}", medicoID);
		
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoID);
		if(medicoEntity.isEmpty())
			throw new EntityNotFoundException("El medico no existe");
		
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadID);
		if(especialidadEntity.isEmpty())
			throw new EntityNotFoundException("La especialidad no existe");
		
        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        especialidadEntity.get().getMedicos().add(medicoEntity.get());

		log.info("Termina el proceso de agregarle un negocio al vecindario con id = {0}", medicoID);
		return medicoEntity.get();
	}
}
