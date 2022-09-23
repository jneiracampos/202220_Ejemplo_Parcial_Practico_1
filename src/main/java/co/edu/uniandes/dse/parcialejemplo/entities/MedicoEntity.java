package co.edu.uniandes.dse.parcialejemplo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MedicoEntity extends BaseEntity 
{
    private String nombre;
    private String apellido;
    private String registroMedico;

    @PodamExclude
    @ManyToMany
    private List<EspecialidadEntity> especialidades = new ArrayList<>();
    
}
