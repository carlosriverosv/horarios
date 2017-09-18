/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentAsigEnPlanEstudio;
import data.Manejador;
import dataDB.Asignaturaenplanestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdEliminarAsigPlanEstudio implements CommandInterface {

    private VentAsigEnPlanEstudio ventana;

    public CmdEliminarAsigPlanEstudio(VentAsigEnPlanEstudio ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        int codigoAsig = ventana.getSeleccionado();
        if (codigoAsig != 0) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar esta asignatura del plan de estudios? ", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                entityM.getTransaction().begin();
                Asignaturaenplanestudio asigEnPlan = (Asignaturaenplanestudio) entityM.createQuery("SELECT a FROM Asignaturaenplanestudio a WHERE a.asignatura.codigoAsig=:codigoAsig AND a.planestudio.idPlan=:idPlan").setParameter("codigoAsig", codigoAsig).setParameter("idPlan", ventana.getPlanestudio().getIdPlan()).getSingleResult();
                List<Asignaturaenplanestudio> listaAsigEnPlan = asigEnPlan.getAsignatura().getAsignaturaenplanestudioList();
                listaAsigEnPlan.remove(asigEnPlan);
                asigEnPlan.getAsignatura().setAsignaturaenplanestudioList(listaAsigEnPlan);
                entityM.remove(asigEnPlan);
                entityM.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Asignatura eliminada del plan de estudios.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                ListaSemestre.actualizar();
                ventana.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna asignatura dentro de este plan de estudios para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
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
