/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Tipologia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class ComboBoxTipologia extends JComboBox implements Observer{

    private DefaultComboBoxModel model;
            
    public ComboBoxTipologia() {
        model = new DefaultComboBoxModel();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Tipologia> listaTipologia = entityM.createNamedQuery("Tipologia.findAll").getResultList();
        List<Tipologia> listaTipologia = entityM.createNamedQuery("Tipologia.findAll").setParameter("oculto", false).getResultList();
        for(Tipologia tip : listaTipologia){
            model.addElement(tip);
        }
        this.setModel(model);
    }

    @Override
    public void actualizarDatos(Observable subject) {
        model.removeAllElements();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Tipologia> listaTipologia = entityM.createNamedQuery("Tipologia.findAll").getResultList();
        List<Tipologia> listaTipologia = entityM.createNamedQuery("Tipologia.findAll").setParameter("oculto", false).getResultList();
        for(Tipologia tip : listaTipologia){
            model.addElement(tip);
        }
    }
    
}
