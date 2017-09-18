/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import generalities.Utilidades;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class CmdExportar implements CommandInterface {

    protected JTable tabla;
    protected String nombreArchivo = "";
    protected VentProgAca ventanaProg;
    private JFrame ventana;

    public CmdExportar(JTable tabla, JFrame ventana) {
        this.tabla = tabla;
        this.ventana = ventana;
    }

    public CmdExportar(VentProgAca ventanaProg) {
        this.ventanaProg = ventanaProg;
    }

    @Override
    public void processEvent() {
        if (nombreArchivo.trim().isEmpty()) {
            nombreArchivo = ventana.getTitle();
        }
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".csv, .txt", "csv", "txt");
        String ubicacionArchivo = Utilidades.mostrarVentanaArchivo("Elija una ubicación y nombre para el archivo", JFileChooser.FILES_AND_DIRECTORIES, filtro, nombreArchivo);
        if (!ubicacionArchivo.trim().isEmpty()) {
            String rutaArchivo = ubicacionArchivo + ".csv";
            try {
                Utilidades.exportar(tabla, rutaArchivo);
                JOptionPane.showMessageDialog(null, "Exportación exitosa", "Resultado de exportación", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error en la exportación " + ex, "Resultado de exportación", JOptionPane.ERROR_MESSAGE);
            }
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
