/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentanaLogin;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Carlos
 */
public class CmdRecordarPassword implements CommandInterface {

    private VentanaLogin ventana;

    public CmdRecordarPassword(VentanaLogin ventana) {
        super();
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        ventana.mostrarRecordarPassword();
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
