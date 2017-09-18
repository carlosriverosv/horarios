/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Edificio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class ComboBoxEdificio extends JComboBox implements Observable, ActionListener {

    private ArrayList<Observer> observadores = new ArrayList<>();
    private DefaultComboBoxModel model;
    public static ArrayList<ComboBoxEdificio> todosComboBoxEdificios = new ArrayList<>();

    public ComboBoxEdificio() {
        model = new DefaultComboBoxModel();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Edificio> listaEdificio = entityM.createNamedQuery("Edificio.findByActivo").setParameter("activo", true).getResultList();
        List<Edificio> listaEdificio = entityM.createNamedQuery("Edificio.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
        for (Edificio listaEd : listaEdificio) {
            model.addElement(listaEd);
        }
        this.setModel(model);
        this.addActionListener(this);
        todosComboBoxEdificios.add(this);
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
        notifyObservers();
    }

    @Override
    public void unRegister(Observer obs) {
        observadores.remove(obs);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyObservers();
    }

    public static void actualizar() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Edificio> listaEdificio = entityM.createNamedQuery("Edificio.findByActivo").setParameter("activo", true).getResultList();
        List<Edificio> listaEdificio = entityM.createNamedQuery("Edificio.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
        for (ComboBoxEdificio combo : todosComboBoxEdificios) {
            combo.model.removeAllElements();
            for (Edificio listaEd : listaEdificio) {
                combo.model.addElement(listaEd);
            }
            combo.notifyObservers();
        }
    }

}
