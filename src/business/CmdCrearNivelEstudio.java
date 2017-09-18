/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearNivelEstudio;
import generalities.Errores;
import data.Manejador;
import dataDB.Nivelestudio;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearNivelEstudio implements Observable, CommandInterface {

    private VentCrearNivelEstudio ventana;
    private ArrayList<Observer> observadores = new ArrayList<>();
    private JButton boton;

    public CmdCrearNivelEstudio(VentCrearNivelEstudio ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {

        String nomNivelEstudio = ventana.getNombreNivelEstudio();
        if (!nomNivelEstudio.trim().isEmpty()) {
            EntityManager entidadManejadora = Manejador.getManejador().gentEntityManager();
            entidadManejadora.getTransaction().begin();
            if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                entidadManejadora.persist(new Nivelestudio(null, nomNivelEstudio));
                entidadManejadora.getTransaction().commit();
                ventana.confirmarNivelEstudioAgregado();
                notifyObservers();
            } else {
                Nivelestudio nivelEstudio = ventana.getNivelEstudio();
                nivelEstudio.setNivelEstudio(nomNivelEstudio);
                entidadManejadora.getTransaction().commit();
                ventana.confirmarNivelEstudioModificado();
            }
            ventana.cerrar();
        } else {
            JOptionPane.showMessageDialog(null, "Introduzca un nombre para el nivel de estudio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            observer.actualizarDatos(this);
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
