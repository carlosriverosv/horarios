/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentConfBD;
import GUI.VentanaLogin;
import generalities.Utilidades;
import exceptions.AdministradorNoExisteException;
import javax.swing.UIManager;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
        }
        try {
            Utilidades.existeArchivo("Conexion.properties");
            new VentanaLogin();
        } catch (AdministradorNoExisteException ex) {
            new VentConfBD();
        }

    }
}
