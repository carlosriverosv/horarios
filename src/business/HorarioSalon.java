/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import static business.Horario.HORA_INICIAL;
import data.Manejador;
import dataDB.Itemhorario;
import dataDB.Salon;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class HorarioSalon extends Horario {

    private Salon salon;

    public HorarioSalon(int idProg) {
        super(idProg);
        cantidadItemsHorario = new int[horas.length][model.getColumnCount()];
    }

    @Override
    public void actualizarDatos(Observable subject) {
        if (!subject.getClass().getName().equalsIgnoreCase("business.HorarioGrupoAsignatura")) {
            borrarContenido();
            ComboBoxSalon comboBoxSalon = (ComboBoxSalon) subject;
            salon = (Salon) comboBoxSalon.getSelectedItem();  
        } else {
            Horario horario = (Horario) subject;
            changeSelection(horario.getSelectedRow(), horario.getSelectedColumn(), false, false);
        }
        if (salon != null) {
            getModeloHorarioSalon(salon);
        }
    }

    public DefaultTableModel getModeloHorarioSalon(Salon salon) {
        borrarContenido();
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        //List<Itemhorario> itemsHorario = entity.createQuery("SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.idSalon =:idSalon").setParameter("idProg", getIdProg()).setParameter("idSalon", salon).getResultList();
        List<Itemhorario> itemsHorario = entity.createQuery("SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.idSalon =:idSalon AND i.oculto=:oculto").setParameter("idProg", getIdProg()).setParameter("idSalon", salon).setParameter("oculto", false).getResultList();
        for (Itemhorario itemhorario : itemsHorario) {
            setDia(getNumeroDia(itemhorario.getDia1().getDia()));
            setHora(itemhorario.getHorainicio().getHoraInicio());
            int numeroGrupo = itemhorario.getGrupo().getGrupoPK().getNumeroGrupo();
            String nombreAsignatura = itemhorario.getGrupo().getAsignatura().getNombreAsig();
            String docente = itemhorario.getIdDocente().getNombreDocente();
            String informacion = getModelo().getValueAt(getHora() - HORA_INICIAL, getDia()) + SEPARADOR + nombreAsignatura + " - Grupo " + numeroGrupo + " - " + docente + SEPARADOR;
            getModelo().setValueAt(informacion, getHora() - HORA_INICIAL, getDia());
            cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] = cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] + 1;
        }
        return getModelo();
    }
}
