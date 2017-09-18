/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Edificio;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaEdificios extends Tabla {

    public TablaEdificios() {
        super();
        model.addColumn("Identificador del edificio");
        model.addColumn("Nombre");
        model.addColumn("Activo/No activo");
        this.setModel(model);
        completarTabla(false);
        setAutoCreateRowSorter(true);
        this.getColumn("Activo/No activo").setCellRenderer(new ActivoRenderer());
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
        //List<Edificio> edificios = entityM.createNamedQuery("Edificio.findAll").getResultList();
        List<Edificio> edificios = entityM.createNamedQuery("Edificio.findAll").setParameter("oculto", oculto).getResultList();
        for (Edificio edificio : edificios) {
            String activo = edificio.getActivo() ? "Activo" : "No activo";
            model.addRow(new Object[]{edificio.getNumeroEd(), edificio.getNombre(), activo});
        }
    }

}
