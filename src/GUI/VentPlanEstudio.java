/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.ComboBoxPlanEstudios;
import dataDB.Planestudio;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class VentPlanEstudio extends VentTabla {

    /**
     * Creates new form VentPlanEstudio
     */
    public VentPlanEstudio(boolean editable, boolean eliminable) {
        super(editable, eliminable);
        initComponents();
        btnModificar = btnAceptar;
        inicializarBotones(editable, eliminable);
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

        lblPlanEstudio = new javax.swing.JLabel();
        comboBox = new ComboBoxPlanEstudios();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planes de estudio");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        lblPlanEstudio.setText("Plan de estudio:");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptarPeque.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlanEstudio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBox, 0, 319, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(btnAceptar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlanEstudio)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {
            new VentAsigEnPlanEstudio((Planestudio) comboBox.getSelectedItem(), editable, eliminable);
            this.dispose();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Seleccione un plan de estudio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    /*
    private javax.swing.JComboBox comboBox;
    */
    private javax.swing.JLabel lblPlanEstudio;
    // End of variables declaration//GEN-END:variables
}
