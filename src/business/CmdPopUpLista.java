/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Carlos
 */
public class CmdPopUpLista implements CommandInterface {

    private Lista lista;

    public CmdPopUpLista(Lista lista) {
        this.lista = lista;
    }

    @Override
    public void processEvent(MouseEvent e) {
        if (lista.getPopUp() != null) {
            if (e.isPopupTrigger()) {
                lista.getPopUp().show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void processEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processEvent(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
