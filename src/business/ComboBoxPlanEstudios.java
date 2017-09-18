/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Planestudio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Carlos
 */
public class ComboBoxPlanEstudios extends ComboBoxEliminar implements Observable, Observer, ActionListener{
    
    private ArrayList<Observer>listaObservadores = new ArrayList<>();
    public static ArrayList<ComboBoxPlanEstudios> todoComboPlan  = new ArrayList<>();
    
    public ComboBoxPlanEstudios() {
        model = new DefaultComboBoxModel();
        this.setModel(model);
        completar(false);
        this.addActionListener(this);
        todoComboPlan.add(this);
    }
    
    @Override
    public void completar(boolean oculto) {
        super.completar(oculto);
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        List<Planestudio> listaPlanEstudio = entityM.createNamedQuery("Planestudio.findAll").setParameter("oculto", oculto).getResultList();
        for (Planestudio listaPlan : listaPlanEstudio) {
            model.addElement(listaPlan);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observador : listaObservadores) {
            observador.actualizarDatos(this);
        }
    }

    @Override
    public void register(Observer observador) {
        this.listaObservadores.add(observador);
    }

    @Override
    public void unRegister(Observer observador) {
        this.listaObservadores.remove(observador);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyObservers();
    }

    @Override
    public void actualizarDatos(Observable subject) {
        this.removeActionListener(this);
        model.removeAllElements();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Planestudio> listaPlanEstudio = entityM.createNamedQuery("Planestudio.findAll").getResultList();
        List<Planestudio> listaPlanEstudio = entityM.createNamedQuery("Planestudio.findAll").setParameter("oculto", false).getResultList();
        for (Planestudio listaPlan : listaPlanEstudio) {
            model.addElement(listaPlan);
        }
        this.addActionListener(this);
    }
    
    public static void actualizar() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Planestudio> listaPlanEstudio = entityM.createNamedQuery("Planestudio.findAll").getResultList();
        List<Planestudio> listaPlanEstudio = entityM.createNamedQuery("Planestudio.findAll").setParameter("oculto", false).getResultList();
        for (ComboBoxPlanEstudios combo : todoComboPlan) {
            combo.model.removeAllElements();
            for (Planestudio planestudio : listaPlanEstudio) {
                combo.model.addElement(planestudio);
            }
        }
    }

}
