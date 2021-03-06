/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdAbrirProgAca;
import business.CmdEliminarProgAca;
import business.ButtonHandler;
import business.ComboBoxProgAcaAnt;
import business.JChkVerOcultos;
import dataDB.Progacademica;

/**
 *
 * @author Carlos
 */
public class VentAbrirProgAnterior extends VentTabla {

    /**
     * Creates new form VentAbrirProgAnterior
     */
    private final VentPrincipal ventanaPrinc;

    /**
     *
     * @param ventanaPrinc representa la ventana principal de la aplicación
     */
    public VentAbrirProgAnterior(VentPrincipal ventanaPrinc, boolean eliminable) {
        super(true, eliminable);
        this.ventanaPrinc = ventanaPrinc;
        initComponents();
        inicializarBotones(true, eliminable);
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

        comboBox = new ComboBoxProgAcaAnt();
        btnModificar = new javax.swing.JButton();
        lblProgAca = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jChkVerOcultos = new JChkVerOcultos(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Abrir programación académica anterior");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptarPeque.png"))); // NOI18N
        btnModificar.setText("Abrir");
        btnModificar.addActionListener(new ButtonHandler(new CmdAbrirProgAca(this.ventanaPrinc, this)));

        lblProgAca.setText("Programación académica:");

        btnEliminar.addActionListener(new ButtonHandler(new CmdEliminarProgAca(this, btnEliminar)));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnEliminar.setText("Ocultar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblProgAca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jChkVerOcultos)
                        .addGap(88, 88, 88)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProgAca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
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
    ¨*/
    /*
    private javax.swing.JCheckBox jChkVerOcultos;
    */
    private javax.swing.JLabel lblProgAca;
    // End of variables declaration//GEN-END:variables

    /**
     *
     * @return la programación académica seleccionada
     */
    public Progacademica getProgAcademica() {
        return (Progacademica) comboBox.getSelectedItem();
    }

    /**
     * Cierra la ventana una vez se ha abierto la programación académica, luego
     * de opimir el botón Aceptar.
     */
    public void cerrar() {
        dispose();
    }

    public void removerFila() {
        ((ComboBoxProgAcaAnt) (comboBox)).remover();
    }

}
