/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearTipoContrato;
import GUI.VentModTipoContrato;
import dataDB.Tipocontrato;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModTipoContrato implements CommandInterface {

    private VentModTipoContrato ventana;

    public CmdModTipoContrato(VentModTipoContrato ventana) {
        super();
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        try {
            Tipocontrato tipoContrato = ventana.getTipoContrato();
            VentCrearTipoContrato vent = new VentCrearTipoContrato();
            vent.setTipoContrato(tipoContrato);
            ventana.cerrar();
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún tipo de contrato para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
