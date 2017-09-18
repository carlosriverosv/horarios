/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Manejador {

    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;
    private static Manejador manejador = null;
    private static String usuario;
    private static String pass;

    private Manejador() throws PersistenceException {

        Properties prop = new Properties();
        try {
            //Se lee el archivo de las propiedades de conexión a la BD y se 
            //agregan el nombre de usuario y contraseña introducidos en la 
            //ventana de login, para crear el entityManager.
            //Este procedimiento sólo se ejecuta una vez, que es cuando se realiza
            //el login, porque luego el objeto manejador queda inicializado y simplemente
            //es retornado por el método singleton getManejador().
            InputStream is = new FileInputStream("Conexion.properties");
            prop.load(is);
            prop.setProperty("javax.persistence.jdbc.user", usuario);
            prop.setProperty("javax.persistence.jdbc.password", pass);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Archivo de propiedades de conexión no encontrado. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error leyendo el archivo de propiedades de conexión. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        emf = Persistence.createEntityManagerFactory("ProgramacionAcademicaPU", prop);
        em = emf.createEntityManager();
    }

    public static Manejador getManejador() throws PersistenceException {
        if (manejador == null) {
            manejador = new Manejador();
        }
        return manejador;
    }

    public static Manejador getManejador(String usuario, String pass) throws PersistenceException {
        Manejador.usuario = usuario;
        Manejador.pass = pass;
        return getManejador();

    }

    public EntityManager gentEntityManager() {
        return em;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    
    public static String getUsuario(){
        return usuario;
    }
}
