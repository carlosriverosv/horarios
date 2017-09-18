/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentConfCorreo;
import GUI.VentanaCrearAdmin;
import generalities.Utilidades;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.sql.Connection;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdConfCorreo implements CommandInterface {

    private VentConfCorreo ventana;
    private JButton boton;
    private Connection conn;

    public CmdConfCorreo(VentConfCorreo ventana, JButton boton, Connection conn) {
        this.ventana = ventana;
        this.boton = boton;
        this.conn = conn;
    }

    @Override
    public void processEvent(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "" + ventana.getSMTP() + "");
        props.put("mail.smtp.port", "" + ventana.getPuerto() + "");
        props.put("usuario", "" + ventana.getUsuario() + "@" + ventana.getDominio() + "");
        props.put("pass", "" + new String(ventana.getPassword()) + "");

        FileOutputStream out;
        File archivo = null;
        try {
            File carpeta = new File("");
            carpeta.setWritable(true);
            out = new FileOutputStream("ConfCorreo.properties");
            props.store(out, "---Datos de configuración del correo---");
            out.close();
            archivo = new File("ConfCorreo.properties");
            archivo.setReadOnly();
            Files.setAttribute(archivo.toPath(), "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
            boton.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Se enviará un mensaje al correo electrónico proporcionado.", "Información", JOptionPane.INFORMATION_MESSAGE);

            try {
                Utilidades.enviarCorreo(ventana.getUsuario() + "@" + ventana.getDominio(), "Información - Aplicativo Gestión Académica DPTOIEE",
                        "Esta dirección de correo se ha configurado para ser desde la cual "
                        + "se envíen los mensajes del Aplicativo "
                        + "de Gestión Académica del Departamento de Ingeniería Eléctrica y Electrónica "
                        + "de la Universidad Nacional de Colombia.", null, null);
                JOptionPane.showMessageDialog(null, "Mensaje enviado exitósamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                ventana.dispose();
                JOptionPane.showMessageDialog(null, "Se creará el administrador del sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
                new VentanaCrearAdmin(conn);
            } catch (MessagingException ex) {
                JOptionPane.showMessageDialog(null, "Error enviando el correo. Revise todos los campos y/o su conexión a Internet, e intente nuevamente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                archivo.delete();
                boton.setEnabled(true);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
