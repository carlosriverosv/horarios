/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentTabla;
import GUI.VentTipologias;
import data.Manejador;
import dataDB.Asignaturaenplanestudio;
import dataDB.Tipologia;
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
public class CmdEliminarTipologia implements CommandInterface {

    private VentTipologias ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarTipologia(VentTipologias ventana, JButton boton) {
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        Tipologia tipologia = ventana.getTipologia();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (tipologia != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Tipologia tipo = (Tipologia) entityM.createNamedQuery("Tipologia.findByIdTipologia").setParameter("idTipologia", ventana.getTipologia().getIdTipologia()).getSingleResult();
            List<Asignaturaenplanestudio> asigEnPlan = entityM.createNamedQuery("Asignaturaenplanestudio.findByIdTipologia").setParameter("idTipologia", tipo).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String mensaje = "¿Está seguro que desea ocultar la tipología: "
                        + tipo.getTipologia() + "?.";
                if (asigEnPlan.size() >= 1) {
                    mensaje = mensaje + " Para ocultarla, primero debe modificar" + System.lineSeparator()
                            + "la tipología de las siguientes asignaturas: " + System.lineSeparator() + System.lineSeparator();
                    for (Asignaturaenplanestudio asig : asigEnPlan) {
                        mensaje += "- " + asig.getAsignatura().getNombreAsig() + " en el plan de estudios " + asig.getPlanestudio().getCodigoPlan() + " - " + asig.getPlanestudio().getNombrePlan() + System.lineSeparator();
                    }
                    JTextArea jtxtArea = new JTextArea(mensaje, 20, 100);
                    jtxtArea.setEditable(false);
                    info = new JScrollPane(jtxtArea);
                    JOptionPane.showMessageDialog(null, info, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    info = mensaje;
                    int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        entityM.getTransaction().begin();
                        //entityM.remove(tipo);
                        tipo.setOculto(ocultar);
                        entityM.getTransaction().commit();
                        ventana.removerFila();
                    }
                }
            } else {
                info = "¿Está seguro que desea desocultar esta tipología?";
                int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    entityM.getTransaction().begin();
                    //entityM.remove(tipo);
                    tipo.setOculto(ocultar);
                    entityM.getTransaction().commit();
                    ventana.removerFila();
                }
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna tipología para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
