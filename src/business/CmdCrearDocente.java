/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearDocente;
import generalities.Errores;
import data.Manejador;
import generalities.Utilidades;
import exceptions.CorreoNoValidoException;
import dataDB.Docente;
import dataDB.Tipocontrato;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearDocente implements CommandInterface {

    private VentCrearDocente ventana;
    private JButton boton;

    public CmdCrearDocente(VentCrearDocente ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    /**
     * Este método se encarga de revisar que los campos para agregar un docente
     * se encuentren diligenciados de forma correcta. Si es así, crea un nuevo
     * docente, de lo contrario, muestra los respectivos mensajes de error. Una
     * vez es creado el docente, se muestra un mensaje de confirmación y se
     * cierra la ventana.
     */
    @Override
    public void processEvent() {
        String nombre = ventana.getNomDocente();
        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Introduzca un nombre e intente nuevamente. ", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                int documentoDocente = Integer.parseInt(ventana.getDocDocente());
                String correo = ventana.getCorreoElec();
                Utilidades.validarCorreo(correo);
                Boolean activo = ventana.getActivo();
                String contrato = ventana.getTipoContrato();
                EntityManager entityM = Manejador.getManejador().gentEntityManager();
                entityM.getTransaction().begin();
                Tipocontrato tipoContrato = (Tipocontrato) entityM.createNamedQuery("Tipocontrato.findByTipoContrato").setParameter("tipoContrato", contrato).getSingleResult();
                if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                    Docente docente = new Docente(null, documentoDocente, nombre, activo);
                    docente.setCorreoElectronico(correo);
                    docente.setIdTipoContrato(tipoContrato);
                    entityM.persist(docente);
                    entityM.getTransaction().commit();
                    ventana.confirmarDocAgregado();
                } else {
                    Docente docente = ventana.getDocente();
                    docente.setNombreDocente(nombre);
                    docente.setActivo(activo);
                    docente.setCorreoElectronico(correo);
                    docente.setDocumentoDoc(documentoDocente);
                    docente.setIdTipoContrato(tipoContrato);
                    entityM.getTransaction().commit();
                    ventana.confirmarDocModificado();
                }
                ventana.dispose();
                ComboBoxDocentes.actualizar();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ventana, "Introduzca una identificación valida (sólo números).", "Error", JOptionPane.ERROR_MESSAGE);
                ventana.setDocDocente("");
            } catch (CorreoNoValidoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de contrato.", "Error", JOptionPane.ERROR_MESSAGE);
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
