/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdCrearAsignatura;
import business.ButtonHandler;
import business.ComboBoxNivelEstudio;
import dataDB.Asignatura;
import dataDB.Nivelestudio;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class VentCrearAsignatura extends javax.swing.JFrame {

    private Asignatura asignatura;

    /**
     * Creates new form VentCrearAsignatura
     */
    public VentCrearAsignatura() {
        initComponents();
        this.setVisible(true);
    }

    /**
     *
     * @param asignatura representa la asignatura a modificar
     */
    public VentCrearAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
        initComponents();
        this.setCodAsignatura(String.valueOf(asignatura.getCodigoAsig()));
        this.setNombreAsignatura(asignatura.getNombreAsig());
        this.setNivelEstudio(asignatura.getIdNivelEstudio());
        this.setActivo(asignatura.getActivo());
        this.jCkAsignaturaActiva.setVisible(true);
        this.btnAdAsignatura.setText("Modificar");
        this.btnAdAsignatura.setActionCommand("Modificar");
        this.setTitle("Modificar asignatura");
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

        lblNombreAsignatura = new javax.swing.JLabel();
        lblCodigoAsig = new javax.swing.JLabel();
        lblNivelAsignatura = new javax.swing.JLabel();
        txtCodAsignatura = new javax.swing.JTextField();
        txtNombreAsig = new javax.swing.JTextField();
        btnAdAsignatura = new javax.swing.JButton();
        jcbNivelEstudio = new ComboBoxNivelEstudio();
        jCkAsignaturaActiva = new javax.swing.JCheckBox();
        jCkAsignaturaActiva.setSelected(true);
        jCkAsignaturaActiva.setVisible(false);
        btnCancelar = new javax.swing.JButton();
        btnAgregarNivelEstudio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear nueva asignatura");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        lblNombreAsignatura.setText("Nombre asignatura:");

        lblCodigoAsig.setText("C�digo asignatura:");

        lblNivelAsignatura.setText("Nivel de estudio:");

        btnAdAsignatura.addActionListener(new ButtonHandler(new CmdCrearAsignatura(this, btnAdAsignatura)));
        btnAdAsignatura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptarPeque.png"))); // NOI18N
        btnAdAsignatura.setText("Aceptar");

        jCkAsignaturaActiva.setText("Activa");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAgregarNivelEstudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/adicionarPeque.png"))); // NOI18N
        btnAgregarNivelEstudio.setToolTipText("Agregar nuevo nivel de estudio");
        btnAgregarNivelEstudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNivelEstudioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNivelAsignatura)
                    .addComponent(lblNombreAsignatura)
                    .addComponent(lblCodigoAsig))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbNivelEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCkAsignaturaActiva)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(btnAgregarNivelEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdAsignatura)
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigoAsig)
                    .addComponent(jCkAsignaturaActiva))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreAsig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreAsignatura))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNivelAsignatura)
                        .addComponent(jcbNivelEstudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarNivelEstudio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAdAsignatura))
                .addGap(37, 37, 37))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.cerrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarNivelEstudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNivelEstudioActionPerformed
        new VentCrearNivelEstudio((ComboBoxNivelEstudio) jcbNivelEstudio);
    }//GEN-LAST:event_btnAgregarNivelEstudioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdAsignatura;
    private javax.swing.JButton btnAgregarNivelEstudio;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox jCkAsignaturaActiva;
    private javax.swing.JComboBox jcbNivelEstudio;
    private javax.swing.JLabel lblCodigoAsig;
    private javax.swing.JLabel lblNivelAsignatura;
    private javax.swing.JLabel lblNombreAsignatura;
    private javax.swing.JTextField txtCodAsignatura;
    private javax.swing.JTextField txtNombreAsig;
    // End of variables declaration//GEN-END:variables

    /**
     * Cierra la ventana
     */
    public void cerrar() {
        this.dispose();
    }

    /**
     *
     * @return el c�digo de la asignatura
     */
    public String getCodAsignatura() {
        return txtCodAsignatura.getText();
    }

    /**
     *
     * @return el nombre de la asignatura
     */
    public String getNombreAsignatura() {
        return txtNombreAsig.getText().toUpperCase();
    }

    /**
     *
     * @return el estado: activo / no activo, de la asignatura
     */
    public boolean getActivo() {
        return jCkAsignaturaActiva.isSelected();
    }

    /**
     *
     * @return el nivel de estudio de la asignatura
     */
    public Nivelestudio getNivelEstudio() {
        return (Nivelestudio) jcbNivelEstudio.getSelectedItem();
    }

    /**
     *
     * @param codigoAsig el c�digo de la asignatura a modificar
     */
    public void setCodAsignatura(String codigoAsig) {
        txtCodAsignatura.setText(codigoAsig);
    }

    /**
     *
     * @param nombreAsig el nombre de la asignatura a modificar
     */
    public void setNombreAsignatura(String nombreAsig) {
        txtNombreAsig.setText(nombreAsig);
    }

    /**
     *
     * @param activo el estado de la asignatura a modificar
     */
    public void setActivo(boolean activo) {
        jCkAsignaturaActiva.setSelected(activo);
    }

    /**
     *
     * @param nivelEstudio el nivel de estudio de la asignatura a modificar
     */
    public void setNivelEstudio(Nivelestudio nivelEstudio) {
        jcbNivelEstudio.setSelectedItem(nivelEstudio);
    }

    /**
     *
     * @return la asignatura
     */
    public Asignatura getAsignatura() {
        return this.asignatura;
    }

    /**
     * Despliega el mensaje cuando la asignatura fue correctamente agregada
     */
    public void confirmarAsignaturaAgregada() {
        JOptionPane.showMessageDialog(this, "Asignatura agregada exitosamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/asignatura.png")));
        this.setCodAsignatura("");
        this.setNombreAsignatura("");
    }

    /**
     * Despliega el mensaje cuando la asignatura fue correctamente modificada
     */
    public void confirmarAsignaturaModificada() {
        JOptionPane.showMessageDialog(this, "Asignatura modificada exitosamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/asignatura.png")));
    }
}
