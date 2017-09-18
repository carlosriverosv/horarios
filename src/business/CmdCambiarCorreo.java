/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCambiarCorreo;
import data.Password;
import exceptions.PasswordException;
import data.Usuario;
import generalities.Utilidades;
import exceptions.CorreoNoValidoException;
import generalities.Errores;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.mail.MessagingException;

/**
 *
 * @author Carlos
 */
public class CmdCambiarCorreo implements CommandInterface {

    private VentCambiarCorreo ventana;

    public CmdCambiarCorreo(VentCambiarCorreo ventana) {
        super();
        this.ventana = ventana;
    }

    /**
     * Este método verifica que la contraseña ingresada corresponda con la del
     * usuario activo en el sistema. Si es así, verifica que se haya ingresado
     * algo en el campo "correo electrónico" y posteriormente se modifican los
     * datos, se envía un correo electrónico y se muestra la confirmación del
     * mismo. En caso de que se presente algún error, se mostrará.
     */
    @Override
    public void processEvent() {

        try {
            Password password = new Password(ventana.getPassword());
            Usuario usuario = ventana.getUsuario();
            String correo = ventana.getCorreo();
            if (password.compareTo(usuario.getPassword()) == 1) {
                if (!correo.isEmpty()) {
                    usuario.modificar(correo);
                    Utilidades.enviarCorreo(usuario);
                    Errores.confirmarEnvioCorreo();
                    ventana.dispose();
                } else {
                    Errores.errorCorreoVacio();
                }
            } else {
                Errores.errorPassword();
            }
        } catch (PasswordException ex) {
            Errores.errorLongitudPassword(ex.getMessage());
        } catch (FileNotFoundException ex) {
            Errores.errorArchivoUsuario(ex.getMessage());
        } catch (IOException ex) {
            Errores.errorArchivoUsuario(ex.getMessage());
        } catch (MessagingException ex) {
            Errores.errorEnvioCorreo(ex.getMessage());
            ventana.dispose();
        } catch (CorreoNoValidoException ex) {
            Errores.correoNoValido(ex.getMessage());
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
