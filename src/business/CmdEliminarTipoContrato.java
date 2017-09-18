/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentModTipoContrato;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Docente;
import dataDB.Tipocontrato;
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
public class CmdEliminarTipoContrato implements CommandInterface {

    private VentModTipoContrato ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarTipoContrato(VentModTipoContrato ventana, JButton boton) {
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        Tipocontrato contrato = ventana.getTipoContrato();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (contrato != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Tipocontrato tipo = (Tipocontrato) entityM.createNamedQuery("Tipocontrato.findByIdTipoContrato").setParameter("idTipoContrato", ventana.getTipoContrato().getIdTipoContrato()).getSingleResult();
            List<Docente> docentes = entityM.createNamedQuery("Docente.findByIdTipoContrato").setParameter("idTipoContrato", tipo).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String mensaje = "¿Está seguro que desea ocultar el tipo de contrato: "
                        + tipo.getTipoContrato() + "?.";
                if (docentes.size() >= 1) {
                    mensaje = mensaje + " Para ocultarlo, primero debe modificar" + System.lineSeparator()
                            + "el tipo de contrato de los siguientes Docentes: " + System.lineSeparator() + System.lineSeparator();
                    for (Docente docente : docentes) {
                        mensaje += "- " + docente.getDocumentoDoc() + ", " + docente.getNombreDocente() + System.lineSeparator();
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
                info = "¿Está seguro que desea desocultar este tipo de contrato?";
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
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún tipo de contrato para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
