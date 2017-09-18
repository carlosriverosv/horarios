/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearEdificio;
import GUI.VentEdificios;
import data.Manejador;
import dataDB.Edificio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModEdificio implements CommandInterface {

    private VentEdificios ventana;

    public CmdModEdificio(VentEdificios ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        String numeroEdificio = ventana.getSeleccionado();
        if (numeroEdificio != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Edificio edificio = (Edificio) entityM.createNamedQuery("Edificio.findByNumeroEd").setParameter("numeroEd", numeroEdificio).getSingleResult();
            new VentCrearEdificio(edificio);
            ventana.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún edificio para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
