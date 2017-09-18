/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Asignatura;
import dataDB.Asignaturaenplanestudio;
import dataDB.Planestudio;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaAsignaturas extends Tabla {

    public TablaAsignaturas() {
        super();
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Nivel de estudio");
        model.addColumn("Vinculada a Planes de Estudio");
        model.addColumn("Activa/No activa");
        this.setModel(model);
        this.completarTabla(false);
        setAutoCreateRowSorter(true);
        this.getColumn("Activa/No activa").setCellRenderer(new ActivoRenderer());
    }

    public TablaAsignaturas(Planestudio plan) {
        super();
        //List<Asignaturaenplanestudio> asignaturaEnPlan = entityM.createNamedQuery("Asignaturaenplanestudio.findByIdPlanEstudio").setParameter("idPlanEstudio", plan.getIdPlan())
        //.getResultList();
        List<Asignaturaenplanestudio> asignaturaEnPlan = entityM.createNamedQuery("Asignaturaenplanestudio.findByIdPlanEstudio").setParameter("idPlanEstudio", plan.getIdPlan()).setParameter("oculto", false)
                .getResultList();
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Semestre");
        model.addColumn("Tipología");
        for (Asignaturaenplanestudio asignaturaenplanestudio : asignaturaEnPlan) {
            model.addRow(new Object[]{asignaturaenplanestudio.getAsignatura().getCodigoAsig(),
                asignaturaenplanestudio.getAsignatura().getNombreAsig(), asignaturaenplanestudio.getSemestre(),
                asignaturaenplanestudio.getIdTipologia()});
        }
        this.setModel(model);
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
        super.completarTabla(oculto);
        List<Asignatura> asignaturas = entityM.createNamedQuery("Asignatura.findAll").setParameter("oculto", oculto).getResultList();
        //List<Asignatura> asignaturas = entityM.createNamedQuery("Asignatura.findAll").getResultList();   
        for (Asignatura asignatura : asignaturas) {
            try {
                String activo = asignatura.getActivo() ? "Activo" : "No activa";
                model.addRow(new Object[]{asignatura.getCodigoAsig(), asignatura.getNombreAsig(),
                    asignatura.getIdNivelEstudio().getNivelEstudio(), asignatura.getPlanesEstudioVinculados(), activo});
            } catch (NullPointerException ex) {
            }
        }
    }
}
