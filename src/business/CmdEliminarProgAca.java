/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentAbrirProgAnterior;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Grupo;
import dataDB.Itemhorario;
import dataDB.Progacademica;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Carlos
 */
public class CmdEliminarProgAca implements CommandInterface{

    private VentAbrirProgAnterior ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarProgAca(VentAbrirProgAnterior ventana, JButton boton) {
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        Progacademica progra = ventana.getProgAcademica();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (progra != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Progacademica prog = (Progacademica) entityM.createNamedQuery("Progacademica.findByIdProg").setParameter("idProg", progra.getIdProg()).getSingleResult();;
            List<Grupo> grupos = entityM.createNamedQuery("Grupo.findByIdProg").setParameter("idProg", prog.getIdProg()).setParameter("oculto", !ocultar).getResultList();
            List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findByIdProg").setParameter("idProg", prog.getIdProg()).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String mensaje = "¿Está seguro que desea ocultar la programación académica: "
                        + prog.getNumeroProg() + "?.";
                if (items.size() >= 1) {
                    mensaje = mensaje + " Si lo hace, se ocultarán los " + System.lineSeparator()
                            + "siguientes horarios: ";
                    for (Itemhorario item : items) {
                        mensaje = mensaje + System.lineSeparator() + " - " + ((Progacademica) (entityM.createNamedQuery("Progacademica.findByIdProg").setParameter("idProg", item.getItemhorarioPK().getIdProg()).getSingleResult())).getNumeroProg()
                                + ", " + item.getDia1().getDia() + ", " + item.getItemhorarioPK().getHoraInicio() + ":00 "
                                + ((Asignatura) (entityM.createNamedQuery("Asignatura.findByIdAsignatura").setParameter("idAsignatura", item.getItemhorarioPK().getIdAsignatura()).getSingleResult())).getNombreAsig()
                                + ", grupo " + item.getGrupo().getGrupoPK().getNumeroGrupo() + ".";
                    }
                    JTextArea jtxtArea = new JTextArea(mensaje, 20, 100);
                    jtxtArea.setEditable(false);
                    info = new JScrollPane(jtxtArea);
                } else {
                    info = mensaje;
                }
            } else {
                info = "¿Está seguro que desea desocultar esta programación académica?";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                entityM.getTransaction().begin();
                for (Itemhorario item : items) {
                    //entityM.remove(item);
                    if(!ocultar){
                        if(!item.getIdDocente().getOculto() && !item.getIdSalon().getOculto() && !item.getGrupo().getAsignatura().getOculto())
                            item.setOculto(ocultar);
                    } else {
                        item.setOculto(ocultar);
                    }    
                }
                for (Grupo grupo : grupos) {
                    //entityM.remove(grupo);
                    grupo.setOculto(ocultar);
                }
                //entityM.remove(prog);
                prog.setOculto(ocultar);
                entityM.getTransaction().commit();
                ventana.removerFila();
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna programación académica para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
