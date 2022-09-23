package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest 
{
    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<MedicoEntity> medicosList = new ArrayList<>();
    private List<EspecialidadEntity> especialidadesList = new ArrayList<>();


    /**
	 * Configuración inicial de la prueba.
	 */
    @BeforeEach
    void setUp()
    {
        clearData();
        insertData();
    }

    /**
	 * Limpia las tablas implicadas en la prueba.
	 */
    private void clearData() 
    {
        entityManager.getEntityManager().createQuery("delete from EspecialidadMedicoEntity");
    }

    /**
	 * Inserta los datos iniciales para realizar las pruebas.
	 */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(especialidadEntity);
            entityManager.persist(medicoEntity);
			especialidadesList.add(especialidadEntity);
            medicosList.add(medicoEntity);
		}
    }

    /**
	 * Prueba para crear una especialidad.
     * @throws EntityNotFoundException
	 */
    @Test
    void testAddMedicoEspecialidad() throws IllegalOperationException, EntityNotFoundException
    {
        MedicoEntity medico = medicoService.createMedico(medicosList.get(0));
        EspecialidadEntity especialidad = especialidadService.createEspecialidad(especialidadesList.get(0));
        MedicoEntity medicoEspecialidad = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());
        assertNotNull(medicoEspecialidad);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, medico.getId());
        assertEquals(medicoEspecialidad.getEspecialidades().get(0), entity.getEspecialidades().get(0));
    }

    /**
	 * Prueba para crear una especialidad con una descripcion invalida.
	 */
    @Test
    void testCreateEspecialidadWithInvalidDescripcion() throws IllegalOperationException
    {
        assertThrows(IllegalOperationException.class, () -> {
			EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
			newEntity.setDescripcion("Ojos");;
			especialidadService.createEspecialidad(newEntity);
		});
    }

}
