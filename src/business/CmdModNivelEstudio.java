/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearNivelEstudio;
import GUI.VentNivelesEstudio;
import dataDB.Nivelestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModNivelEstudio implements CommandInterface {

    private VentNivelesEstudio ventana;

    public CmdModNivelEstudio(VentNivelesEstudio ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        Nivelestudio nivelEstudio = ventana.getNivelEstudio();
        if (nivelEstudio != null) {
            VentCrearNivelEstudio ventanaCrearNivelEs = new VentCrearNivelEstudio();
            ventanaCrearNivelEs.setNivelEstudio(nivelEstudio);
            ventana.cerrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún nivel de estudio para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
