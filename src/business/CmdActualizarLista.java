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
public class CmdActualizarLista implements CommandInterface {

    private Lista lista;

    public CmdActualizarLista(Lista lista) {
        this.lista = lista;
    }

    @Override
    public void processEvent() {

    }

    @Override
    public void processEvent(MouseEvent e) {
        if (!lista.model.isEmpty()) {
            lista.notifyObservers();
        }
    }

    @Override
    public void processEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
            lista.notifyObservers();
        }
    }

}
