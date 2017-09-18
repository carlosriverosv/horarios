/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Asignatura;
import dataDB.Asignaturaenplanestudio;
import dataDB.Planestudio;
import dataDB.Semestre;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JList;

/**
 *
 * @author Carlos
 */
public class ListaAsignaturas extends Lista implements Observer {

    private ListaPlanEstudios listaPlanEstudios;

    public ListaAsignaturas(ListaPlanEstudios listaPlanEstudios) {
        super();
        this.listaPlanEstudios = listaPlanEstudios;
        crearPopUp();
        menuItemOcultar.addActionListener(new ButtonHandler(new CmdEliminarAsignatura(this, menuItemOcultar)));
        menuItemModificar.addActionListener(new ButtonHandler(new CmdModAsignatura(this)));
    }

    @Override
    public void actualizarDatos(Observable obs) {
        model.removeAllElements();
        JList listaSemestre = (JList) obs;
        Semestre semestre = null;
        List<Asignaturaenplanestudio> asignaturasEnPlan = null;
        List<Asignatura> asignaturas = null;
        if (listaSemestre.getSelectedValue() != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            if (listaSemestre.getSelectedValue().toString().equalsIgnoreCase(ListaPlanEstudios.NINGUN_PLAN_ESTUDIOS)) {
                //asignaturas = entityM.createQuery("SELECT a FROM Asignatura a WHERE a.activo =:activo AND (NOT EXISTS (SELECT asigEnPlan FROM Asignaturaenplanestudio asigEnPlan WHERE asigEnPlan.asignatura.idAsignatura=a.idAsignatura))").setParameter("activo", true).getResultList();
                asignaturas = entityM.createQuery("SELECT a FROM Asignatura a WHERE a.activo =:activo AND a.oculto=:oculto AND (NOT EXISTS (SELECT asigEnPlan FROM Asignaturaenplanestudio asigEnPlan WHERE asigEnPlan.asignatura.idAsignatura=a.idAsignatura AND asigEnPlan.oculto =:oculto)) ORDER BY a.nombreAsig ASC").setParameter("activo", true).setParameter("oculto", false).getResultList();
            } else {
                semestre = (Semestre) listaSemestre.getSelectedValue();
            }
            if (semestre != null) {
                Planestudio plan = (Planestudio) (listaPlanEstudios.getSelectedValue());
                //asignaturasEnPlan = entityM.createQuery("SELECT a FROM Asignaturaenplanestudio a WHERE a.semestre =:semestre AND a.planestudio.idPlan=:idPlanEstudio AND a.asignatura.activo =:activo").setParameter("semestre", semestre).setParameter("idPlanEstudio", plan.getIdPlan()).setParameter("activo", true).getResultList();
                asignaturasEnPlan = entityM.createQuery("SELECT a FROM Asignaturaenplanestudio a WHERE a.semestre =:semestre AND a.planestudio.idPlan=:idPlanEstudio AND a.asignatura.activo =:activo AND a.asignatura.oculto =:oculto ORDER BY a.asignatura.nombreAsig ASC").setParameter("semestre", semestre).setParameter("idPlanEstudio", plan.getIdPlan()).setParameter("activo", true).setParameter("oculto", false).getResultList();
                for (Asignaturaenplanestudio asignaturaEnPlan : asignaturasEnPlan) {
                    model.addElement(asignaturaEnPlan.getAsignatura());
                }
            } else {
                for (Asignatura asignatura : asignaturas) {
                    model.addElement(asignatura);
                }
            }

        }
        notifyObservers();
    }

    public int getCodigoAsigSeleccionada() {
        try {
            return ((Asignatura) this.getSelectedValue()).getCodigoAsig();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

}
