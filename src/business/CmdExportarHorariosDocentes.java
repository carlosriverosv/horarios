/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import data.Manejador;
import generalities.Utilidades;
import dataDB.Docente;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdExportarHorariosDocentes implements CommandInterface {

    private VentProgAca ventana;
    private HorarioDocente jTableHorarioDocente;

    public CmdExportarHorariosDocentes(VentProgAca ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        String ubicacionArchivo = Utilidades.mostrarVentanaArchivo("Elija una ubicación para los archivos", JFileChooser.DIRECTORIES_ONLY, null, null);
        if (!ubicacionArchivo.isEmpty()) {
            jTableHorarioDocente = ventana.getTablaHorarioDocente();
            File archivo = Utilidades.crearCarpeta(ubicacionArchivo, "/Horarios Docentes " + ventana.getNumeroProg());
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            List<Docente> docentes = entityM.createNamedQuery("Docente.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
            //List<Docente> docentes = entityM.createNamedQuery("Docente.findByActivo").setParameter("activo", true).getResultList();
            boolean fallo = false;
            for (Docente docente : docentes) {
                try {
                    String rutaArchivo = archivo.getAbsolutePath() + "/"
                            + docente.getNombreDocente() + " - " + ventana.getNumeroProg() + ".csv";
                    jTableHorarioDocente.setModel(jTableHorarioDocente.getModeloHorarioDocente(docente));
                    Utilidades.exportar(jTableHorarioDocente, rutaArchivo);
                } catch (IOException ex) {
                    fallo = true;
                    JOptionPane.showMessageDialog(null, "Error en la exportación del horario del Docente " + docente.getNombreDocente() + " " + ex, "Resultado de exportación", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!fallo) {
                JOptionPane.showMessageDialog(null, "Exportación exitosa", "Resultado de exportación", JOptionPane.INFORMATION_MESSAGE);
            }
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
