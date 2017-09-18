/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCambiarPass;
import data.Password;
import exceptions.PasswordException;
import generalities.Errores;
import generalities.Utilidades;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.swing.JButton;

/**
 *
 * @author Carlos
 */
public class CmdCambiarPass implements CommandInterface {

    private VentCambiarPass ventana;
    private JButton boton;

    public CmdCambiarPass(VentCambiarPass ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    /**
     * Este método verifica primero si la contraseña ingresada corresponde a la
     * contraseña actual del usuario. Si es así, verifica que la contraseña
     * nueva y su repetición sean iguales entre sí. De serlo, procede a
     * modificar el usuario, enviar un correo electrónico a la cuentra
     * registrada y cerrar la ventana. En caso de presentarse algún error, las
     * ventana de error se muestran.
     *
     */
    @Override
    public void processEvent() {
        boton.setEnabled(false);
        Password passActual = null;
        try {
            passActual = new Password(ventana.getPassword());
            if (passActual.compareTo(ventana.getUsuario().getPassword()) == 1) {
                Password passNueva = null;
                Password rePassNueva = null;

                passNueva = new Password(ventana.getPassNueva());
                rePassNueva = new Password(ventana.getRePassword());

                if (passNueva.compareTo(rePassNueva) == 1) {
                    try {
                        ventana.getUsuario().modificar(ventana.getUsuario().getNomUsuario(), passNueva);
                        ventana.getUsuario().setCorreo(ventana.getCorreo());
                        Utilidades.enviarCorreo(ventana.getUsuario());
                        Errores.confirmarEnvioCorreo();
                        ventana.dispose();
                    } catch (FileNotFoundException ex) {
                        Errores.errorArchivoUsuario(ex.getMessage());
                    } catch (IOException ex) {
                        Errores.errorEntradaSalidaUsuario(ex.getMessage());
                    } catch (MessagingException ex) {
                        Errores.errorEnvioCorreo(ex.getMessage());
                        ventana.dispose();
                    }
                } else {
                    Errores.errorPasswordsDiferentes();
                    boton.setEnabled(true);
                }
            } else {
                Errores.errorPassword();
                boton.setEnabled(true);
            }
        } catch (PasswordException ex) {
            Errores.errorLongitudPassword(ex.getMessage());
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
