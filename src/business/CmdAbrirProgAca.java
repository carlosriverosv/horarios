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
     * @param ventanaPrincipal representa la ventana principal de la aplicaci�n
     * @param ventanaProgAnt representa la ventana interna que contiene la programaci�n acad�mica
     */
    public CmdAbrirProgAca(VentPrincipal ventanaPrincipal, VentAbrirProgAnterior ventanaProgAnt) {
        this.ventanaProgAnt = ventanaProgAnt;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    /**
     *Este m�todo se ejecuta cuando se oprime el bot�n Aceptar, en la ventana
     * de abrir programaci�n acad�mica. Si el objeto que se obtiene es nullo,
     * esto quiere decir que no se seleccion� ninguna programaci�n acad�mica
     * y por tanto se muestra una ventana de error. Si por el contrario se 
     * eligi� alguna, se crea una nueva ventana de programaci�n acad�mica,
     * es decir, una ventana interna, a la que se le env�a el n�mero de 
     * programaci�n acad�mica y las caracter�sticas de modificable, editable
     * y eliminable.
     */

    @Override
    public void processEvent() {
        Progacademica progAca = (Progacademica) ventanaProgAnt.getProgAcademica();
        if (progAca == null) {
            JOptionPane.showMessageDialog(null, "No hay programaciones acad�micas para abrir.", "Error", JOptionPane.ERROR_MESSAGE);
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