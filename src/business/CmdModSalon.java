/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearSalon;
import GUI.VentSalones;
import data.Manejador;
import dataDB.Salon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModSalon implements CommandInterface {

    private VentSalones ventana;

    public CmdModSalon(VentSalones ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        Object[] numSalonEdificio = ventana.getSeleccionado();
        if (numSalonEdificio != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Salon salon = (Salon) entityM.createQuery("SELECT s FROM Salon s WHERE s.numeroSalon = '" + numSalonEdificio[0] + "' AND s.idEdificio.numeroEd = '" + numSalonEdificio[1] + "'").getSingleResult();
            new VentCrearSalon(salon);
            ventana.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún salón para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
