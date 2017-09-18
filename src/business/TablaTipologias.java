/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Tipologia;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaTipologias extends Tabla {

    public TablaTipologias() {
        super();
        model.addColumn("Tipología");
        this.setModel(model);
        completarTabla(false);
        setAutoCreateRowSorter(true);
    }

    public void removerFila() {
        try {
            model.removeRow(this.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            model.removeRow(0);
        }
    }

    @Override
    public void completarTabla(boolean oculto) {
        super.completarTabla(oculto); //To change body of generated methods, choose Tools | Templates.
        //List<Tipologia> tipologias = entityM.createNamedQuery("Tipologia.findAll").getResultList();
        List<Tipologia> tipologias = entityM.createNamedQuery("Tipologia.findAll").setParameter("oculto", oculto).getResultList();
        for (Tipologia tipologia : tipologias) {
            model.addRow(new Object[]{tipologia});
        }
    }
}
