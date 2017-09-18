/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import exceptions.ImposibleEliminarException;
import data.Usuario;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Carlos
 */
public class ListaUsuarios extends JList {

    private List<Object> usuarios;
    private DefaultListModel modelo;

    public ListaUsuarios() {
        modelo = new DefaultListModel();
        usuarios = Usuario.getUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            modelo.addElement(usuarios.get(i));
        }
        this.setModel(modelo);
    }

    /**
     * Este método obtiene el objeto usuario seleccionado en la lista e intenta
     * eliminarlo. Si es posible eliminarlo, lo remueve de la lista, si no, no.
     */
    public void eliminar(int numUsuarioSeleccionado) throws ImposibleEliminarException {
        String usuario = (String) usuarios.get(numUsuarioSeleccionado);
        Usuario.eliminar(usuario);
        modelo.removeElementAt(numUsuarioSeleccionado);
    }
}
