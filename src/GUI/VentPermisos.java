/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdModificarPermisos;
import business.ButtonHandler;
import business.ListaUsuarios;
import data.Usuario;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Carlos
 */
public class VentPermisos extends javax.swing.JFrame implements ListSelectionListener {

    /**
     * Creates new form VentPermisos
     */
    public VentPermisos() {
        initComponents();
        jListUsuarios.addListSelectionListener(this);
        jListUsuarios.setSelectedIndex(0);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jListUsuarios = new ListaUsuarios();
        jCheckCrear = new javax.swing.JCheckBox();
        jCheckModificar = new javax.swing.JCheckBox();
        btnAceptar = new javax.swing.JButton();
        jCheckCrearModEli = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar permisos de usuarios");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        jScrollPane1.setViewportView(jListUsuarios);

        jCheckCrear.setText("Crear");

        jCheckModificar.setText("Modificar");

        btnAceptar.addActionListener(new ButtonHandler(new CmdModificarPermisos(this)));
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptarPeque.png"))); // NOI18N
        btnAceptar.setText("Modificar permisos");

        jCheckCrearModEli.setText("Crear, modificar y eliminar");
        jCheckCrearModEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckCrearModEliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jCheckCrearModEli)
                .addGap(18, 18, 18)
                .addComponent(jCheckCrear)
                .addGap(38, 38, 38)
                .addComponent(jCheckModificar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(btnAceptar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckCrear)
                    .addComponent(jCheckModificar)
                    .addComponent(jCheckCrearModEli))
                .addGap(18, 18, 18)
                .addComponent(btnAceptar)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckCrearModEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckCrearModEliActionPerformed
        if (jCheckCrearModEli.isSelected()) {
            jCheckCrear.setSelected(false);
            jCheckModificar.setSelected(false);
            jCheckCrear.setEnabled(false);
            jCheckModificar.setEnabled(false);
        } else {
            jCheckCrear.setEnabled(true);
            jCheckModificar.setEnabled(true);
        }

    }//GEN-LAST:event_jCheckCrearModEliActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JCheckBox jCheckCrear;
    private javax.swing.JCheckBox jCheckCrearModEli;
    private javax.swing.JCheckBox jCheckModificar;
    private javax.swing.JList jListUsuarios;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (jCheckCrearModEli.isSelected()) {
            jCheckCrearModEli.doClick();
        }
        jCheckCrear.setSelected(false);
        jCheckModificar.setSelected(false);
        String nomUsuario = getUsuarioSeleccionado();
        if (Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_ELIMINAR)) {
            jCheckCrearModEli.doClick();
        } else {
            if (Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_INSERTAR)) {
                jCheckCrear.setSelected(true);
            } else {
                jCheckCrear.setSelected(false);
            }
            if (Usuario.consultarPermiso(nomUsuario, Usuario.PRIV_ACTUALIZAR)) {
                jCheckModificar.setSelected(true);
            } else {
                jCheckModificar.setSelected(false);
            }
        }

    }

    public boolean modificarSeleccionado() {
        return jCheckModificar.isSelected();
    }

    public boolean crearSeleccionado() {
        return jCheckCrear.isSelected();
    }

    public boolean crearModEliSeleccionado() {
        return jCheckCrearModEli.isSelected();
    }

    public String getUsuarioSeleccionado() {
        return (String) jListUsuarios.getSelectedValue();
    }
}
