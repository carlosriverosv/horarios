/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearEdificio;
import generalities.Errores;
import dataDB.Edificio;
import data.Manejador;
import dataDB.Salon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearEdificio implements Observable, CommandInterface {

    private VentCrearEdificio ventana;
    private ArrayList<Observer> observadores = new ArrayList<>();
    private JButton boton;

    public CmdCrearEdificio(VentCrearEdificio ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        String nombreEd = ventana.getNombreEd();
        if (!nombreEd.trim().isEmpty()) {
            try {
                String numeroEd = ventana.getNumeroEd();
                Boolean activo = ventana.getActivo();
                EntityManager entidadManejadora = Manejador.getManejador().gentEntityManager();
                entidadManejadora.getTransaction().begin();
                if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                    Edificio edificio = new Edificio(null, numeroEd, nombreEd, activo);
                    entidadManejadora.persist(edificio);
                    entidadManejadora.getTransaction().commit();
                    ventana.confirmarEdAgregado();
                    ComboBoxEdificio.actualizar();
                    notifyObservers();
                } else {
                    Edificio edificio = ventana.getEdificio();
                    edificio.setNumeroEd(numeroEd);
                    edificio.setNombre(nombreEd);
                    edificio.setActivo(activo);
                    //List<Salon> salones = entidadManejadora.createNamedQuery("Salon.findByIdEdificio").setParameter("idEdificio", edificio).getResultList();
                    List<Salon> salones = entidadManejadora.createNamedQuery("Salon.findByIdEdificio").setParameter("idEdificio", edificio).setParameter("oculto", false).getResultList();
                    for (Salon salon : salones) {
                        salon.setActivo(activo);
                    }
                    entidadManejadora.getTransaction().commit();
                    ventana.confirmarEdModificado();
                    ComboBoxEdificio.actualizar();
                }
                ventana.cerrar();
                ComboBoxEdificio.actualizar();
            } catch (NumberFormatException ex) {
                ventana.errorNumeroEd();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Introduzca un nombre para el edificio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            try {
                observer.actualizarDatos(this);
            } catch (NullPointerException ex) {
            }
        }
    }

    @Override
    public void register(Observer obs) {
        observadores.add(obs);
    }

    @Override
    public void unRegister(Observer obs) {
        observadores.remove(obs);
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
