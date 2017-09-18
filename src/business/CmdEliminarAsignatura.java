/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentAsignaturas;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Asignaturaenplanestudio;
import dataDB.Grupo;
import dataDB.Itemhorario;
import dataDB.Progacademica;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Carlos
 */
public class CmdEliminarAsignatura implements CommandInterface {

    private VentAsignaturas ventana;
    private boolean ocultar;
    private JButton boton;
    private ListaAsignaturas lista;
    private JMenuItem jMenuItem;

    public CmdEliminarAsignatura(VentAsignaturas ventana, JButton boton) {
        super(); 
        this.ventana = ventana;
        this.boton = boton;
    }
    
    public CmdEliminarAsignatura(ListaAsignaturas lista, JMenuItem jMenuItem) {
        this.lista = lista;
        this.jMenuItem = jMenuItem;
    }

    @Override
    public void processEvent() {
        int codAsignatura = 0;
        if(ventana != null) {
            codAsignatura = ventana.getSeleccionado();
            ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        } else {
            codAsignatura = lista.getCodigoAsigSeleccionada();
            ocultar = jMenuItem.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        }
        
        if (codAsignatura != 0) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Asignatura asignatura = (Asignatura) entityM.createNamedQuery("Asignatura.findByCodigoAsig").setParameter("codigoAsig", codAsignatura).getSingleResult();
            List<Integer> idProgsDondeEstaAsig = entityM.createNamedQuery("Itemhorario.findIdProgByIdAsignatura").setParameter("idAsignatura", asignatura.getIdAsignatura()).setParameter("oculto", !ocultar).getResultList();
            List<Grupo> grupos = entityM.createNamedQuery("Grupo.findByIdAsignatura").setParameter("idAsignatura", asignatura.getIdAsignatura()).setParameter("oculto", !ocultar).getResultList();
            List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findByIdAsignatura").setParameter("idAsignatura", asignatura.getIdAsignatura()).setParameter("oculto", !ocultar).getResultList();
            List<Asignaturaenplanestudio> asigEnPlan = entityM.createNamedQuery("Asignaturaenplanestudio.findByIdAsignatura").setParameter("idAsignatura", asignatura.getIdAsignatura()).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String idProgs = "¿Está seguro que desea ocultar la asignatura: "
                        + asignatura.getNombreAsig() + "?.";
                if (idProgsDondeEstaAsig.size() >= 1) {
                    idProgs = idProgs + " Si la oculta, se afectarán las " + System.lineSeparator()
                            + "siguientes programaciones académicas: ";
                    for (Integer idProg : idProgsDondeEstaAsig) {
                        idProgs += entityM.createNamedQuery("Progacademica.findNameByIdProg").setParameter("idProg", idProg).getSingleResult() + ", ";
                    }
                    idProgs = idProgs.substring(0, idProgs.length() - 2);
                    idProgs = idProgs + "." + System.lineSeparator() + "Se ocultarán los siguientes horarios: " + System.lineSeparator();
                    for (Itemhorario item : items) {
                        idProgs = idProgs + System.lineSeparator() + " - " + ((Progacademica) (entityM.createNamedQuery("Progacademica.findByIdProg").setParameter("idProg", item.getItemhorarioPK().getIdProg()).getSingleResult())).getNumeroProg()
                                + ", " + item.getDia1().getDia() + ", " + item.getItemhorarioPK().getHoraInicio() + ":00, " + "edificio: " + item.getIdSalon().getIdEdificio().getNumeroEd() + ", salón: " + item.getIdSalon().getNumeroSalon() + ", "
                                + ((Asignatura) (entityM.createNamedQuery("Asignatura.findByIdAsignatura").setParameter("idAsignatura", item.getItemhorarioPK().getIdAsignatura()).getSingleResult())).getNombreAsig()
                                + ", grupo " + item.getGrupo().getGrupoPK().getNumeroGrupo() + ".";
                    }
                    JTextArea jtxtArea = new JTextArea(idProgs, 20, 100);
                    jtxtArea.setEditable(false);
                    info = new JScrollPane(jtxtArea);
                } else {
                    info = idProgs;
                }
            } else {
                info = "¿Está seguro que desea desocultar esta asignatura?";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                entityM.getTransaction().begin();
                for (Itemhorario item : items) {
                    //entityM.remove(item);
                    if (!ocultar) {
                        if (!item.getIdDocente().getOculto() && !item.getIdSalon().getOculto() && !item.getGrupo().getProgacademica().getOculto()) {
                            item.setOculto(ocultar);
                        }
                    } else {
                        item.setOculto(ocultar);
                    }

                }
                for (Grupo grupo : grupos) {
                    //entityM.remove(grupo);
                    grupo.setOculto(ocultar);
                }
                for (Asignaturaenplanestudio asigPlan : asigEnPlan) {
                    asigPlan.setOculto(ocultar);
                    //entityM.remove(asigPlan);
                }
                //entityM.remove(asignatura);
                asignatura.setOculto(ocultar);
                entityM.getTransaction().commit();
                if (ventana != null) {
                    ventana.removerFila();
                }
                ComboBoxAsignaturas.actualizar();
                ListaSemestre.actualizar();
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna asignatura para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
