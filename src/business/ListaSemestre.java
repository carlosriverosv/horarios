/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Planestudio;
import dataDB.Semestre;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JList;

/**
 *
 * @author Carlos
 */
public class ListaSemestre extends Lista implements Observer {

    public static ArrayList<ListaSemestre> todasListasSemestre = new ArrayList<>();
    private EntityManager entityM;
    private JList listaPlan;

    public ListaSemestre() {
        super();
        entityM = Manejador.getManejador().gentEntityManager();
        todasListasSemestre.add(this);
    }

    @Override
    public void actualizarDatos(Observable obs) {
        model.removeAllElements();
        listaPlan = (JList) obs;
        completarLista();
        notifyObservers();
    }

    public void completarLista() {
        List<Semestre> semestres = null;
        if (listaPlan.getSelectedValue() != null) {
            if (!listaPlan.getSelectedValue().toString().equalsIgnoreCase(ListaPlanEstudios.NINGUN_PLAN_ESTUDIOS)) {
                Planestudio planEstudio = (Planestudio) listaPlan.getSelectedValue();
                //asigEnPlan = entityM.createQuery("SELECT DISTINCT a.semestre FROM Asignaturaenplanestudio a WHERE a.planestudio.idPlan=:idPlanEstudio ORDER BY a.semestre ASC").setParameter("idPlanEstudio", planEstudio.getIdPlan()).getResultList();
                semestres = entityM.createQuery("SELECT DISTINCT a.semestre FROM Asignaturaenplanestudio a WHERE a.planestudio.idPlan=:idPlanEstudio AND a.oculto =:oculto ORDER BY a.semestre ASC").setParameter("idPlanEstudio", planEstudio.getIdPlan()).setParameter("oculto", false).getResultList();
            }
            if (semestres != null) {
                for (Semestre semestre : semestres) {
                    model.addElement(semestre);
                }
            } else {
                model.addElement(ListaPlanEstudios.NINGUN_PLAN_ESTUDIOS);
            }
        }
    }

    public static void actualizar() {
        for (ListaSemestre lista : todasListasSemestre) {
            int seleccionado = lista.getSelectedIndex();
            lista.model.removeAllElements();
            lista.completarLista();
            lista.setSelectedIndex(seleccionado);
            lista.notifyObservers();
        }
    }

}
