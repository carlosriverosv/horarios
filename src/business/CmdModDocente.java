/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearDocente;
import GUI.VentDocentes;
import data.Manejador;
import dataDB.Docente;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModDocente implements CommandInterface {

    private VentDocentes ventana;

    public CmdModDocente(VentDocentes ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        int documentoDocente = ventana.getSeleccionado();
        if (documentoDocente != 0) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Docente docente = (Docente) entityM.createNamedQuery("Docente.findByDocumentoDoc").setParameter("documentoDoc", documentoDocente).getSingleResult();
            new VentCrearDocente(docente);
            ventana.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún Docente para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
