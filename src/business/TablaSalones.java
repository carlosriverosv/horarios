/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Salon;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaSalones extends Tabla {

    public TablaSalones() {
        super();
        model.addColumn("Número de salón");
        model.addColumn("Identificador del edificio");
        model.addColumn("Capacidad");
        model.addColumn("Observaciones");
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
        super.completarTabla(oculto);
        //List<Salon> salones = entityM.createNamedQuery("Salon.findAll").getResultList();
        List<Salon> salones = entityM.createNamedQuery("Salon.findAll").setParameter("oculto", oculto).getResultList();
        for (Salon salon : salones) {
            String activo = salon.getActivo() ? "Activo" : "No activo";
            model.addRow(new Object[]{salon.getNumeroSalon(), salon.getIdEdificio().getNumeroEd(), salon.getCapacidad(), salon.getObservaciones(), activo});
        }
    }
}
