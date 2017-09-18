/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Semestre;
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
public class ComboBoxNumSemestre extends JComboBox implements Observer, Observable, ActionListener {

    private DefaultComboBoxModel model;
    private ArrayList<Observer> observadores = new ArrayList<Observer>();
    public static ArrayList<ComboBoxNumSemestre> todosComboBoxSemestre = new ArrayList<>();

    public ComboBoxNumSemestre() {
        model = new DefaultComboBoxModel();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        List<Semestre> semestres = entityM.createNamedQuery("Semestre.findAll").getResultList();
        for (Semestre semestre : semestres) {
            model.addElement(semestre);
        }
        this.setModel(model);
        this.addActionListener(this);
        todosComboBoxSemestre.add(this);
    }

    @Override
    public void actualizarDatos(Observable subject) {
        this.removeActionListener(this);
        ComboBoxPlanEstudios comboplanEstudio = (ComboBoxPlanEstudios) subject;
        model.removeAllElements();
        if (!comboplanEstudio.getSelectedItem().toString().isEmpty()) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            List<Semestre> semestres = entityM.createNamedQuery("Semestre.findAll").getResultList();
            for (Semestre semestre : semestres) {
                model.addElement(semestre);
            }
        } else {
            model.addElement("");
        }
        notifyObservers();
        this.addActionListener(this);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            observer.actualizarDatos(this);
        }
    }

    @Override
    public void register(Observer obs) {
        this.observadores.add(obs);
    }

    @Override
    public void unRegister(Observer obs) {
        this.observadores.remove(obs);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyObservers();
    }
    
    public static void actualizar() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        List<Semestre> semestres = entityM.createNamedQuery("Semestre.findAll").getResultList();
        for (ComboBoxNumSemestre comboBoxNumSemestre : todosComboBoxSemestre) {
            comboBoxNumSemestre.model.removeAllElements();
            for (Semestre semestre : semestres) {
                comboBoxNumSemestre.model.addElement(semestre);
            }
        }
    }

}
