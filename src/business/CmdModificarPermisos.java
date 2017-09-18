/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentPermisos;
import data.Usuario;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdModificarPermisos implements CommandInterface {

    private VentPermisos ventana;

    public CmdModificarPermisos(VentPermisos ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        String nomUsuario = ventana.getUsuarioSeleccionado();
        if (nomUsuario != null) {
            if (Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_ACTUALIZAR) && !ventana.modificarSeleccionado()) {
                Usuario.removerPermiso(nomUsuario, "UPDATE");
            } else if (!Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_ACTUALIZAR) && ventana.modificarSeleccionado()) {
                Usuario.asignarPermiso(nomUsuario, "UPDATE");
            }
            if (Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_INSERTAR) && !ventana.crearSeleccionado()) {
                Usuario.removerPermiso(nomUsuario, "INSERT");
            } else if (!Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_INSERTAR) && ventana.crearSeleccionado()) {
                Usuario.asignarPermiso(nomUsuario, "INSERT");
            }
            if (!Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_ELIMINAR) && ventana.crearModEliSeleccionado()) {
                Usuario.asignarPermiso(nomUsuario, "INSERT");
                Usuario.asignarPermiso(nomUsuario, "UPDATE");
                Usuario.asignarPermiso(nomUsuario, "DELETE");
                Usuario.asignarPermiso(nomUsuario, "CREATE TEMPORARY TABLES");
            } else if (Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_ELIMINAR) && !ventana.crearModEliSeleccionado()) {
                Usuario.removerPermiso(nomUsuario, "INSERT");
                Usuario.removerPermiso(nomUsuario, "UPDATE");
                Usuario.removerPermiso(nomUsuario, "DELETE");
                Usuario.removerPermiso(nomUsuario, "CREATE TEMPORARY TABLES");
            }
            JOptionPane.showMessageDialog(null, "Modificación de permisos realizada con éxito.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            ventana.dispose();
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
