/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentNivelesEstudio;
import GUI.VentTabla;
import data.Manejador;
import dataDB.Asignatura;
import dataDB.Nivelestudio;
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
public class CmdEliminarNivelEstudio implements CommandInterface {

    private VentNivelesEstudio ventana;
    private boolean ocultar;
    private JButton boton;

    public CmdEliminarNivelEstudio(VentNivelesEstudio ventana, JButton boton) {
        this.ventana = ventana;
        this.boton = boton;
    }

    @Override
    public void processEvent() {
        Nivelestudio nivelEstudio = ventana.getNivelEstudio();
        ocultar = boton.getActionCommand().equalsIgnoreCase(VentTabla.OCULTAR);
        if (nivelEstudio != null) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            Nivelestudio nivel = (Nivelestudio) entityM.createNamedQuery("Nivelestudio.findByIdNivelEstudio").setParameter("idNivelEstudio", nivelEstudio.getIdNivelEstudio()).getSingleResult();
            List<Asignatura> asignaturas = entityM.createNamedQuery("Asignatura.findByIdNivelEstudio").setParameter("idNivelEstudio", nivel).setParameter("oculto", !ocultar).getResultList();
            Object info;
            if (ocultar) {
                String mensaje = "¿Está seguro que desea ocultar el nivel de estudio: "
                        + nivel.getNivelEstudio() + "?.";
                if (asignaturas.size() >= 1) {
                    mensaje = mensaje + " Para ocultarlo, primero debe modificar" + System.lineSeparator()
                            + "el nivel de estudio de las siguientes asignaturas: " + System.lineSeparator() + System.lineSeparator();
                    for (Asignatura asignatura : asignaturas) {
                        mensaje += "- " + asignatura.getNombreAsig() + System.lineSeparator();
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
                        //entityM.remove(nivel);
                        nivel.setOculto(ocultar);
                        entityM.getTransaction().commit();
                        ventana.removerFila();
                    }
                }
            } else {
                info = "¿Está seguro que desea desocultar este nivel de estudio?";
                int respuesta = JOptionPane.showConfirmDialog(null, info, "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    entityM.getTransaction().begin();
                    //entityM.remove(nivel);
                    nivel.setOculto(ocultar);
                    entityM.getTransaction().commit();
                    ventana.removerFila();
                }
            }
        } else {
            String msj = ocultar ? "ocultar. " : "desocultar.";
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún nivel de estudio para " + msj, "Error", JOptionPane.ERROR_MESSAGE);
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
