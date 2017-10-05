/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import generalities.Utilidades;
import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 *
 * Esta clase es la plantilla para importar cualquiera de los archivos
 * (docentes, asignaturas y salones) que se tienen en la aplicación. Los métdos
 * verificarArchivo e importar son definidos por cada clase concreta que hereda
 * de esta.
 */
public abstract class CmdImportarArchivo implements CommandInterface {
    
    protected File origen;
    protected static char SIMBOLO = ';';
    protected String nombreColumnas;
    protected JMenuItem jmenu;

    public CmdImportarArchivo(JMenuItem jmenu) {
        this.jmenu = jmenu;
    }

    @Override
    public void processEvent() {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".csv, .txt", "csv", "txt");
        String ubicacionArchivo = Utilidades.mostrarVentanaArchivo("Elija el archivo a importar", JFileChooser.FILES_AND_DIRECTORIES, filtro, null);
        if (!ubicacionArchivo.isEmpty()) {
            origen = new File(ubicacionArchivo);
            if (verificarArchivo()) {
                JOptionPane.showMessageDialog(null, "Por favor espere mientras se importa el archivo. Al finalizar se mostrará una ventana de confirmación.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                String errores = importar();
                if (errores.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Archivo importado exitósamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error: No todos los datos fueron importados." + '\n' + errores, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El formato de columnas del archivo no es el esperado. No fue posible importar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        jmenu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /*
     * En este método se verifica que la primera línea del archivo leído 
     * sea igual a lo que se espera, es decir, que los nombres de la columnas
     * sean las esperadas. Si no es así se retorna un valor de falso.
     */
    public boolean verificarArchivo(){
        try {
            String primeraLinea = Utilidades.leerLineaArchivo(origen).replace(" ", "");
            return primeraLinea.equalsIgnoreCase(nombreColumnas);
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
    }

    public abstract String importar();
}
