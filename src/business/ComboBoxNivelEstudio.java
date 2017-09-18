/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Nivelestudio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class ComboBoxNivelEstudio extends JComboBox implements Observer {

    private DefaultComboBoxModel model;
    
    public ComboBoxNivelEstudio() {
        model = new DefaultComboBoxModel();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Nivelestudio> listaNivelEstudio = entityM.createNamedQuery("Nivelestudio.findAll").getResultList();
        List<Nivelestudio> listaNivelEstudio = entityM.createNamedQuery("Nivelestudio.findAll").setParameter("oculto", false).getResultList();
        for (Nivelestudio listaNivel : listaNivelEstudio) {
            model.addElement(listaNivel);
        }
        this.setModel(model);
    }
    
    @Override
    public void actualizarDatos(Observable subject) {
        model.removeAllElements();
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        //List<Nivelestudio> listaNivelEstudio = entityM.createNamedQuery("Nivelestudio.findAll").getResultList();
        List<Nivelestudio> listaNivelEstudio = entityM.createNamedQuery("Nivelestudio.findAll").setParameter("oculto", false).getResultList();
        for (Nivelestudio listaNivel : listaNivelEstudio) {
            model.addElement(listaNivel);
        }
    }
    
}
