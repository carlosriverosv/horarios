/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentanaLogin;
import generalities.Utilidades;
import data.Usuario;
import generalities.Errores;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.mail.*;

/**
 *
 * @author Carlos
 */
public class CmdAceptarRecordarPass implements CommandInterface {

    private VentanaLogin ventana;
    private static ArrayList<Usuario> usuarios;
    private boolean encontrado = false;

    public CmdAceptarRecordarPass(VentanaLogin ventana) {
        super();
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {

        String correo = ventana.getCorreo();
        //Recupera los usuarios almacenados en el arraylist luego de ser leidos del archivo y compara los coreos de estos con 
        //el correo del usuario ingresado
        //Si el usuario se encuentra, la variable encontrado se iguala a true, de lo contrario a false.
        int i = 0;
        while (i < usuarios.size() && encontrado != true) {
            Usuario user = usuarios.get(i);
            encontrado = user.getCorreo().equalsIgnoreCase(correo);
            i++;
        }
        //Si los datos ingresados en la ventana login no coinciden con ninguno de los que se encuentran en el archivo
        if (!encontrado) {
            Errores.errorCorreoUsuario();
        } else {
            try {
                Utilidades.enviarCorreo((Usuario) usuarios.get(i - 1));
            } catch (MessagingException ex) {
                Errores.errorEnvioCorreo(ex.getMessage());
            }
            Errores.confirmarEnvioCorreo();
            ventana.dispose();
            new VentanaLogin();
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
