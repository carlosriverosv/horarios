/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Docente;
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
public class ComboBoxDocentes extends JComboBox implements Observable, ActionListener {

    private ArrayList<Observer> observadores = new ArrayList<>();
    private DefaultComboBoxModel model;
    public static ArrayList<ComboBoxDocentes> todosComboBoxDocentes = new ArrayList<>();

    public ComboBoxDocentes() {
        model = new DefaultComboBoxModel();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Docente> listaDocentes = entityM.createNamedQuery("Docente.findByActivo").setParameter("activo", true).getResultList();
        List<Docente> listaDocentes = entityM.createNamedQuery("Docente.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
        for (Docente docente : listaDocentes) {
            model.addElement(docente);
        }
        this.setModel(model);
        this.addActionListener(this);
        todosComboBoxDocentes.add(this);
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
        //List<Docente> listaDocentes = entityM.createNamedQuery("Docente.findByActivo").setParameter("activo", true).getResultList();
        List<Docente> listaDocentes = entityM.createNamedQuery("Docente.findByOculto").setParameter("oculto", false).setParameter("activo", true).getResultList();
        for (ComboBoxDocentes combo : todosComboBoxDocentes) {
            combo.model.removeAllElements();
            for (Docente docente : listaDocentes) {
                combo.model.addElement(docente);
            }
        }
    }

}
