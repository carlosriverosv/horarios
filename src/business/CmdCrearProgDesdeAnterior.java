/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentCrearProgDesdeAnterior;
import data.Manejador;
import dataDB.Grupo;
import dataDB.Itemhorario;
import dataDB.Progacademica;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearProgDesdeAnterior implements CommandInterface {

    private VentCrearProgDesdeAnterior ventana;

    public CmdCrearProgDesdeAnterior(VentCrearProgDesdeAnterior ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        EntityManager entityM = null;
        try {
            String nombreNuevaProg = ventana.getNombreNuevaProgAca();
            if (!nombreNuevaProg.trim().isEmpty()) {
                Progacademica progAca = ventana.getProgAcaAnterior();
                entityM = Manejador.getManejador().gentEntityManager();
                entityM.getTransaction().begin();
                List<Itemhorario> itemsHorario = entityM.createNamedQuery("Itemhorario.findByIdProg").setParameter("idProg", progAca.getIdProg()).setParameter("oculto", false).getResultList();
                List<Grupo> grupos = entityM.createNamedQuery("Grupo.findByIdProg").setParameter("idProg", progAca.getIdProg()).setParameter("oculto", false).getResultList();
                Progacademica nuevaProg = new Progacademica(null, nombreNuevaProg);
                entityM.persist(nuevaProg);
                entityM.getTransaction().commit();
                entityM.getTransaction().begin();
                for (Grupo grupo : grupos) {
                    Grupo grup = new Grupo(grupo.getGrupoPK().getNumeroGrupo(), nuevaProg.getIdProg(), grupo.getGrupoPK().getIdAsignatura());
                    grup.setInscritos(0);
                    entityM.persist(grup);
                }
                entityM.getTransaction().commit();
                entityM.getTransaction().begin();
                for (Itemhorario itemhorario : itemsHorario) {
                    Itemhorario item = new Itemhorario(itemhorario.getDia1().getDia(), itemhorario.getHorainicio().getHoraInicio(),
                            nuevaProg.getIdProg(), itemhorario.getGrupo().getGrupoPK().getNumeroGrupo(),
                            itemhorario.getGrupo().getAsignatura().getIdAsignatura());
                    item.setIdDocente(itemhorario.getIdDocente());
                    item.setIdSalon(itemhorario.getIdSalon());
                    item.setGrupo(itemhorario.getGrupo());
                    item.setDia1(itemhorario.getDia1());
                    item.setHorainicio(itemhorario.getHorainicio());
                    item.setLimiteMax(itemhorario.getLimiteMax());
                    item.setCapMax(itemhorario.getCapMax());
                    item.setCapMin(itemhorario.getCapMin());
                    item.setCuotas(itemhorario.getCuotas());
                    item.setPrincipalOAsistente(itemhorario.getPrincipalOAsistente());
                    entityM.persist(item);
                }
                entityM.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Programación académica creada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/progAca.png")));
                ventana.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Debe introducir un nombre para la nueva programación académica", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "No existe una programación académica anterior.", "Error", JOptionPane.ERROR_MESSAGE);
            entityM.getTransaction().rollback();
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
