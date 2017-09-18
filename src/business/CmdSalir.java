/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentPrincipal;
import data.Manejador;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdSalir implements CommandInterface {

    @Override
    public void processEvent(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Confirma que desea salir de la aplicación?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/salir.png")));
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                Manejador.getManejador().gentEntityManager().close();
                Manejador.getManejador().getEntityManagerFactory().close();
            } catch (NullPointerException ex) {

            }
            System.exit(0);
        }
    }
}
