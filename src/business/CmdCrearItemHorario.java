/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import generalities.Errores;
import data.Manejador;
import dataDB.Dia;
import dataDB.Docente;
import dataDB.Grupo;
import dataDB.Horainicio;
import dataDB.Itemhorario;
import dataDB.Progacademica;
import dataDB.Salon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearItemHorario implements CommandInterface {

    private VentProgAca ventana;
    private String mensajeConfirmacion;
    private JButton boton;

    public CmdCrearItemHorario(VentProgAca ventana, JButton boton) {
        this.ventana = ventana;
        this.boton = boton;
    }
    
    

    @Override
    public void processEvent() {
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        try {
            int limiteMax = ventana.getLimiteMax();
            int cuotas = ventana.getCuotas();
            int capMax = ventana.getCapMax();
            int capMin = ventana.getCapMin();
            String principalOAsistente = ventana.getOpcPrincipal() ? "P" : "A";
            Docente docente = ventana.getDocente();
            Salon salon = ventana.getSalon();
            Itemhorario itemHorario = null;
            if (boton.getActionCommand().equalsIgnoreCase(Errores.CREAR)) {
                int idAsignatura = ventana.getAsignatura().getIdAsignatura();
                Grupo grupo = entity.find(Grupo.class, ventana.getGrupoPK());
                String nombreDia = ventana.getDia();
                int numeroHoraInicio = ventana.getHoraInicio();
                Dia dia = (Dia) entity.createNamedQuery("Dia.findByDia").setParameter("dia", nombreDia).getSingleResult();
                Horainicio horaInicio = (Horainicio) entity.createNamedQuery("Horainicio.findByHoraInicio").setParameter("horaInicio", numeroHoraInicio).getSingleResult();
                int numeroGrupo = grupo.getGrupoPK().getNumeroGrupo();
                int idProg = ((Progacademica) entity.createNamedQuery("Progacademica.findByNumeroProg").setParameter("numeroProg", ventana.getNumeroProg()).getSingleResult()).getIdProg();
                itemHorario = new Itemhorario(nombreDia, numeroHoraInicio, idProg, numeroGrupo, idAsignatura);
                itemHorario.setGrupo(grupo);
                itemHorario.setIdDocente(docente);
                itemHorario.setIdSalon(salon);
                itemHorario.setDia1(dia);
                itemHorario.setHorainicio(horaInicio);
                mensajeConfirmacion = "Horario creado exitosamente.";
            } else if (boton.getActionCommand().equalsIgnoreCase(Errores.MODIFICAR)) {
                itemHorario = ventana.getItemHorarioSeleccionado();
                itemHorario.setIdDocente(docente);
                itemHorario.setIdSalon(salon);
                mensajeConfirmacion = "Horario modificado exitosamente.";
            }
            if (!boton.getActionCommand().equalsIgnoreCase(Errores.ELIMINAR)) {
                itemHorario.setLimiteMax(limiteMax);
                itemHorario.setCuotas(cuotas);
                itemHorario.setCapMax(capMax);
                itemHorario.setCapMin(capMin);
                itemHorario.setPrincipalOAsistente(principalOAsistente);
                entity.getTransaction().begin();
                entity.persist(itemHorario);
                entity.getTransaction().commit();
            } else {
                ventana.getTablaHorarioAsignatura().borrarItemHorario();
                mensajeConfirmacion = "Horario eliminado exitosamente.";
            }
            JOptionPane.showMessageDialog(null, mensajeConfirmacion, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            ventana.actualizarHorarios();
            boton.setActionCommand(Errores.CREAR);
            boton.setText(Errores.CREAR);
            boton.setToolTipText("Crear item de horario (Alt+C)");
            boton.setIcon(new ImageIcon(VentProgAca.class.getResource("/GUI/iconos/progAca1.png")));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Asegúrese que los campos: límite máximo, cuotas, capacidad máxima y capacidad mínima, contengan únicamente valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Asegúrese que seleccionó todos los campos. " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoResultException ex) {
            JOptionPane.showMessageDialog(null, "Seleccione un horario para asignárselo al grupo de la asignatura. ", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un grupo de la asignatura. ", "Error", JOptionPane.ERROR_MESSAGE);
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
