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
public class CmdAgregarGrupo implements CommandInterface {

    private ListaGrupos lista;

    public CmdAgregarGrupo(ListaGrupos lista) {
        this.lista = lista;
    }

    @Override
    public void processEvent() {
        try {
            Asignatura asignatura = lista.getAsignatura();
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Progacademica progAca = (Progacademica) entityM.createNamedQuery("Progacademica.findByNumeroProg").setParameter("numeroProg", lista.getNumProgAcademica()).getSingleResult();
            int numGrupo = 1;
            int i = 0;
            //Se encarga de buscar el número de grupo faltante. Mientras no se encuentra
            //un número de grupo, la variable i se incrementa hasta el número de grupos
            //que haya en ese momento y si definitivamente no se encuentra,
            //se terminará el ciclo, quedando almacenado en la variable numGrupo este valor.
            while (i < lista.model.getSize()) {
                if (((GrupoPK) lista.model.getElementAt(i)).getNumeroGrupo() != numGrupo) {
                    i++;
                } else {
                    numGrupo++;
                    i = 0;
                }
            }
            Grupo grupo = new Grupo(numGrupo, progAca.getIdProg(), asignatura.getIdAsignatura());
            grupo.setAsignatura(asignatura);
            grupo.setInscritos(0);
            entityM.getTransaction().begin();
            entityM.persist(grupo);
            entityM.getTransaction().commit();
            lista.actualizarDatos();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una asignatura para agregar un grupo. ", "Error", JOptionPane.ERROR_MESSAGE);
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
