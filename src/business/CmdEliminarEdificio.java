/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentEdificios;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Edificio;
import dataDB.Itemhorario;
import dataDB.Progacademica;
import dataDB.Salon;
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
public class CmdEliminarEdificio implements CommandInterface {

    private VentEdificios ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarEdificio(VentEdificios ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        String numeroEdificio = ventana.getSeleccionado();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (numeroEdificio != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Edificio edificio = (Edificio) entityM.createNamedQuery("Edificio.findByNumeroEd").setParameter("numeroEd", numeroEdificio).getSingleResult();
            //List<Integer> idProgsDondeEstaEdi = entityM.createNamedQuery("Itemhorario.findIdProgByEdi").setParameter("idEdificio", edificio).getResultList();
            List<Integer> idProgsDondeEstaEdi = entityM.createNamedQuery("Itemhorario.findIdProgByEdi").setParameter("idEdificio", edificio).setParameter("oculto", !ocultar).getResultList();
            //List<Salon> salonesEnEdificio = entityM.createNamedQuery("Salon.findByIdEdificio").setParameter("idEdificio", edificio).getResultList(); 
            //List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findItemsByEdi").setParameter("idEdificio", edificio).getResultList();
            List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findItemsByEdi").setParameter("idEdificio", edificio).setParameter("oculto", !ocultar).getResultList();
            List<Salon> salonesEnEdificio = entityM.createNamedQuery("Salon.findByIdEdificio").setParameter("idEdificio", edificio).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String idProgs = "¿Está seguro que desea ocultar el edificio: "
                        + numeroEdificio + "?.";
                if (idProgsDondeEstaEdi.size() >= 1) {
                    idProgs = idProgs + " Si lo oculta, se afectarán las " + System.lineSeparator()
                            + "siguientes programaciones académicas: ";
                    for (Integer idProg : idProgsDondeEstaEdi) {
                        idProgs += entityM.createNamedQuery("Progacademica.findNameByIdProg").setParameter("idProg", idProg).getSingleResult() + ", ";
                    }
                    idProgs = idProgs.substring(0, idProgs.length() - 2);
                    idProgs = idProgs + "." + System.lineSeparator() + "Se ocultarán los siguientes horarios: " + System.lineSeparator();
                    for (Itemhorario item : items) {
                        idProgs = idProgs + System.lineSeparator() + " - " + ((Progacademica) (entityM.createNamedQuery("Progacademica.findByIdProg").setParameter("idProg", item.getItemhorarioPK().getIdProg()).getSingleResult())).getNumeroProg()
                                + ", " + item.getDia1().getDia() + ", " + item.getItemhorarioPK().getHoraInicio() + ":00, " + "edificio: " + numeroEdificio + ", salón: " + item.getIdSalon().getNumeroSalon() + ", "
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
                info = "¿Está seguro que desea desocultar este edificio?";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                entityM.getTransaction().begin();
                for (Salon salon : salonesEnEdificio) {
                    //entityM.remove(salon);
                    salon.setOculto(ocultar);
                }
                for (Itemhorario item : items) {
                    //entityM.remove(item);
                    if (!ocultar) {
                        if (!item.getIdDocente().getOculto() && !item.getIdSalon().getOculto() && !item.getGrupo().getProgacademica().getOculto() && !item.getGrupo().getAsignatura().getOculto()) {
                            item.setOculto(ocultar);
                        }
                    } else {
                        item.setOculto(ocultar);
                    }
                }
                //entityM.remove(edificio);
                edificio.setOculto(ocultar);
                entityM.getTransaction().commit();
                ventana.removerFila();
                ComboBoxEdificio.actualizar();
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún edificio para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
