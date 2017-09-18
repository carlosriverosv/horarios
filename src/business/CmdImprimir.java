/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Carlos
 */
public class CmdImprimir implements CommandInterface {

    private JTable tabla;
    private JFrame ventana;

    public CmdImprimir(JTable tabla, JFrame ventana) {
        this.tabla = tabla;
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        try {
            MessageFormat header = new MessageFormat(ventana.getTitle());
            boolean completado = tabla.print(JTable.PrintMode.FIT_WIDTH, header, null, true, null, true, null);
            if (completado) {
                JOptionPane.showMessageDialog(null, "Impresi�n completada", "Resultado de la impresi�n", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Impresi�n cancelada", "Resultado de la impresi�n", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null, "La impresi�n fall�: " + pe.getMessage(), "Resultado de la impresi�n", JOptionPane.ERROR_MESSAGE);
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
