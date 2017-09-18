/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentFormatoCorreoHorario;
import GUI.VentProgAca;
import data.Manejador;
import generalities.Utilidades;
import dataDB.Docente;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Carlos
 */
public class CmdEnviarTodosHorariosCorreo implements CommandInterface {

    private VentProgAca ventana;
    private HorarioDocente jTableHorarioDocente;
    private VentFormatoCorreoHorario ventFormatoCorreo;
    private JButton boton;

    public CmdEnviarTodosHorariosCorreo(VentProgAca ventana, VentFormatoCorreoHorario ventFormatoCorreo, JButton boton) {
        this.ventana = ventana;
        this.ventFormatoCorreo = ventFormatoCorreo;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        boton.setEnabled(false);
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea enviar a todos los Docentes su respectivo horario de la programación académica actual?", "Enviar horarios a Docentes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Se le enviará a todos los Docentes su horario correspondiente a la programación académica actual. Por favor espere.", "Información", JOptionPane.INFORMATION_MESSAGE);
            jTableHorarioDocente = ventana.getTablaHorarioDocente();
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            List<Docente> docentes = entityM.createNamedQuery("Docente.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
            //List<Docente> docentes = entityM.createNamedQuery("Docente.findByActivo").setParameter("activo", true).getResultList();
            String msjError = "";
            for (Docente docente : docentes) {
                String nombreArchivo = "";
                try {
                    nombreArchivo = docente.getNombreDocente() + " - " + ventana.getNumeroProg() + ".csv";
                    jTableHorarioDocente.setModel(jTableHorarioDocente.getModeloHorarioDocente(docente));
                    Utilidades.exportar(jTableHorarioDocente, nombreArchivo);
                    Utilidades.enviarCorreo(docente.getCorreoElectronico(), ventFormatoCorreo.getAsunto(), ventFormatoCorreo.getCuerpo(), nombreArchivo, nombreArchivo);
                } catch (IOException ex) {
                    msjError += "Error en la preparación del archivo del Docente " + docente.getNombreDocente() + " a ser enviado. " + System.lineSeparator();
                } catch (MessagingException ex) {
                    msjError += "Error enviando el correo electrónico al Docente " + docente.getNombreDocente() + ". Revise que la dirección de correo electrónico sea correcta y su conexión a Internet, e intente nuevamente." + System.lineSeparator();
                }
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) {
                    archivo.delete();
                }
            }
            if (!msjError.trim().isEmpty()) {
                JTextArea txtError = new JTextArea(msjError, 20, 100);
                txtError.setEditable(false);
                JScrollPane info = new JScrollPane(txtError);
                JOptionPane.showMessageDialog(null, info, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Correos envíados exitósamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        ventFormatoCorreo.guardarFormatoCorreo();
        ventFormatoCorreo.dispose();
        boton.setEnabled(true);
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
