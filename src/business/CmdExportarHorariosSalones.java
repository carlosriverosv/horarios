/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import data.Manejador;
import generalities.Utilidades;
import dataDB.Salon;
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
public class CmdExportarHorariosSalones implements CommandInterface {

    private VentProgAca ventana;
    private HorarioSalon jTableHorarioSalon;

    public CmdExportarHorariosSalones(VentProgAca ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        String ubicacionArchivo = Utilidades.mostrarVentanaArchivo("Elija una ubicación para los archivos", JFileChooser.DIRECTORIES_ONLY, null, null);
        if (!ubicacionArchivo.isEmpty()) {
            jTableHorarioSalon = ventana.getTablaHorarioSalon();
            File archivo = Utilidades.crearCarpeta(ubicacionArchivo, "/Horarios Salones " + ventana.getNumeroProg());
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            List<Salon> salones = entityM.createNamedQuery("Salon.findByActivo").setParameter("activo", true).getResultList();
            boolean fallo = false;
            for (Salon salon : salones) {
                try {
                    String rutaArchivo = archivo.getAbsolutePath() + "/Edificio " + salon.getIdEdificio().getNumeroEd()
                            + " - salón " + salon.getNumeroSalon() + " - " + ventana.getNumeroProg() + ".csv";
                    jTableHorarioSalon.setModel(jTableHorarioSalon.getModeloHorarioSalon(salon));
                    Utilidades.exportar(jTableHorarioSalon, rutaArchivo);
                } catch (IOException ex) {
                    fallo = true;
                    JOptionPane.showMessageDialog(null, "Error en la exportación del horario del Docente " + salon.getNumeroSalon() + " " + ex, "Resultado de exportación", JOptionPane.ERROR_MESSAGE);
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
