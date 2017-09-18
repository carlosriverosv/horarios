/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Nivelestudio;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaNivelesEstudio extends Tabla {

    public TablaNivelesEstudio() {
        super();
        model.addColumn("Niveles de estudio");
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
        //List<Nivelestudio> nivelesEstudio = entityM.createNamedQuery("Nivelestudio.findAll").getResultList();
        List<Nivelestudio> nivelesEstudio = entityM.createNamedQuery("Nivelestudio.findAll").setParameter("oculto", oculto).getResultList();
        for (Nivelestudio nivelestudio : nivelesEstudio) {
            model.addRow(new Object[]{nivelestudio});
        }
    }
}
