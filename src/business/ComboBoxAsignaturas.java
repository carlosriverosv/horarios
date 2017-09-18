/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Asignatura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class ComboBoxAsignaturas extends JComboBox {

    private DefaultComboBoxModel model;
    public static ArrayList<ComboBoxAsignaturas> todosComboBoxAsignaturas = new ArrayList<>();

    public ComboBoxAsignaturas() {
        model = new DefaultComboBoxModel();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Asignatura> listaAsignaturas = entityM.createNamedQuery("Asignatura.findByActivo").setParameter("activo", true).getResultList();
        List<Asignatura> listaAsignaturas = entityM.createNamedQuery("Asignatura.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
        for (Asignatura asignatura : listaAsignaturas) {
            model.addElement(asignatura);
        }
        this.setModel(model);
        todosComboBoxAsignaturas.add(this);
    }

    public static void actualizar() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Asignatura> listaAsignaturas = entityM.createNamedQuery("Asignatura.findByActivo").setParameter("activo", true).getResultList();
        List<Asignatura> listaAsignaturas = entityM.createNamedQuery("Asignatura.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
        for (ComboBoxAsignaturas combo : todosComboBoxAsignaturas) {
            combo.model.removeAllElements();
            for (Asignatura asignatura : listaAsignaturas) {
                combo.model.addElement(asignatura);
            }
        }

    }
}
