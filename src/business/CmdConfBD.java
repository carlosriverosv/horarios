/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentConfBD;
import GUI.VentConfCorreo;
import GUI.VentPrincipal;
import data.Manejador;
import data.Password;
import data.Usuario;
import exceptions.PasswordException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdConfBD implements CommandInterface {

    private VentConfBD ventana;

    public CmdConfBD(VentConfBD ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String ubicacion = ventana.getUbicacion();
            String puerto = ventana.getPuerto();
            String usuario = ventana.getUsuario();
            String password = String.valueOf(ventana.getPassword());
            //Se intenta conectar con el servidor de Base de Datos. Si logra conectarse
            //crea el archivo de Properties, de lo contrario muestra un error de conexión
            try {
                conn = DriverManager.getConnection("jdbc:mysql://" + ubicacion + ":" + puerto, usuario, password);
                //Escribe el archivo de propiedades de conexión de la BD
                Properties propiedades = new Properties();
                propiedades.setProperty("javax.persistence.jdbc.url", "jdbc:mysql://" + ubicacion + ":" + puerto + "/gestionacademica_dptoiee");
                propiedades.setProperty("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
                propiedades.setProperty("eclipselink.ddl-generation", "create-tables");
                FileOutputStream out;
                try {
                    out = new FileOutputStream("Conexion.properties");
                    propiedades.store(out, "---Datos de acceso a la Base de Datos---");
                    out.close();
                    File archivo = new File("Conexion.properties");
                    archivo.setReadOnly();
                    Files.setAttribute(archivo.toPath(), "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }

                //Intenta crear la base de datos gestionacademica_dptoiee. Si no existe se crea y 
                // se muestra la ventana para crear el administrador.
                //Si ya existe, ocurre una excepción y se
                //muestra la ventana principal.
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("CREATE DATABASE gestionacademica_dptoiee");
                    ventana.dispose();
                    JOptionPane.showMessageDialog(null, "Antes de continuar, asegúrese de que autoriza en su "
                            + "servidor de correo electrónico (Ej: Gmail ), el inicio de sesión de la cuenta elegida desde "
                            + "aplicaciones externas a las que provee su servidor.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    new VentConfCorreo(conn);
                } catch (SQLException ex2) {
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Asignatura ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Docente ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Edificio ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Salon ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Nivelestudio ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Planestudio ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Tipologia ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Tipocontrato ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Progacademica ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Grupo ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Itemhorario ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
                    try {
                        stmt.executeUpdate("ALTER TABLE gestionacademica_dptoiee.Asignaturaenplanestudio ADD oculto bool DEFAULT 0");
                    } catch (SQLException ex3) {
                    }
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
                    ventana.dispose();
                    //Si ya existe la base de datos, se hace el login y se muestra la ventana principal
                    Manejador.getManejador(usuario, password);
                    try {
                        new VentPrincipal(new Usuario(usuario, new Password(password.toCharArray())));
                    } catch (PasswordException ex) {
                    }
                }
            } catch (SQLException ex2) {
                JOptionPane.showMessageDialog(null, "Error de conexión con el servidor de Base de Datos. " + ex2.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException ex1) {
            JOptionPane.showMessageDialog(null, ex1, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
