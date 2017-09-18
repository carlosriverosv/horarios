/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import dataDB.Asignatura;
import dataDB.Grupo;
import dataDB.GrupoPK;
import dataDB.Progacademica;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdEliminarGrupo implements CommandInterface{

    private ListaGrupos lista;

    public CmdEliminarGrupo(ListaGrupos lista) {
        this.lista = lista;
    }

    @Override
    public void processEvent() {
        GrupoPK grupopk = (GrupoPK) lista.getSelectedValue();
        if (grupopk == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un grupo para eliminarlo. ", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este grupo? Si lo elimina, su horarios asignados también se eliminarán. ", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                EntityManager entityM = Manejador.getManejador().gentEntityManager();
                Progacademica progAca = (Progacademica) entityM.createNamedQuery("Progacademica.findByNumeroProg").setParameter("numeroProg", lista.getNumProgAcademica()).getSingleResult();
                Grupo grupo = entityM.find(Grupo.class, grupopk);
                entityM.getTransaction().begin();
                Asignatura asignatura = lista.getAsignatura();
                entityM.createQuery("DELETE FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.grupo =:numeroGrupo AND i.grupo.asignatura =:asignatura").setParameter("idProg", progAca.getIdProg()).setParameter("numeroGrupo", grupo).setParameter("asignatura", asignatura).executeUpdate();
                entityM.remove(grupo);
                entityM.getTransaction().commit();
                lista.notifyObservers();
                lista.actualizarDatos();
            }
        }
    }

    @Override
    public void processEvent(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
