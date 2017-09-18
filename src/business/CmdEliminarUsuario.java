/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentEliminarUsuarios;
import exceptions.ImposibleEliminarException;
import generalities.Errores;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdEliminarUsuario implements CommandInterface {

    private ListaUsuarios listaUsuarios;
    private VentEliminarUsuarios ventana;

    public CmdEliminarUsuario(VentEliminarUsuarios ventana) {
        super();
        this.ventana = ventana;
        listaUsuarios = ventana.getListaUsuarios();
    }

    /**
     * Este método muestra una ventana donde pregunta si desea eliminarse un
     * usuario. Si la respuesta es afirmativa, obtiene el objeto de tipo
     * ListaUsuarios que contiene los objetos usuarios, para posteriormente
     * intentar eliminar el usuario seleccionado. El error que puede ocurrir es
     * que ese tipo de usuario no pueda ser eliminado y por tanto se produce una
     * excepción que muestra el mensaje de error. Si el usuario es eliminado
     * exitósamente, se muestra un mensaje de confirmación.
     */
    @Override
    public void processEvent() {
        if (listaUsuarios.getSelectedIndex() != -1) {
            if (deseaEliminarUsuario()) {
                try {
                    listaUsuarios.eliminar(listaUsuarios.getSelectedIndex());
                    Errores.confirmacionUsuarioEliminado();
                } catch (ImposibleEliminarException ex) {
                    Errores.errorEliminarTipoUsuario();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No ha seleccionado un usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    public boolean deseaEliminarUsuario() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
}
