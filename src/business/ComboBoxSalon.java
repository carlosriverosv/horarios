/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Edificio;
import dataDB.Salon;
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
public class ComboBoxSalon extends JComboBox implements Observer, Observable, ActionListener {

    private DefaultComboBoxModel model;
    private ArrayList<Observer> observadores = new ArrayList<Observer>();

    public ComboBoxSalon() {
        model = new DefaultComboBoxModel();
        this.setModel(model);
        this.addActionListener(this);
    }

    @Override
    public void actualizarDatos(Observable subject) {
        model.removeAllElements();
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        JComboBox comboBoxEdificio = (JComboBox) subject;
        Edificio edificio = (Edificio) comboBoxEdificio.getSelectedItem();
        //List<Salon> salones = entity.createQuery("SELECT s FROM Salon s WHERE s.idEdificio =:idEdificio AND s.activo =:activo").setParameter("idEdificio",edificio).setParameter("activo", true).getResultList(); 
        List<Salon> salones = entity.createQuery("SELECT s FROM Salon s WHERE s.idEdificio =:idEdificio AND s.activo =:activo AND s.oculto =:oculto ORDER BY S.numeroSalon ASC").setParameter("idEdificio", edificio).setParameter("activo", true).setParameter("oculto", false).getResultList();
        for (Salon salon : salones) {
            model.addElement(salon);
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

}
