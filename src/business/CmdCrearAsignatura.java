/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearAsignatura;
import generalities.Errores;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Nivelestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearAsignatura implements CommandInterface {

    private VentCrearAsignatura ventana;
    private JButton boton;

    public CmdCrearAsignatura(VentCrearAsignatura ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        String nombreAsignatura = ventana.getNombreAsignatura();
        if (!nombreAsignatura.trim().isEmpty()) {
            try {
                int codigoAsignatura = Integer.parseInt(ventana.getCodAsignatura());
                boolean activo = ventana.getActivo();
                Nivelestudio nivelEstudio = ventana.getNivelEstudio();
                if (nivelEstudio == null) {
                    throw new NullPointerException();
                }
                EntityManager entityM = Manejador.getManejador().gentEntityManager();
                entityM.getTransaction().begin();
                if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                    Asignatura asignatura = new Asignatura(null, codigoAsignatura, nombreAsignatura, activo);
                    asignatura.setIdNivelEstudio(nivelEstudio);
                    entityM.persist(asignatura);
                    entityM.getTransaction().commit();
                    ventana.confirmarAsignaturaAgregada();
                } else {
                    Asignatura asignatura = ventana.getAsignatura();
                    asignatura.setCodigoAsig(codigoAsignatura);
                    asignatura.setNombreAsig(nombreAsignatura);
                    asignatura.setActivo(activo);
                    asignatura.setIdNivelEstudio(nivelEstudio);
                    entityM.getTransaction().commit();
                    ventana.confirmarAsignaturaModificada();
                }
                ventana.cerrar();
                ComboBoxAsignaturas.actualizar();
                ListaSemestre.actualizar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "El campo código asignatura debe contener únicamente valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(ventana, "Seleccione un nivel de estudio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Introduzca un nombre para la asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
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
