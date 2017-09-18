/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentFormatoCorreoHorario;
import GUI.VentHorarioDocente;
import generalities.Utilidades;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Carlos
 */
public class CmdEnviarHorarioCorreo implements CommandInterface {

    private JTable horarioDocente;
    private VentHorarioDocente ventana;
    private VentFormatoCorreoHorario ventFormatoCorreo;
    private JButton boton;

    public CmdEnviarHorarioCorreo(JTable horarioDocente, VentHorarioDocente ventana, VentFormatoCorreoHorario ventFormatoCorreo, JButton boton) {
        this.horarioDocente = horarioDocente;
        this.ventana = ventana;
        this.ventFormatoCorreo = ventFormatoCorreo;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        if (ventana.getDocenteSeleccionado().getCorreoElectronico().trim().isEmpty() || ventana.getDocenteSeleccionado().getCorreoElectronico() == null) {
            JOptionPane.showMessageDialog(null, "El Docente no posee un correo electr�nico registrado. Reg�strelo e intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            boton.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Se le enviar� el horario al Docente seleccionado. Por favor espere.", "Informaci�n", JOptionPane.INFORMATION_MESSAGE);
            String nombreArchivo = ventana.getTitle() + ".csv";
            try {
                Utilidades.exportar(horarioDocente, nombreArchivo);
                Utilidades.enviarCorreo(ventana.getDocenteSeleccionado().getCorreoElectronico(), ventFormatoCorreo.getAsunto(), ventFormatoCorreo.getCuerpo(), nombreArchivo, nombreArchivo);
                JOptionPane.showMessageDialog(null, "Mensaje enviado correctamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error en la preparaci�n del archivo a ser enviado. " + ex, "Resultado de exportaci�n", JOptionPane.ERROR_MESSAGE);
            } catch (MessagingException ex) {
                JOptionPane.showMessageDialog(null, "Error enviando el correo electr�nico. Revise que el correo electr�nico del Docente sea correcto y su conexi�n a Internet, e intente nuevamente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                archivo.delete();
            }
            ventFormatoCorreo.guardarFormatoCorreo();
            ventFormatoCorreo.dispose();
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
