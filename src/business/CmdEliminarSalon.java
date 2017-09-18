/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentSalones;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
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
public class CmdEliminarSalon implements CommandInterface {

    private VentSalones ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarSalon(VentSalones ventana, JButton boton) {
        super();
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        Object[] numSalonEdificio = ventana.getSeleccionado();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (numSalonEdificio != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Salon salon = (Salon) entityM.createQuery("SELECT s FROM Salon s WHERE s.numeroSalon = '" + numSalonEdificio[0] + "' AND s.idEdificio.numeroEd = '" + numSalonEdificio[1] + "'").getSingleResult();
            //List<Integer> idProgsDondeEstaSalon = entityM.createNamedQuery("Itemhorario.findIdProgBySalon").setParameter("idSalon", salon).setParameter("idEdificio", salon.getIdEdificio()).getResultList();
            List<Integer> idProgsDondeEstaSalon = entityM.createNamedQuery("Itemhorario.findIdProgBySalon").setParameter("idSalon", salon).setParameter("idEdificio", salon.getIdEdificio()).setParameter("oculto", !ocultar).getResultList();
            //List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findItemsBySalon").setParameter("idSalon", salon).setParameter("idEdificio", salon.getIdEdificio()).getResultList();
            List<Itemhorario> items = entityM.createNamedQuery("Itemhorario.findItemsBySalon").setParameter("idSalon", salon).setParameter("idEdificio", salon.getIdEdificio()).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String idProgs = "¿Está seguro que desea ocultar el salón: "
                        + numSalonEdificio[0] + " del edificio " + numSalonEdificio[1] + "?.";
                if (idProgsDondeEstaSalon.size() >= 1) {
                    idProgs = idProgs + " Si lo oculta, se afectarán las " + System.lineSeparator()
                            + "siguientes programaciones académicas: ";
                    for (Integer idProg : idProgsDondeEstaSalon) {
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
                info = "¿Está seguro que desea desocultar este salón?";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                if (!ocultar && salon.getIdEdificio().getOculto()) {
                    JOptionPane.showMessageDialog(null, "No es posible desocultar este salón porque el edificio al que pertenece se encuentra oculto.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    entityM.getTransaction().begin();
                    for (Itemhorario item : items) {
                        //entityM.remove(item);
                        if (!ocultar) {
                            if (!item.getIdDocente().getOculto() && !item.getGrupo().getAsignatura().getOculto() && !item.getGrupo().getProgacademica().getOculto()) {
                                item.setOculto(ocultar);
                            }
                        } else {
                            item.setOculto(ocultar);
                        }
                    }
                    //entityM.remove(salon);
                    salon.setOculto(ocultar);
                    entityM.getTransaction().commit();
                    ventana.removerFila();
                    ComboBoxEdificio.actualizar();
                }
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún salón para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
