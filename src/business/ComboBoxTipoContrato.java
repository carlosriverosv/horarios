/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Tipocontrato;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Carlos
 */
public class ComboBoxTipoContrato extends ComboBoxEliminar implements Observer{
    
    public ComboBoxTipoContrato() {
        model = new DefaultComboBoxModel();
        this.setModel(model);
        completar(false);
    }

    @Override
    public void actualizarDatos(Observable subject) {
        completar(false);
    }

    @Override
    public void completar(boolean oculto) {
        super.completar(oculto);
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        List<Tipocontrato> listaTipoCotrato = entityM.createNamedQuery("Tipocontrato.findAll").setParameter("oculto", oculto).getResultList();
        for(Tipocontrato tipCon : listaTipoCotrato){
            model.addElement(tipCon);
        }
    }
}
