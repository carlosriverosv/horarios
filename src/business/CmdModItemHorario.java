/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import generalities.Errores;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Carlos
 */
public class CmdModItemHorario implements CommandInterface {

    private VentProgAca ventana;
    private String accion;
    private JButton boton;

    public CmdModItemHorario(VentProgAca ventana, JButton boton, String accion) {
        this.ventana = ventana;
        this.accion = accion;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        if (accion.equalsIgnoreCase(Errores.MODIFICAR)) {
            ventana.habilitarCamposEdicion();
            //Cambia el boton "Crear" a "Modificar", para modificar el item de horario seleccionado
            ventana.cambiarBtnCrearAAceptarModificar(true);
            //Cambia el botón "Modificar" a "Cancelar", para salir de la modificación sin guardar cambios
            boton.setText("Cancelar");
            boton.setActionCommand("Cancelar");
            boton.setToolTipText("Cancelar la modificación");
            boton.setIcon(new ImageIcon(VentProgAca.class.getResource("/GUI/iconos/eliminar.png")));
            boton.setEnabled(true);
        } else {
            //Si se oprime el botón "Cancelar", vuelve a estar disponible la opción "Crear"
            ventana.cambiarBtnCrearAAceptarModificar(false);
            boton.setText("Modificar");
            boton.setActionCommand("Modificar");
            boton.setToolTipText("Modificar item de horario");
            boton.setIcon(new ImageIcon(VentProgAca.class.getResource("/GUI/iconos/modItemHorario.png")));
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
