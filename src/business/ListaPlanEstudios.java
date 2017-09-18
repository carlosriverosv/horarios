/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Planestudio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Carlos
 */
public class ListaPlanEstudios extends Lista {

    public static final String NINGUN_PLAN_ESTUDIOS = "NINGUNO";
    public static ArrayList<ListaPlanEstudios> todasListasPlan = new ArrayList<>();
    private EntityManager entityM;

    public ListaPlanEstudios() {
        super();
        entityM = Manejador.getManejador().gentEntityManager();
        completarLista();
        crearPopUp();
        menuItemOcultar.addActionListener(new ButtonHandler(new CmdEliminarPlanEstudio(this, menuItemOcultar)));
        menuItemModificar.addActionListener(new ButtonHandler(new CmdModPlanEstudio(this)));
        todasListasPlan.add(this);
    }

    private void completarLista() {
        //List<Planestudio> listaPlanEstudios = entityM.createNamedQuery("Planestudio.findAll").getResultList();
        List<Planestudio> listaPlanEstudios = entityM.createNamedQuery("Planestudio.findAll").setParameter("oculto", false).getResultList();
        for (Planestudio planestudio : listaPlanEstudios) {
            model.addElement(planestudio);
        }
        //List<Asignatura> asignaturas = entityM.createNamedQuery("Asignatura.findAll").getResultList();
        List<Asignatura> asignaturas = entityM.createNamedQuery("Asignatura.findAll").setParameter("oculto", false).getResultList();
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getPlanesEstudioVinculados().trim().isEmpty()) {
                model.addElement(NINGUN_PLAN_ESTUDIOS);
                break;
            }
        }
    }

    public static void actualizar() {
        for (ListaPlanEstudios lista : todasListasPlan) {
            int seleccionado = lista.getSelectedIndex();
            lista.model.removeAllElements();
            lista.completarLista();
            lista.setSelectedIndex(seleccionado);
            lista.notifyObservers();
        }
    }

}
