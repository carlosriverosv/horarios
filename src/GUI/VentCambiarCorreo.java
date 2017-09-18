/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdCambiarCorreo;
import business.ButtonHandler;
import data.Usuario;
import javax.swing.JFrame;

/**
 *
 * @author Carlos
 */
public class VentCambiarCorreo extends JFrame {

    private Usuario usuario;
    /**
     * Creates new form VentCambiarCorreo
     * @param usuario representa el objeto usuario que est� en la aplicaci�n
     */
    public VentCambiarCorreo(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
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

        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblNuevoCorreo = new javax.swing.JLabel();
        txtNuevoCorreo = new javax.swing.JTextField();
        btnCambiarCorreo = new javax.swing.JButton();
        lblCorreoActual = new javax.swing.JLabel();
        txtCorreoActual = new javax.swing.JTextField();
        txtCorreoActual.setText(usuario.getCorreo());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cambiar correo electr�nico");
        setAlwaysOnTop(true);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());
        setResizable(false);

        lblPassword.setText("Contrase�a:");

        lblNuevoCorreo.setText("Nuevo correo electr�nico:");

        btnCambiarCorreo.addActionListener(new ButtonHandler(new CmdCambiarCorreo(this)));
        btnCambiarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/aceptar.png"))); // NOI18N
        btnCambiarCorreo.setText("Aceptar");

        lblCorreoActual.setText("Correo electr�nico actual:");

        txtCorreoActual.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCorreoActual)
                        .addGap(18, 18, 18)
                        .addComponent(txtCorreoActual))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoCorreo)
                            .addComponent(lblPassword))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNuevoCorreo)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btnCambiarCorreo)))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreoActual)
                    .addComponent(txtCorreoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevoCorreo)
                    .addComponent(txtNuevoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnCambiarCorreo)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarCorreo;
    private javax.swing.JLabel lblCorreoActual;
    private javax.swing.JLabel lblNuevoCorreo;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JTextField txtCorreoActual;
    private javax.swing.JTextField txtNuevoCorreo;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
    
    /**
     *
     * @return el usuario que est� en la aplicaci�n
     */
    public Usuario getUsuario(){
        return this.usuario;
    }
    
    /**
     *
     * @return la contrase�a introducida en el campo contrase�a
     */
    public char[] getPassword(){
        return this.txtPassword.getPassword();
    }
    
    /**
     *
     * @return el correo introducido en el campo correo electr�nico
     */
    public String getCorreo(){
        return this.txtNuevoCorreo.getText();
    }

}
