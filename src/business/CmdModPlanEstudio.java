/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearPlanEstudio;
import GUI.VentModPlanEstudio;
import dataDB.Planestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModPlanEstudio implements CommandInterface {

    private VentModPlanEstudio ventana;
    private ListaPlanEstudios lista;

    public CmdModPlanEstudio(VentModPlanEstudio ventana) {
        this.ventana = ventana;
    }

    public CmdModPlanEstudio(ListaPlanEstudios lista) {
        this.lista = lista;
    }

    @Override
    public void processEvent() {
        Planestudio plan = null;
        try {
            if (ventana != null) {
                plan = ventana.getPlanEstudio();
                ventana.dispose();
            } else {
                try {
                    plan = (Planestudio) lista.getSelectedValue();
                } catch (ClassCastException ex) {
                    plan = null;
                }
            }
            new VentCrearPlanEstudio(plan);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún plan de estudio para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
