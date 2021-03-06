/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdEliminarTipoContrato;
import business.CmdModTipoContrato;
import business.ButtonHandler;
import business.ComboBoxTipoContrato;
import business.JChkVerOcultos;
import dataDB.Tipocontrato;

/**
 *
 * @author Carlos
 */
public class VentModTipoContrato extends VentTabla {

    /**
     * Creates new form VentModTipoContrato
     */
    public VentModTipoContrato(boolean editable, boolean eliminable) {
        super(editable, eliminable);
        initComponents();
        inicializarBotones(editable, eliminable);
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

        lblTipoContrato = new javax.swing.JLabel();
        comboBox = new ComboBoxTipoContrato();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jChkVerOcultos = new JChkVerOcultos(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar tipo de contrato");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        lblTipoContrato.setText("Tipo de contrato:");

        btnModificar.addActionListener(new ButtonHandler(new CmdModTipoContrato(this)));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/editarPeque.png"))); // NOI18N
        btnModificar.setText("Modificar");

        btnEliminar.addActionListener(new ButtonHandler(new CmdEliminarTipoContrato(this, btnEliminar)));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnEliminar.setText("Ocultar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblTipoContrato)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jChkVerOcultos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addGap(67, 67, 67))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoContrato)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(jChkVerOcultos))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /*
    private javax.swing.JButton btnEliminar;
    */
    /*
    private javax.swing.JButton btnModificar;
    */
    /*
    private javax.swing.JComboBox comboBox;
    */
    /*
    private javax.swing.JCheckBox jChkVerOcultos;
    */
    private javax.swing.JLabel lblTipoContrato;
    // End of variables declaration//GEN-END:variables

    public Tipocontrato getTipoContrato() {
        return (Tipocontrato) comboBox.getSelectedItem();
    }

    public void cerrar() {
        this.dispose();
    }

    public void removerFila() {
        comboBox.remover();
    }
}
