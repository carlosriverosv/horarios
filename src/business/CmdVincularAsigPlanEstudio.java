/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentVincularAsigPlanEstudio;
import generalities.Errores;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Asignaturaenplanestudio;
import dataDB.Planestudio;
import dataDB.Semestre;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdVincularAsigPlanEstudio implements CommandInterface {

    private VentVincularAsigPlanEstudio ventana;
    private String accion;

    public CmdVincularAsigPlanEstudio(VentVincularAsigPlanEstudio ventana, String accion) {
        super();
        this.ventana = ventana;
        this.accion = accion;
    }

    @Override
    public void processEvent() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        if (!accion.equalsIgnoreCase(Errores.MODIFICAR)) {
            try {
                Asignatura asignatura = ventana.getAsignatura();
                Planestudio planEstudio = ventana.getPlanEstudio();
                Semestre semestre = ventana.getSemestre();
                if (semestre == null) {
                    throw new NullPointerException();
                }
                Asignaturaenplanestudio asignaturaEnPlan = new Asignaturaenplanestudio(asignatura.getIdAsignatura(), planEstudio.getIdPlan());
                asignaturaEnPlan.setIdTipologia(ventana.getTipologia());
                asignaturaEnPlan.setSemestre(semestre);
                asignaturaEnPlan.setAsignatura(asignatura);
                asignaturaEnPlan.setPlanestudio(planEstudio);
                List<Asignaturaenplanestudio> listaAsigEnPlan = asignatura.getAsignaturaenplanestudioList();
                listaAsigEnPlan.add(asignaturaEnPlan);
                asignatura.setAsignaturaenplanestudioList(listaAsigEnPlan);
                entityM.getTransaction().begin();
                entityM.persist(asignaturaEnPlan);
                entityM.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Asignatura vinculada exitosamente al plan de estudios seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(null, "Seleccione un valor para la tipología y el plan de estudios.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Seleccione un semestre.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            entityM.getTransaction().begin();
            Asignaturaenplanestudio asignaturaEnPlan = ventana.getAsignaturaenplanestudio();
            asignaturaEnPlan.setIdTipologia(ventana.getTipologia());
            asignaturaEnPlan.setSemestre(ventana.getSemestre());
            entityM.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Vinculación de asignatura modificada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            ventana.dispose();
        }
        ListaSemestre.actualizar();
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
