/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataDB.Grupo;
import dataDB.Progacademica;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class TablaInscritos extends Tabla {

    public TablaInscritos(String numProgAca) {
        super();
        List<Grupo> grupos = entityM.createNamedQuery("Grupo.findByIdProg")
                .setParameter("idProg", ((Progacademica) entityM.createNamedQuery("Progacademica.findByNumeroProg").setParameter("numeroProg", numProgAca).getSingleResult()).getIdProg())
                .setParameter("oculto", false).getResultList();
        model.addColumn("Código");
        model.addColumn("Asignatura");
        model.addColumn("Grupo");
        model.addColumn("Inscritos");
        for (Grupo grupo : grupos) {
            model.addRow(new Object[]{grupo.getAsignatura().getCodigoAsig(),grupo.getAsignatura().getNombreAsig(), grupo.getGrupoPK().getNumeroGrupo(), grupo.getInscritos()});
        }
        this.setModel(model);
        setAutoCreateRowSorter(true);
    }
}