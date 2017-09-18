/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearPlanEstudio;
import generalities.Errores;
import data.Manejador;
import dataDB.Planestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearPlanEstudio implements Observable, CommandInterface {

    private VentCrearPlanEstudio ventana;
    private ArrayList<Observer> observadores = new ArrayList<>();
    private JButton boton;
    

    public CmdCrearPlanEstudio(VentCrearPlanEstudio ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {

        String nombrePlan = ventana.getNombrePlan();
        if (!nombrePlan.trim().isEmpty()) {
            try {
                String codigoPlan = ventana.getCodigoPlan();
                int annoPlan = Integer.parseInt(ventana.getAnnoPlan());
                EntityManager entityM = Manejador.getManejador().gentEntityManager();
                entityM.getTransaction().begin();
                if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                    entityM.persist(new Planestudio(null, codigoPlan, nombrePlan, annoPlan));
                    entityM.getTransaction().commit();
                    ventana.confirmacionPlanAgregado();
                    notifyObservers();
                } else {
                    Planestudio planEstudio = ventana.getPlanEstudio();
                    planEstudio.setCodigoPlan(codigoPlan);
                    planEstudio.setNombrePlan(nombrePlan);
                    planEstudio.setAnno(annoPlan);
                    entityM.getTransaction().commit();
                    ventana.confirmarPlanModificado();
                }
                ventana.cerrar();
                ComboBoxPlanEstudios.actualizar();
                ListaPlanEstudios.actualizar();
            } catch (NumberFormatException ex) {
                ventana.errorNoNumeros();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Introduzca un nombre para el plan de estudios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            observer.actualizarDatos(null);
        }
    }

    @Override
    public void register(Observer obs) {
        observadores.add(obs);
    }

    @Override
    public void unRegister(Observer obs) {
        observadores.remove(obs);
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
