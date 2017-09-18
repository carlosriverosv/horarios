/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Docente;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaDocentes extends Tabla {

    public TablaDocentes() {
        super();
        model.addColumn("Documento");
        model.addColumn("Nombre");
        model.addColumn("Correo electrónico");
        model.addColumn("Tipo de contrato");
        model.addColumn("Activo/No activo");
        this.setModel(model);
        this.completarTabla(false);
        setAutoCreateRowSorter(true);
        this.getColumn("Activo/No activo").setCellRenderer(new ActivoRenderer());
    }

    public void completarTabla(boolean oculto) {
        //List<Docente> docentes = entityM.createNamedQuery("Docente.findAll").getResultList();
        super.completarTabla(oculto);
        List<Docente> docentes = entityM.createNamedQuery("Docente.findAll").setParameter("oculto", oculto).getResultList();
        for (Docente docente : docentes) {
            String activo = docente.getActivo() ? "Activo" : "No activo";
            model.addRow(new Object[]{docente.getDocumentoDoc(), docente.getNombreDocente(), docente.getCorreoElectronico(), docente.getIdTipoContrato().getTipoContrato(), activo});
        }
    }

    public void removerFila() {
        model.removeRow(this.getSelectedRow());
    }
}
