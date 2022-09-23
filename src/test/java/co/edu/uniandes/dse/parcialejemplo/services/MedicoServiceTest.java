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

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest 
{
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<MedicoEntity> medicosList = new ArrayList<>();

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
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
    }

    /**
	 * Inserta los datos iniciales para realizar las pruebas.
	 */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(medicoEntity);
			medicosList.add(medicoEntity);
		}
    }

    /**
	 * Prueba para crear un medico.
	 */
    @Test
    void testCreateMedico() throws IllegalOperationException
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        MedicoEntity result = medicoService.createMedico(newEntity);
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getApellido(), entity.getApellido());
        assertEquals(newEntity.getRegistroMedico(), entity.getRegistroMedico());
    }

    /**
	 * Prueba para crear un medico con un registro medico invalido.
	 */
    @Test
    void testCreateMedicoWithInvalidRegistroMedico() throws IllegalOperationException
    {
        assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setRegistroMedico("ABCDE");
			medicoService.createMedico(newEntity);
		});
    }

}
