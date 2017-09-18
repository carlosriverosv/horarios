/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import static business.Horario.HORA_INICIAL;
import static business.Horario.horas;
import data.Manejador;
import dataDB.Asignaturaenplanestudio;
import dataDB.Grupo;
import dataDB.Itemhorario;
import dataDB.Planestudio;
import dataDB.Progacademica;
import dataDB.Semestre;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Carlos
 */
public class HorarioSemestre extends Horario {
    
    private ComboBoxPlanEstudios comboPlanEstudios;
    
    public HorarioSemestre(int idProg, ComboBoxPlanEstudios comboPlanEstudios) {
        super(idProg);
        this.comboPlanEstudios = comboPlanEstudios;
        cantidadItemsHorario = new int[horas.length][model.getColumnCount()];
    }

    @Override
    public void actualizarDatos(Observable subject) {
        borrarContenido();
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        ComboBoxNumSemestre comboBoxNumSemestre = (ComboBoxNumSemestre) subject;
        if (!(comboBoxNumSemestre.getSelectedItem().equals("") || comboBoxNumSemestre.getSelectedItem().equals(" ") )) {
            Semestre semestre = (Semestre) comboBoxNumSemestre.getSelectedItem();
            Progacademica progAca = (Progacademica) entity.find(Progacademica.class, getIdProg());
            List<Asignaturaenplanestudio> asignaturasEnPlan = entity.createQuery("SELECT a FROM Asignaturaenplanestudio a WHERE a.planestudio.idPlan =:idPlanEstudio AND a.semestre=:semestre").setParameter("idPlanEstudio", ((Planestudio)comboPlanEstudios.getSelectedItem()).getIdPlan()).setParameter("semestre", semestre).getResultList();
            for (Asignaturaenplanestudio asignaturaEnPlan : asignaturasEnPlan) { 
                List<Grupo> grupos = entity.createQuery("SELECT g FROM Grupo g WHERE g.asignatura = :idAsignatura AND g.progacademica = :idProg").setParameter("idAsignatura", asignaturaEnPlan.getAsignatura()).setParameter("idProg", progAca).getResultList();
                for (Grupo grupo : grupos) {
                    List<Itemhorario> itemsHorario = grupo.getItemhorarioList();
                    try {
                        for (Itemhorario itemhorario : itemsHorario) {
                            setDia(getNumeroDia(itemhorario.getDia1().getDia()));
                            setHora(itemhorario.getHorainicio().getHoraInicio());
                            int numeroGrupo = itemhorario.getGrupo().getGrupoPK().getNumeroGrupo();
                            String docente = itemhorario.getIdDocente().getNombreDocente();
                            String nomAsignatura = itemhorario.getGrupo().getAsignatura().getNombreAsig();
                            String edificio = String.valueOf(itemhorario.getIdSalon().getIdEdificio().getNumeroEd());
                            String salon = String.valueOf(itemhorario.getIdSalon().getNumeroSalon());
                            String informacion = getModelo().getValueAt(getHora() - HORA_INICIAL, getDia()) + SEPARADOR + nomAsignatura + " - Grupo " + numeroGrupo + " Ed: " + edificio + " - " + salon + " " + docente + SEPARADOR;
                            getModelo().setValueAt(informacion, getHora() - HORA_INICIAL, getDia());
                            cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] = cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] + 1;
                        }
                    } catch (NullPointerException ex) {
                    }
                }
            }
        }
    }
}