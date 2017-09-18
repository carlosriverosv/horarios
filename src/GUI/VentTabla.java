/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.ComboBoxEliminar;
import business.JChkVerOcultos;
import business.Tabla;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Carlos
 */
public abstract class VentTabla extends JFrame {

    protected JButton btnEliminar;
    protected JButton btnModificar;
    protected Tabla tabla;
    protected JChkVerOcultos jChkVerOcultos;
    protected ComboBoxEliminar comboBox;
    public static final String OCULTAR = "Ocultar";
    public static final String NO_OCULTAR = "No ocultar";
    protected boolean editable, eliminable;

    public VentTabla(boolean editable, boolean eliminable) {

        this.editable = editable;
        this.eliminable = eliminable;
    }

    public void mostrarOcultos() {
        btnEliminar.setText(VentTabla.NO_OCULTAR);
        btnEliminar.setActionCommand(VentTabla.NO_OCULTAR);
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/modificarPeque.png")));
        btnModificar.setVisible(false);
        if (tabla != null) {
            tabla.completarTabla(true);
        } else {
            comboBox.completar(true);
        }
        inicializarBotones(editable, eliminable);
    }

    public void mostrarNoOcultos() {
        btnEliminar.setText(VentTabla.OCULTAR);
        btnEliminar.setActionCommand(VentTabla.OCULTAR);
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png")));
        btnModificar.setVisible(true);
        if (tabla != null) {
            tabla.completarTabla(false);
        } else {
            comboBox.completar(false);
        }
        inicializarBotones(editable, eliminable);
    }

    public void inicializarBotones(boolean editable, boolean eliminable) {
        if (!editable) {
            modBtnModificar(false);
        }
        if (!eliminable) {
            modBtnEliminar(false);
        }
        if (tabla != null) {
            if (tabla.getModel().getRowCount() == 0) {
                modBtn(false);
            } else {
                modBtn(true);
            }
        } else if (comboBox != null) {
            if (comboBox.getModel().getSize() == 0) {
                modBtn(false);
            } else {
                modBtn(true);
            }
        }
    }

    private void modBtn(boolean valor) {
        modBtnModificar(valor);
        modBtnEliminar(valor);
    }

    private void modBtnModificar(boolean valor) {
        if (btnModificar != null) {
            btnModificar.setEnabled(valor);
        }
    }

    private void modBtnEliminar(boolean valor) {
        if (btnEliminar != null) {
            btnEliminar.setEnabled(valor);
        }
    }

}
