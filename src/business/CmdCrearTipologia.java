/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearTipologia;
import generalities.Errores;
import data.Manejador;
import dataDB.Tipologia;
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
public class CmdCrearTipologia implements CommandInterface, Observable {

    private VentCrearTipologia ventana;
    private ArrayList<Observer> observadores = new ArrayList<>();
    private JButton boton;

    public CmdCrearTipologia(VentCrearTipologia ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        String nomTipologia = ventana.getNomTipologia();
        if (!nomTipologia.trim().isEmpty()) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            entityM.getTransaction().begin();
            if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                Tipologia tipologia = new Tipologia(null, nomTipologia);
                entityM.persist(tipologia);
                entityM.getTransaction().commit();
                ventana.confirmarTipologiaAgregada();
                notifyObservers();
            } /*
             * Se ejecuta cuando se oprime el botón "modificar"
             */ else {
                Tipologia tip = ventana.getTipologia();
                tip.setTipologia(nomTipologia);
                entityM.getTransaction().commit();
                ventana.confirmarTipologiaModificada();
            }
            ventana.cerrar();
        } else {
            JOptionPane.showMessageDialog(null, "Introduzca un nombre para la tipología.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            observer.actualizarDatos(null);
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
