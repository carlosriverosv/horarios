/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdCrearEdificio;
import business.ButtonHandler;
import business.ComboBoxEdificio;
import dataDB.Edificio;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class VentCrearEdificio extends javax.swing.JFrame {

    private Edificio edificio;
    private ComboBoxEdificio cbEdificio;

    /**
     * Creates new form VentCrearEdificio
     */
    public VentCrearEdificio() {
        initComponents();
        this.setVisible(true);
    }

    /**
     *
     * @param cbEdificio el combobox de todos los edificios creados
     */
    public VentCrearEdificio(ComboBoxEdificio cbEdificio) {
        this.cbEdificio = cbEdificio;
        initComponents();
        this.setVisible(true);
    }

    /**
     *
     * @param edificio el edificio a modificar
     */
    public VentCrearEdificio(Edificio edificio) {
        this.edificio = edificio;
        initComponents();
        this.setNumeroEd(String.valueOf(edificio.getNumeroEd()));
        this.setNombreEd(edificio.getNombre());
        this.setActivo(edificio.getActivo());
        this.btnAceptar.setActionCommand("Modificar");
        this.btnAceptar.setText("Modificar");
        this.setTitle("Modificar edificio");
        this.jChEdificioActivo.setVisible(true);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNumeroEd = new javax.swing.JLabel();
        txtNumeroEd = new javax.swing.JTextField();
        lblNombreEd = new javax.swing.JLabel();
        txtNombreEd = new javax.swing.JTextField();
        jChEdificioActivo = new javax.swing.JCheckBox();
        jChEdificioActivo.setSelected(true);
        jChEdificioActivo.setVisible(false);
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear edificio");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        lblNumeroEd.setText("Identificador del edificio(n�meros o letras):");

        lblNombreEd.setText("Nombre del edificio:");

        jChEdificioActivo.setText("Activo");
        jChEdificioActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChEdificioActivoActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        CmdCrearEdificio btnCrearEdifico = new CmdCrearEdificio(this, btnAceptar);
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptarPeque.png"))); // NOI18N
        btnAceptar.addActionListener(new ButtonHandler(btnCrearEdifico));
        btnAceptar.setText("Aceptar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumeroEd)
                    .addComponent(lblNombreEd))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNombreEd, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNumeroEd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jChEdificioActivo)
                        .addGap(32, 32, 32))))
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(btnCancelar)
                .addGap(103, 103, 103)
                .addComponent(btnAceptar)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumeroEd)
                    .addComponent(txtNumeroEd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jChEdificioActivo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreEd)
                    .addComponent(txtNombreEd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.cerrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jChEdificioActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChEdificioActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChEdificioActivoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox jChEdificioActivo;
    private javax.swing.JLabel lblNombreEd;
    private javax.swing.JLabel lblNumeroEd;
    private javax.swing.JTextField txtNombreEd;
    private javax.swing.JTextField txtNumeroEd;
    // End of variables declaration//GEN-END:variables

    public String getNumeroEd() {
        return txtNumeroEd.getText().toUpperCase();
    }

    public String getNombreEd() {
        return txtNombreEd.getText().toUpperCase();
    }

    public boolean getActivo() {
        return jChEdificioActivo.isSelected();
    }

    public void setActivo(boolean activo) {
        this.jChEdificioActivo.setSelected(activo);
    }

    public void setNombreEd(String nombreEd) {
        this.txtNombreEd.setText(nombreEd);
    }

    public void setNumeroEd(String numeroEd) {
        this.txtNumeroEd.setText(numeroEd);
    }

    public void errorNumeroEd() {
        JOptionPane.showMessageDialog(this, "Introduzca s�lo n�meros en el campo: " + "n�mero de edificio" + ".", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void cerrar() {
        this.dispose();
    }

    public Edificio getEdificio() {
        return this.edificio;
    }

    public void confirmarEdAgregado() {
        JOptionPane.showMessageDialog(this, "Edificio agregado correctamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/edificio.png")));
        this.setNombreEd("");
        this.setNumeroEd("");
    }

    public void confirmarEdModificado() {
        JOptionPane.showMessageDialog(this, "Edificio modificado correctamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/edificio.png")));
    }
}
