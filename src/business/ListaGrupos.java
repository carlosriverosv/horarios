/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Asignatura;
import dataDB.Grupo;
import dataDB.Progacademica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JList;

/**
 *
 * @author Carlos
 */
public class ListaGrupos extends Lista implements Observer {

    private JList listaAsignaturas;
    private Asignatura asignatura;
    private String numProgAcademica;

    public ListaGrupos(String numProgAcademica) {
        super();
        this.numProgAcademica = numProgAcademica;
    }

    @Override
    public void actualizarDatos(Observable obs) {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        model.removeAllElements();
        listaAsignaturas = (JList) obs;
        asignatura = (Asignatura) listaAsignaturas.getSelectedValue();
        Progacademica progAca = (Progacademica) entityM.createNamedQuery("Progacademica.findByNumeroProg").setParameter("numeroProg", this.numProgAcademica).getSingleResult();
        List<Grupo> grupos = entityM.createQuery("SELECT g FROM Grupo g WHERE g.asignatura = :idAsignatura AND g.progacademica = :prog").setParameter("idAsignatura", asignatura).setParameter("prog", progAca).getResultList();
        for (Grupo grupo : grupos) {
            model.addElement(grupo.getGrupoPK());
        }
        notifyObservers();
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public String getNumProgAcademica() {
        return numProgAcademica;
    }

    public void actualizarDatos() {
        actualizarDatos((Observable) listaAsignaturas);
    }

    public String getInfo() {
        try {
            return this.getSelectedValue().toString();
        } catch (NullPointerException ex) {
            return "";
        }
    }

}
