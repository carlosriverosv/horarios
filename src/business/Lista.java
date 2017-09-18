/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Carlos
 */
public abstract class Lista extends JList implements Observable {

    protected DefaultListModel model;
    protected ArrayList<Observer> observadores = new ArrayList<>();
    private JPopupMenu popup;
    protected JMenuItem menuItemOcultar;
    protected JMenuItem menuItemModificar;

    public Lista() {
        model = new DefaultListModel();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setModel(model);
        CmdActualizarLista actualizarLista = new CmdActualizarLista(this);
        this.addMouseListener(new ButtonHandler(new CmdPopUpLista(this)));
        this.addMouseListener(new ButtonHandler(actualizarLista));
        this.addKeyListener(new ButtonHandler(actualizarLista));
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observadores) {
            observer.actualizarDatos(this);
        }
    }

    @Override
    public void register(Observer obs) {
        observadores.add(obs);
    }

    @Override
    public void unRegister(Observer obs) {
        observadores.remove(obs);
    }

    public void crearPopUp() {
        popup = new JPopupMenu();
        menuItemOcultar = new JMenuItem("Ocultar");
        menuItemOcultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png")));
        menuItemModificar = new JMenuItem("Modificar");
        menuItemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/editarPeque.png")));
        popup.add(menuItemModificar);
        popup.add(menuItemOcultar);
    }

    public JPopupMenu getPopUp() {
        return popup;
    }

}
