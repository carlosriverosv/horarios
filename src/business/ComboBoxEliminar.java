/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class ComboBoxEliminar extends JComboBox{
    
    protected DefaultComboBoxModel model;
 
    public void completar(boolean oculto) {
        if(model.getSize() != 0) {
            model.removeAllElements();
        }
    }
    
    public void remover() {
        model.removeElementAt(this.getSelectedIndex());
    }
}
