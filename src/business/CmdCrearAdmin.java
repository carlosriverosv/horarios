/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import generalities.Errores;
import GUI.VentanaCrearAdmin;
import data.Usuario;
import data.Manejador;
import data.Password;
import generalities.Utilidades;
import exceptions.CorreoNoValidoException;
import exceptions.NomUsuarioException;
import exceptions.PasswordException;
import exceptions.UsuarioYaExisteException;
import dataDB.Dia;
import dataDB.Horainicio;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdCrearAdmin implements CommandInterface {

    private VentanaCrearAdmin ventana;
    private Usuario administrador;
    private Connection conn;
    private JButton boton;

    public CmdCrearAdmin(VentanaCrearAdmin ventana, Connection conn, JButton boton) {
        super();
        this.ventana = ventana;
        this.conn = conn;
        this.boton = boton;
    }

    @Override
    public void processEvent() {

        boton.setEnabled(false);
        Password pass = null;
        Password rePass = null;
        try {
            pass = new Password(ventana.getPassword());
            rePass = new Password(ventana.getRePassword());
            //compara que ambas contraseñas digitadas sean iguales y luego crea el administrador
            if (pass.compareTo(rePass) == 1) {
                boton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    administrador = new Usuario(ventana.getNomUsuario(), pass, ventana.getCorreo());
                    JOptionPane.showMessageDialog(null, "Se enviará un mensaje al correo electrónico proporcionado. Por favor espere.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        Utilidades.enviarCorreo(administrador);
                        Errores.confirmarEnvioCorreo();

                        //Crea un usuario en la base de datos con los datos ingresados
                        //en la ventana de Crear Usuario
                        Statement stmt = null;
                        try {
                            stmt = conn.createStatement();
                            //Crea el usuario en la base de datos
                            String sql = "CREATE USER '" + ventana.getNomUsuario() + "' IDENTIFIED BY '" + pass.toString() + "'";
                            String sql1 = "CREATE USER '" + ventana.getNomUsuario() + "'@'localhost' IDENTIFIED BY '" + pass.toString() + "'";
                            stmt.executeUpdate(sql);
                            stmt.executeUpdate(sql1);
                            //Asigna todos los privilegios a este usuario, incluyendo el priviligeo de asignar
                            //privilegios a otros usuarios
                            stmt.executeUpdate("GRANT ALL PRIVILEGES ON gestionacademica_dptoiee.* TO '" + ventana.getNomUsuario() + "'@'%' WITH GRANT OPTION");
                            stmt.executeUpdate("GRANT ALL PRIVILEGES ON gestionacademica_dptoiee.* TO '" + ventana.getNomUsuario() + "'@'localhost' WITH GRANT OPTION");
                            stmt.executeUpdate("GRANT SELECT ON mysql.user TO '" + ventana.getNomUsuario() + "'@'localhost'");
                            stmt.executeUpdate("GRANT SELECT ON mysql.user TO '" + ventana.getNomUsuario() + "'@'%'");
                            stmt.executeUpdate("GRANT SELECT ON mysql.db TO '" + ventana.getNomUsuario() + "'@'localhost' WITH GRANT OPTION");
                            stmt.executeUpdate("GRANT SELECT ON mysql.db TO '" + ventana.getNomUsuario() + "'@'%' WITH GRANT OPTION");
                            stmt.executeUpdate("GRANT CREATE USER ON *.* TO '" + ventana.getNomUsuario() + "'@'localhost'");
                            stmt.executeUpdate("GRANT CREATE USER ON *.* TO '" + ventana.getNomUsuario() + "'@'%'");
                            stmt.executeUpdate("FLUSH PRIVILEGES");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                            boton.setEnabled(true);
                        } finally {
                            try {
                                if (stmt != null) {
                                    stmt.close();
                                }
                            } catch (SQLException se2) {
                                JOptionPane.showMessageDialog(null, se2, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                JOptionPane.showMessageDialog(null, se, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Administrador creado exitosamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/GUI/iconos/usuarioGrande.png")));
                        ventana.dispose();
                        try {
                            EntityManager entityM = Manejador.getManejador(ventana.getNomUsuario(), pass.toString()).gentEntityManager();
                            entityM.getTransaction().begin();
                            entityM.persist(new Dia("lunes"));
                            entityM.persist(new Dia("martes"));
                            entityM.persist(new Dia("miercoles"));
                            entityM.persist(new Dia("jueves"));
                            entityM.persist(new Dia("viernes"));
                            entityM.persist(new Dia("sabado"));
                            entityM.persist(new Dia("domingo"));
                            for (int i = Horario.HORA_INICIAL; i <= Horario.HORA_FINAL; i++) {
                                entityM.persist(new Horainicio(i));
                            }
                            entityM.getTransaction().commit();
                            JOptionPane.showMessageDialog(null, "Ejecute la aplicación nuevamente e ingrese al sistema con el usuario creado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                            entityM.close();
                            System.exit(0);
                        } catch (PersistenceException | NullPointerException ex) {
                            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(null, "Revise la dirección de correo electrónico ingresada y/o su conexión a Internet e intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                        boton.setEnabled(true);
                    }
                } catch (UsuarioYaExisteException ex) {
                    Errores.errorUsuarioYaExiste();
                    boton.setEnabled(true);
                    boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } catch (CorreoNoValidoException ex) {
                    Errores.correoNoValido(ex.getMessage());
                    boton.setEnabled(true);
                    boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } catch (NomUsuarioException ex) {
                    Errores.nomUsuarioException(ex.getMessage());
                    boton.setEnabled(true);
                    boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            } else {
                Errores.errorPasswordsDiferentes();
                boton.setEnabled(true);
                boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        } catch (PasswordException ex) {
            Errores.errorLongitudPassword(ex.getMessage());
            boton.setEnabled(true);
            boton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
