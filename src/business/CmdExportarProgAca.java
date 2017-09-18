/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import data.Manejador;
import dataDB.Itemhorario;
import dataDB.Progacademica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

/**
 *
 * @author Carlos
 */
public class CmdExportarProgAca extends CmdExportar {

    public CmdExportarProgAca(VentProgAca ventana) {
        super(ventana);
        nombreArchivo = "Programación Académica " + ventana.getNumeroProg();
    }

    @Override
    public void processEvent() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        List<Itemhorario> itemsHorario = entityM.createNamedQuery("Itemhorario.findByIdProg").setParameter("idProg", ventanaProg.getIdProg()).setParameter("oculto", false).getResultList();
        Object datos[][] = new Object[itemsHorario.size()][Progacademica.nombreColumnas.length];
        int fila = 0;
        for (Itemhorario itemhorario : itemsHorario) {
            datos[fila][0] = itemhorario.getGrupo().getAsignatura().getCodigoAsig();
            datos[fila][1] = itemhorario.getGrupo().getAsignatura().getNombreAsig();
            datos[fila][2] = itemhorario.getGrupo().getGrupoPK().getNumeroGrupo();
            datos[fila][3] = itemhorario.getLimiteMax();
            datos[fila][4] = itemhorario.getGrupo().getAsignatura().getPlanesEstudioVinculados();
            datos[fila][5] = itemhorario.getCuotas();
            datos[fila][6] = Horario.getNumeroDia(itemhorario.getDia1().getDia());
            datos[fila][7] = itemhorario.getHorainicio().getHoraInicio() + ":00";
            datos[fila][8] = (itemhorario.getHorainicio().getHoraInicio() + 1) + ":00";
            datos[fila][9] = itemhorario.getIdSalon().getIdEdificio().getNumeroEd();
            datos[fila][10] = itemhorario.getIdSalon().getNumeroSalon();
            datos[fila][11] = itemhorario.getIdDocente().getDocumentoDoc();
            datos[fila][12] = itemhorario.getIdDocente().getNombreDocente();
            datos[fila][13] = itemhorario.getPrincipalOAsistente();
            datos[fila][14] = itemhorario.getIdDocente().getIdTipoContrato().getTipoContrato();
            datos[fila][15] = itemhorario.getCapMax();
            datos[fila][16] = itemhorario.getCapMin();
            datos[fila][17] = itemhorario.getDia1().getDia();
            datos[fila][18] = datos[fila][2] + "-" + datos[fila][1];
            datos[fila][19] = datos[fila][6] + "-" + datos[fila][17];
            datos[fila][20] = datos[fila][7] + "-" + datos[fila][8];
            datos[fila][21] = datos[fila][9] + "-" + datos[fila][10];
            datos[fila][22] = itemhorario.getGrupo().getAsignatura().getTipologias();
            datos[fila][23] = "";
            datos[fila][24] = "";
            fila++;
        }
        tabla = new JTable(datos, Progacademica.nombreColumnas);
        tabla.setAutoCreateRowSorter(true);
        DefaultRowSorter sorter = ((DefaultRowSorter) tabla.getRowSorter());
        ArrayList list = new ArrayList();
        list.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
        super.processEvent();
    }
}
