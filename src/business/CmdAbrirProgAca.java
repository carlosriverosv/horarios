/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentAbrirProgAnterior;
import GUI.VentPrincipal;
import GUI.VentProgAca;
import dataDB.Progacademica;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdAbrirProgAca implements CommandInterface {

    private final VentAbrirProgAnterior ventanaProgAnt;
    private final VentPrincipal ventanaPrincipal;
    
    /**
     *
     * @param ventanaPrincipal representa la ventana principal de la aplicación
     * @param ventanaProgAnt representa la ventana interna que contiene la programación académica
     */
    public CmdAbrirProgAca(VentPrincipal ventanaPrincipal, VentAbrirProgAnterior ventanaProgAnt) {
        this.ventanaProgAnt = ventanaProgAnt;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    /**
     *Este método se ejecuta cuando se oprime el botón Aceptar, en la ventana
     * de abrir programación académica. Si el objeto que se obtiene es nullo,
     * esto quiere decir que no se seleccionó ninguna programación académica
     * y por tanto se muestra una ventana de error. Si por el contrario se 
     * eligió alguna, se crea una nueva ventana de programación académica,
     * es decir, una ventana interna, a la que se le envía el número de 
     * programación académica y las características de modificable, editable
     * y eliminable.
     */

    @Override
    public void processEvent() {
        Progacademica progAca = (Progacademica) ventanaProgAnt.getProgAcademica();
        if (progAca == null) {
            JOptionPane.showMessageDialog(null, "No hay programaciones académicas para abrir.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ventanaPrincipal.setVentanaProgAca(new VentProgAca(progAca.getNumeroProg(), ventanaPrincipal.getEditable(), ventanaPrincipal.getModificable(), ventanaPrincipal.getEliminable()));
            ventanaProgAnt.cerrar();
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