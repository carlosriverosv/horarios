/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentDocentes;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Docente;
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
public class CmdEliminarDocente implements CommandInterface {

    private VentDocentes ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarDocente(VentDocentes ventana, JButton boton) {
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        int documentoDocente = ventana.getSeleccionado();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (documentoDocente != 0) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Docente docente = (Docente) entityM.createNamedQuery("Docente.findByDocumentoDoc").setParameter("documentoDoc", documentoDocente).getSingleResult();
            //List<Integer> idProgsDondeEstaDocente = entityM.createNamedQuery("Itemhorario.findIdProgByDocente").setParameter("documentoDoc", documentoDocente).getResultList();
            List<Integer> idProgsDondeEstaDocente = entityM.createNamedQuery("Itemhorario.findIdProgByDocente").setParameter("documentoDoc", documentoDocente).setParameter("oculto", !ocultar).getResultList();
            //List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findItemsByDocente").setParameter("documentoDoc", documentoDocente).getResultList();
            List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findItemsByDocente").setParameter("documentoDoc", documentoDocente).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String idProgs = "¿Está seguro que desea ocultar el Docente: "
                        + docente.getNombreDocente() + "?.";
                if (idProgsDondeEstaDocente.size() >= 1) {
                    idProgs = idProgs + " Si lo oculta," + System.lineSeparator()
                            + "se afectarán las siguientes programaciones académicas: ";
                    for (Integer idProg : idProgsDondeEstaDocente) {
                        idProgs += entityM.createNamedQuery("Progacademica.findNameByIdProg").setParameter("idProg", idProg).getSingleResult() + ", ";
                    }
                    idProgs = idProgs.substring(0, idProgs.length() - 2);
                    idProgs = idProgs + "." + System.lineSeparator() + "Se ocultarán los siguientes horarios: " + System.lineSeparator();
                    for (Itemhorario item : items) {
                        idProgs = idProgs + System.lineSeparator() + " - " + ((Progacademica) (entityM.createNamedQuery("Progacademica.findByIdProg").setParameter("idProg", item.getItemhorarioPK().getIdProg()).getSingleResult())).getNumeroProg()
                                + ", " + item.getDia1().getDia() + ", " + item.getItemhorarioPK().getHoraInicio() + ":00 "
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
                info = "¿Está seguro que desea desocultar este Docente?";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                entityM.getTransaction().begin();
                for (Itemhorario item : items) {
                    //entityM.remove(item);
                    if (!ocultar) {
                        if (!item.getGrupo().getAsignatura().getOculto() && !item.getGrupo().getProgacademica().getOculto() && !item.getIdSalon().getOculto()) {
                            item.setOculto(ocultar);
                        }
                    } else {
                        item.setOculto(ocultar);
                    }
                }
                //entityM.remove(docente);
                docente.setOculto(ocultar);
                entityM.getTransaction().commit();
                if (ventana != null) {
                    ventana.removerFila();
                }
                ComboBoxDocentes.actualizar();
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún Docente para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
