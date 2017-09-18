/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearTipologia;
import GUI.VentTipologias;
import dataDB.Tipologia;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModTipologia implements CommandInterface {

    private VentTipologias ventana;

    public CmdModTipologia(VentTipologias ventana) {
        super();
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        Tipologia tip = ventana.getTipologia();
        if (tip != null) {
            VentCrearTipologia ventanaCrearTipologia = new VentCrearTipologia();
            ventanaCrearTipologia.setTipologia(tip);
            ventana.cerrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna tipología para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
