/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Progacademica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class ComboBoxProgAcaAnt extends ComboBoxEliminar {

    public ComboBoxProgAcaAnt() {
        model = new DefaultComboBoxModel();
        this.setModel(model);
        completar(false);
    }

    @Override
    public void completar(boolean oculto) {
        if (model.getSize() != 0) {
            model.removeAllElements();
        }
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        //List<Progacademica> progAcaAnteriores = entity.createNamedQuery("Progacademica.findAll").getResultList();
        List<Progacademica> progAcaAnteriores = entity.createNamedQuery("Progacademica.findAll").setParameter("oculto", oculto).getResultList();
        for (Progacademica progacademica : progAcaAnteriores) {
            model.addElement(progacademica);
        }
    }

}
