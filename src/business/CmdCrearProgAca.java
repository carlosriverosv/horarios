/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearProgAca;
import GUI.VentPrincipal;
import GUI.VentProgAca;
import data.Manejador;
import dataDB.Progacademica;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearProgAca implements CommandInterface {

    private VentPrincipal ventana;
    private VentCrearProgAca ventanaCrearProg;

    public CmdCrearProgAca(VentPrincipal ventana, VentCrearProgAca ventCrearProgAca) {
        this.ventana = ventana;
        this.ventanaCrearProg = ventCrearProgAca;
    }

    @Override
    public void processEvent() {
        String numProgAca = ventanaCrearProg.getProgAca();
        if (!numProgAca.trim().isEmpty()) {
            try {
                EntityManager entityM = Manejador.getManejador().gentEntityManager();
                entityM.getTransaction().begin();
                Progacademica progAca = new Progacademica(null, numProgAca);
                entityM.persist(progAca);
                entityM.getTransaction().commit();
                ventanaCrearProg.cerrar();
                VentProgAca ventanaProgAca = new VentProgAca(numProgAca, ventana.getEditable(), ventana.getModificable(), ventana.getEliminable());
                ventana.setVentanaProgAca(ventanaProgAca);
            } catch (RollbackException ex) {
                JOptionPane.showMessageDialog(null, "Ya existe una programación académica con ese nombre. ", "Error", JOptionPane.ERROR_MESSAGE);
                ventanaCrearProg.setProgAca("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La programación académica debe tener un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
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
