/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearAsignatura;
import GUI.VentAsignaturas;
import data.Manejador;
import dataDB.Asignatura;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModAsignatura implements CommandInterface {

    private VentAsignaturas ventana;
    private ListaAsignaturas lista;

    public CmdModAsignatura(VentAsignaturas ventana) {
        this.ventana = ventana;
    }

    public CmdModAsignatura(ListaAsignaturas lista) {
        this.lista = lista;
    }

    @Override
    public void processEvent() {
        int codAsignatura = 0;
        codAsignatura = ventana != null ? ventana.getSeleccionado() : lista.getCodigoAsigSeleccionada();
        if (codAsignatura != 0) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Asignatura asignatura = (Asignatura) entityM.createNamedQuery("Asignatura.findByCodigoAsig").setParameter("codigoAsig", codAsignatura).getSingleResult();
            new VentCrearAsignatura(asignatura);
            if(ventana != null) {
                ventana.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna asignatura para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
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
