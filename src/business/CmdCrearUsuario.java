/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import generalities.Errores;
import GUI.VentanaCrearUsuario;
import data.Usuario;
import data.Manejador;
import data.Password;
import exceptions.PasswordException;
import generalities.Utilidades;
import exceptions.NomUsuarioException;
import exceptions.UsuarioYaExisteException;
import exceptions.CorreoNoValidoException;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearUsuario implements CommandInterface {

    private VentanaCrearUsuario ventana;
    private Usuario invitado;
    private JButton boton;

    public CmdCrearUsuario(VentanaCrearUsuario ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        boton.setEnabled(false);
        Password pass = null;
        Password rePass = null;
        try {
            pass = new Password(ventana.getPassword());
            rePass = new Password(ventana.getRePassword());
            //compara que ambas contraseñas digitadas sean iguales y luego crea el invitado
            if (pass.compareTo(rePass) == 1) {
                boton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    Usuario.disponibilidadUsuario(ventana.getNomUsuario());
                    invitado = new Usuario(ventana.getNomUsuario(), pass, ventana.getCorreo());
                    JOptionPane.showMessageDialog(null, "Se enviará un mensaje al correo electrónico proporcionado. Por favor espere.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    //Se crea el usuario con el único consultarPermiso para seleccionar todas la tablas de
                    //la BD
                    EntityManager entityM = Manejador.getManejador().gentEntityManager();
                    entityM.getTransaction().begin();
                    entityM.createNativeQuery("CREATE USER '" + ventana.getNomUsuario() + "' IDENTIFIED BY '" + pass.toString() + "'").executeUpdate();
                    entityM.createNativeQuery("GRANT SELECT ON gestionacademica_dptoiee.* TO '" + ventana.getNomUsuario() + "'@'%'").executeUpdate();
                    entityM.createNativeQuery("CREATE USER '" + ventana.getNomUsuario() + "'@'localhost' IDENTIFIED BY '" + pass.toString() + "'").executeUpdate();
                    entityM.createNativeQuery("GRANT SELECT ON gestionacademica_dptoiee.* TO '" + ventana.getNomUsuario() + "'@'localhost'").executeUpdate();
                    entityM.createNativeQuery("GRANT SELECT ON mysql.db TO '" + ventana.getNomUsuario() + "'@'localhost'").executeUpdate();
                    entityM.createNativeQuery("GRANT SELECT ON mysql.db TO '" + ventana.getNomUsuario() + "'@'%'").executeUpdate();
                    entityM.getTransaction().commit();
                    try {
                        Utilidades.enviarCorreo(invitado);
                        Errores.confirmarEnvioCorreo();
                    } catch (MessagingException ex) {
                        Errores.errorEnvioCorreo(ex.getMessage());
                    }
                    ventana.dispose();
                } catch (UsuarioYaExisteException ex) {
                    Errores.errorUsuarioYaExiste();
                    boton.setEnabled(true);
                    boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } catch (CorreoNoValidoException ex) {
                    Errores.correoNoValido(ex.getMessage());
                    boton.setEnabled(true);
                    boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } catch (NomUsuarioException ex) {
                    Errores.nomUsuarioException(ex.getMessage());
                    boton.setEnabled(true);
                    boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            } else {
                Errores.errorPasswordsDiferentes();
                boton.setEnabled(true);
                boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        } catch (PasswordException ex) {
            Errores.errorLongitudPassword(ex.getMessage());
            boton.setEnabled(true);
            boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
