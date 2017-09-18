/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentModPlanEstudio;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignaturaenplanestudio;
import dataDB.Planestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdEliminarPlanEstudio implements CommandInterface {

    private VentModPlanEstudio ventana;
    private boolean ocultar;
    private ListaPlanEstudios lista;
    private JButton boton;
    private JMenuItem jMenuItem;
    private Planestudio planEstudio;

    public CmdEliminarPlanEstudio(VentModPlanEstudio ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    public CmdEliminarPlanEstudio(ListaPlanEstudios lista, JMenuItem jMenuItem) {
        this.lista = lista;
        this.jMenuItem = jMenuItem;
    }

    @Override
    public void processEvent() {
        if (ventana != null) {
            planEstudio = ventana.getPlanEstudio();
            ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        } else {
            try {
                planEstudio = (Planestudio) lista.getSelectedValue();
                ocultar = jMenuItem.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
            } catch (ClassCastException ex) {
                ocultar = true;
                planEstudio = null;
            }
        }
        if (planEstudio != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Planestudio plan = (Planestudio) entityM.createNamedQuery("Planestudio.findByCodigoPlan").setParameter("codigoPlan", planEstudio.getCodigoPlan()).getSingleResult();
            //List<Asignaturaenplanestudio> asigEnPlan = entityM.createNamedQuery("Asignaturaenplanestudio.findByIdPlanEstudio").setParameter("idPlanEstudio", plan.getIdPlan()).getResultList();
            Object info;
            if (ocultar) {
                info = "¿Está seguro que desea ocultar el plan de estudio: "
                        + plan.getCodigoPlan() + " - " + plan.getNombrePlan();
            } else {
                info = "¿Está seguro que desea desocultar este plan de estudio?";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                entityM.getTransaction().begin();
                List<Asignaturaenplanestudio> asigEnPlan = entityM.createNamedQuery("Asignaturaenplanestudio.findByIdPlanEstudio").setParameter("idPlanEstudio", plan.getIdPlan()).setParameter("oculto", !ocultar).getResultList();
                for (Asignaturaenplanestudio asigPlan : asigEnPlan) {
                    //List<Asignaturaenplanestudio> listaAsigEnPlan = asigPlan.getAsignatura().getAsignaturaenplanestudioList();
                    //listaAsigEnPlan.remove(asigPlan);
                    asigPlan.setOculto(ocultar);
                    //asigPlan.getAsignatura().setAsignaturaenplanestudioList(listaAsigEnPlan);
                    //entityM.remove(asigPlan);
                }
                plan.setOculto(ocultar);
                //entityM.remove(plan);
                entityM.getTransaction().commit();
                if (ventana != null) {
                    ventana.removerPlan();
                }
                ComboBoxPlanEstudios.actualizar();
                ListaPlanEstudios.actualizar();
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún plan de estudio para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void processEvent(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
