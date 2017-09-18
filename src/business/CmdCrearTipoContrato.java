/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearTipoContrato;
import generalities.Errores;
import data.Manejador;
import dataDB.Tipocontrato;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.swing.JButton;

/**
 *
 * @author Carlos
 */
public class CmdCrearTipoContrato implements CommandInterface, Observable {

    private VentCrearTipoContrato ventana;
    private ArrayList<Observer> observadores = new ArrayList<>();
    private JButton boton;

    public CmdCrearTipoContrato(VentCrearTipoContrato ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {

        String nomTipoContrato = ventana.getNomTipoContrato();
        if (!nomTipoContrato.isEmpty()) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            entityM.getTransaction().begin();
            if (!boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                entityM.persist(new Tipocontrato(null, nomTipoContrato));
                entityM.getTransaction().commit();
                ventana.confirmarTipoContratoAgregado();
                notifyObservers();
            } else {
                Tipocontrato tipoContrato = ventana.getTipoContrato();
                tipoContrato.setTipoContrato(nomTipoContrato);
                entityM.getTransaction().commit();
                ventana.confirmarTipoContratoModificado();
            }
            ventana.cerrar();
        } else {
            ventana.errorNombreVacio();
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
