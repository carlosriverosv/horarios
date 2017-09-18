/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import static business.Horario.HORA_INICIAL;
import static business.Horario.horas;
import data.Manejador;
import dataDB.Docente;
import dataDB.Itemhorario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class HorarioDocente extends Horario {

    private Docente docente;

    public HorarioDocente(int idProg) {
        super(idProg);
        cantidadItemsHorario = new int[horas.length][model.getColumnCount()];
    }

    @Override
    public void actualizarDatos(Observable subject) {
        if (!subject.getClass().getName().equalsIgnoreCase("business.HorarioGrupoAsignatura")) {
            ComboBoxDocentes comboBoxDocente = (ComboBoxDocentes) subject;
            docente = (Docente) comboBoxDocente.getSelectedItem();
        } else {
            Horario horario = (Horario) subject;
            changeSelection(horario.getSelectedRow(), horario.getSelectedColumn(), false, false);
        }
        if (docente != null) {
                getModeloHorarioDocente(docente);
            }
    }

    public DefaultTableModel getModeloHorarioDocente(Docente docente) {
        borrarContenido();
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        //List<Itemhorario> itemsHorario = entity.createQuery("SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.idDocente =:idDocente").setParameter("idProg", getIdProg()).setParameter("idDocente", docente).getResultList();
        List<Itemhorario> itemsHorario = entity.createQuery("SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.idDocente =:idDocente AND i.oculto =:oculto").setParameter("idProg", getIdProg()).setParameter("idDocente", docente).setParameter("oculto", false).getResultList();
        for (Itemhorario itemhorario : itemsHorario) {
            setDia(getNumeroDia(itemhorario.getDia1().getDia()));
            setHora(itemhorario.getHorainicio().getHoraInicio());
            int numeroGrupo = itemhorario.getGrupo().getGrupoPK().getNumeroGrupo();
            String asignatura = itemhorario.getGrupo().getAsignatura().getNombreAsig();
            String edificio = String.valueOf(itemhorario.getIdSalon().getIdEdificio().getNumeroEd());
            String salon = String.valueOf(itemhorario.getIdSalon().getNumeroSalon());
            String informacion = getModelo().getValueAt(getHora() - HORA_INICIAL, getDia()) + SEPARADOR + asignatura + " - Grupo " + numeroGrupo + " Ed: " + edificio + " - " + salon + SEPARADOR;
            getModelo().setValueAt(informacion, getHora() - HORA_INICIAL, getDia());
            cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] = cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] + 1;
        }
        return getModelo();
    }
}
