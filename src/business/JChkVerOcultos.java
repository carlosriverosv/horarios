/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentTabla;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

/**
 *
 * @author Carlos
 */
public class JChkVerOcultos extends JCheckBox implements CommandInterface{

    private VentTabla ventana;

    public JChkVerOcultos(VentTabla ventana) {
        this.ventana = ventana;
        this.setText("Ver ocultos");
        this.addActionListener(new ButtonHandler(this));
    }
    
    @Override
    public void processEvent() {
        if(this.isSelected()) {
            ventana.mostrarOcultos();
        } else {
            ventana.mostrarNoOcultos();
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
