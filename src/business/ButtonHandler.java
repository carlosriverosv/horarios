/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 *
 * Esta clase se encarga de escuchar los eventos producidos por los botones o
 * menÃºs de la aplicaciÃ³n. Es parte del patrón comando.
 *
 */
public class ButtonHandler implements ActionListener, MouseListener, KeyListener, WindowListener {

    private CommandInterface commandObj;

    public ButtonHandler(CommandInterface commandObj) {
        this.commandObj = commandObj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            commandObj.processEvent();
        } catch (RollbackException ex) {
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            if (((String) (entityM.createNativeQuery("SELECT Insert_priv FROM mysql.db WHERE User ='" + Manejador.getUsuario() + "' AND Db ='gestionacademica_dptoiee' AND Host = '%'").getSingleResult())).equalsIgnoreCase("N")) {
                JOptionPane.showMessageDialog(null, "Usted no tiene permisos para insertar datos en esta aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (((String) (entityM.createNativeQuery("SELECT Update_priv FROM mysql.db WHERE User ='" + Manejador.getUsuario() + "' AND Db ='gestionacademica_dptoiee' AND Host = '%'").getSingleResult())).equalsIgnoreCase("N")) {
                JOptionPane.showMessageDialog(null, "Usted no tiene permisos para modificar datos en esta aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un registro que contiene alguno de los datos ingresados. " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        commandObj.processEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        commandObj.processEvent(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        commandObj.processEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        commandObj.processEvent(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        commandObj.processEvent(e);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        commandObj.processEvent();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
