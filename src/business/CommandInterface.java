/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Carlos
 */
public interface CommandInterface {

    public void processEvent(MouseEvent e);
    
    public void processEvent(KeyEvent e);
    
    public void processEvent();
}
