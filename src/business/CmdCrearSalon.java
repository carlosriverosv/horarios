/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearSalon;
import generalities.Errores;
import data.Manejador;
import dataDB.Edificio;
import dataDB.Salon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearSalon implements CommandInterface {

    private VentCrearSalon ventana;
    private JButton boton;

    public CmdCrearSalon(VentCrearSalon ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    /**
     * Este m�todo se encarga de revisar que los campos para agregar un sal�n se
     * encuentren diligenciados de forma correcta. Si es as�, crea un nuevo
     * sal�n, de lo contrario, muestra los respectivos mensajes de error. Una
     * vez es creado el sal�n, se muestra un mensaje de confirmaci�n y se borran
     * todos los campos.
     */
    @Override
    public void processEvent() {
        Edificio edificio = ventana.getNumEdificio();
        String numeroSalon = ventana.getNumSalon();
        String capacidad = ventana.getCapacidad();
        String observaciones = ventana.getObservaciones();
        if (numeroSalon.isEmpty() || capacidad.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Los campos: n�mero de sal�n y capacidad deben contener alg�n valor.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int numCapacidad = Integer.parseInt(capacidad);
                boolean activo = ventana.getActivo();
                EntityManager entityM = Manejador.getManejador().gentEntityManager();
                entityM.getTransaction().begin();

                if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                    Salon salon = new Salon(null, numeroSalon, numCapacidad, activo);
                    salon.setIdEdificio(edificio);
                    salon.setObservaciones(observaciones);
                    entityM.persist(salon);
                    entityM.getTransaction().commit();
                    ventana.confirmacionSalonAgregado();
                } else {
                    Salon salon = ventana.getSalon();
                    salon.setIdEdificio(edificio);
                    salon.setNumeroSalon(numeroSalon);
                    salon.setCapacidad(numCapacidad);
                    salon.setActivo(activo);
                    salon.setObservaciones(observaciones);
                    entityM.getTransaction().commit();
                    ventana.confirmacionSalonModificado();
                }
                ventana.cerrar();
                ComboBoxEdificio.actualizar();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ventana, "Los campos: n�mero de edificio, n�mero de sal�n y capacidad deben contener �nicamente valores num�ricos.", "Error", JOptionPane.ERROR_MESSAGE);
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
