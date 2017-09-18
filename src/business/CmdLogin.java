/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import generalities.Errores;
import GUI.VentPrincipal;
import GUI.VentanaLogin;
import data.Manejador;
import data.Password;
import exceptions.PasswordException;
import data.Usuario;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.PersistenceException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdLogin implements CommandInterface {

    private VentanaLogin ventana;
    private JButton boton;

    public CmdLogin(VentanaLogin ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    /**
     * Este método obtiene los usuarios almacenados en el archivo.
     * Posteriormente crea un objeto tipo Invitado con los datos ingresados en
     * la ventana y los compara con todos los objetos existentes previamente
     * obtenidos. Si los datos ingresados coinciden con los de alguno de los
     * objetos tipo usuario, se crea la ventana principal. De lo contrario se
     * muestra una ventana de error.
     */
    @Override
    public void processEvent() {
        boton.setEnabled(false);
        String usuario = ventana.getNomUsuario();
        String pass = new String(ventana.getPassword());
        try {
            Manejador.getManejador(usuario, pass);
            ventana.dispose();
            try {
                new VentPrincipal(new Usuario(usuario, new Password(ventana.getPassword())));
            } catch (PasswordException ex) {
            }
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión con el servidor de Base de Datos. Asegúrese que su usuario y contraseña sean correctos.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
            boton.setEnabled(true);
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
