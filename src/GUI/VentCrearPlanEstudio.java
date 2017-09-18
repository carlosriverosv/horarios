/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdCrearPlanEstudio;
import business.ButtonHandler;
import business.ComboBoxPlanEstudios;
import business.Observer;
import dataDB.Planestudio;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class VentCrearPlanEstudio extends javax.swing.JFrame {

    private Planestudio planEstudio;
    private ComboBoxPlanEstudios cbPlanEstudios;

    /**
     * Creates new form VentCrearPlanEstudio
     */
    public VentCrearPlanEstudio() {
        initComponents();
        this.setVisible(true);
    }

    public VentCrearPlanEstudio(ComboBoxPlanEstudios cbPlanEstudios) {
        this.cbPlanEstudios = cbPlanEstudios;
        initComponents();
        this.setVisible(true);
    }

    public VentCrearPlanEstudio(Planestudio planEstudio) {
        this.planEstudio = planEstudio;
        initComponents();
        this.btnAceptar.setActionCommand("Modificar");
        this.btnAceptar.setText("Modificar");
        this.setNombrePlan(planEstudio.getNombrePlan());
        this.setAnnoPlan(String.valueOf(planEstudio.getAnno()));
        this.setCodigoPlan(String.valueOf(planEstudio.getCodigoPlan()));
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

        lblCodigoPlan = new javax.swing.JLabel();
        txtCodigoPlan = new javax.swing.JTextField();
        lblNombrePlan = new javax.swing.JLabel();
        txtNombrePlan = new javax.swing.JTextField();
        lblAnno = new javax.swing.JLabel();
        txtAnno = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear plan de estudio");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        lblCodigoPlan.setText("C�digo del plan:");

        lblNombrePlan.setText("Nombre del plan de estudio:");

        lblAnno.setText("A�o:");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        CmdCrearPlanEstudio btnCrearPlanEstudio = new CmdCrearPlanEstudio(this, btnAceptar);
        btnCrearPlanEstudio.register((Observer) cbPlanEstudios);
        btnAceptar.addActionListener(new ButtonHandler(btnCrearPlanEstudio));
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptarPeque.png"))); // NOI18N
        btnAceptar.setText("Aceptar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombrePlan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAnno)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNombrePlan)
                                .addComponent(lblCodigoPlan, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnCancelar)
                        .addGap(44, 44, 44)
                        .addComponent(btnAceptar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoPlan)
                    .addComponent(txtCodigoPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombrePlan)
                    .addComponent(txtNombrePlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnno)
                    .addComponent(txtAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.cerrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblAnno;
    private javax.swing.JLabel lblCodigoPlan;
    private javax.swing.JLabel lblNombrePlan;
    private javax.swing.JTextField txtAnno;
    private javax.swing.JTextField txtCodigoPlan;
    private javax.swing.JTextField txtNombrePlan;
    // End of variables declaration//GEN-END:variables

    public String getNombrePlan() {
        return txtNombrePlan.getText().toUpperCase();
    }

    public String getCodigoPlan() {
        return txtCodigoPlan.getText().toUpperCase();
    }

    public String getAnnoPlan() {
        return txtAnno.getText();
    }

    public void setNombrePlan(String nombrePlan) {
        this.txtNombrePlan.setText(nombrePlan);
    }

    public void setCodigoPlan(String codigoPlan) {
        this.txtCodigoPlan.setText(codigoPlan);
    }

    public void setAnnoPlan(String annoPlan) {
        this.txtAnno.setText(annoPlan);
    }

    public void cerrar() {
        this.dispose();
    }

    public void confirmacionPlanAgregado() {
        JOptionPane.showMessageDialog(this, "Plan de estudios agregado correctamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE);
        this.setNombrePlan("");
        this.setCodigoPlan("");
        this.setAnnoPlan("");
    }

    public void errorNoNumeros() {
        JOptionPane.showMessageDialog(this, "Introduzca s�lo n�meros en los campos: " + "c�digo del plan " + "y " + "a�o plan" + ".", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public Planestudio getPlanEstudio() {
        return this.planEstudio;
    }

    public void confirmarPlanModificado() {
        JOptionPane.showMessageDialog(this, "Plan de estudios modificado correctamente.", "Confirmaci�n", JOptionPane.INFORMATION_MESSAGE);
    }

}
