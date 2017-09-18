/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentColoresHorarios;
import GUI.VentProgAca;
import generalities.Utilidades;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdElegirColoresHorarios implements CommandInterface {

    private VentColoresHorarios ventana;

    public CmdElegirColoresHorarios(VentColoresHorarios ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        if (ventana.getLblColorGr1().getBackground().equals(ventana.getLblColorGr2().getBackground()) || ventana.getLblColorGr1().getBackground().equals(ventana.getLblColorGr3().getBackground())
                || ventana.getLblColorGr1().getBackground().equals(ventana.getLblColorGr4().getBackground()) || ventana.getLblColorGr1().getBackground().equals(ventana.getLblColorGr5().getBackground())
                || ventana.getLblColorGr2().getBackground().equals(ventana.getLblColorGr3().getBackground()) || ventana.getLblColorGr2().getBackground().equals(ventana.getLblColorGr4().getBackground())
                || ventana.getLblColorGr2().getBackground().equals(ventana.getLblColorGr5().getBackground()) || ventana.getLblColorGr3().getBackground().equals(ventana.getLblColorGr4().getBackground())
                || ventana.getLblColorGr3().getBackground().equals(ventana.getLblColorGr5().getBackground()) || ventana.getLblColorGr4().getBackground().equals(ventana.getLblColorGr5().getBackground())) {
            JOptionPane.showMessageDialog(null, "Todos los colores elegidos deben ser distintos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Color[] colores = {ventana.getLblColorGr1().getBackground(), ventana.getLblColorGr2().getBackground(),
                ventana.getLblColorGr3().getBackground(), ventana.getLblColorGr4().getBackground(), ventana.getLblColorGr5().getBackground()};
            Utilidades.escribirArchivo(Horario.NOMB_ARCHIVO_COLORES, colores);
            ventana.dispose();
            VentProgAca.actualizarColoresHorarios();
        }
    }

    @Override
    public void processEvent(MouseEvent e) {
        Color nuevoColor = JColorChooser.showDialog(ventana, "Elija el color de la casilla del horario ", e.getComponent().getBackground());
        if (nuevoColor != null) {
            ((JLabel) (e.getComponent())).setBackground(nuevoColor);
        }
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
