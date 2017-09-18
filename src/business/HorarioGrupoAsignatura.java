/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentProgAca;
import static business.Horario.HORA_INICIAL;
import static business.Horario.horas;
import data.Manejador;
import dataDB.Grupo;
import dataDB.GrupoPK;
import dataDB.Itemhorario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Carlos
 */
public class HorarioGrupoAsignatura extends Horario implements Observable {

    private static ArrayList<Observer> observadores = new ArrayList<>();
    private ListaGrupos listaGr;
    private GrupoPK grupoPK;
    private Grupo grupo;
    private VentProgAca ventana;

    public HorarioGrupoAsignatura(int idProg, boolean eliminable, VentProgAca ventana) {
        super(idProg);
        cantidadItemsHorario = new int[horas.length][model.getColumnCount()];
        this.ventana = ventana;
        ListSelectionModel cellSelectionModel = getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actualizarDatosInformacion();
                notifyObservers();
            }
        };
        if (eliminable) {
            KeyListener keyListener;
            keyListener = new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_DELETE && !(getModelo().getValueAt(getSelectedRow(), getSelectedColumn()).toString().isEmpty())) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este horario?", "Horario", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            borrarItemHorario(getSelectedRow(), getSelectedColumn());
                            JOptionPane.showMessageDialog(null, "Horario eliminado exitosamente.", "Horario", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            };
            this.addKeyListener(keyListener);
        }
        cellSelectionModel.addListSelectionListener(listener);
        getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }

    private void actualizarDatosInformacion() {
        setDia(getSelectedColumn());
        setHora(getSelectedRow());
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        try {
            Itemhorario item = (Itemhorario) entity.createQuery("SELECT i FROM Itemhorario i WHERE "
                    + "i.itemhorarioPK.idProg = :idProg AND i.grupo =:numeroGrupo AND i.dia1.dia =:dia "
                    + "AND i.horainicio.horaInicio =:horaI AND i.oculto =:oculto").setParameter("idProg", getIdProg())
                    .setParameter("numeroGrupo", grupo).setParameter("dia", getNombreDia())
                    .setParameter("horaI", getNumeroHora()).setParameter("oculto", false).getSingleResult();
            ventana.setLimiteMax(String.valueOf(item.getLimiteMax()));
            ventana.setCapMax(String.valueOf(item.getCapMax()));
            ventana.setCuotas(String.valueOf(item.getCuotas()));
            ventana.setCapMin(String.valueOf(item.getCapMin()));
            ventana.setEdificio(item.getIdSalon().getIdEdificio());
            ventana.setSalon(item.getIdSalon());
            ventana.setDocente(item.getIdDocente());
            ventana.setOpcPrincipal(item.getPrincipalOAsistente().equalsIgnoreCase("P") ? true : false);
            ventana.activarBotonModificar(true);
        } catch (NoResultException ex) {
            ventana.habilitarCamposEdicion();
            ventana.activarBotonModificar(false);
        }
    }

    @Override
    public void actualizarDatos(Observable subject) {
        borrarContenido();
        listaGr = (ListaGrupos) subject;
        grupoPK = (GrupoPK) listaGr.getSelectedValue();
        if (grupoPK != null) {
            try {
                EntityManager entity = Manejador.getManejador().gentEntityManager();
                grupo = entity.find(Grupo.class, grupoPK);
                //List<Itemhorario> itemsHorario = entity.createQuery("SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.grupo =:numeroGrupo").setParameter("idProg", getIdProg()).setParameter("numeroGrupo", grupo).getResultList();
                List<Itemhorario> itemsHorario = entity.createQuery("SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.grupo =:numeroGrupo AND i.oculto =:oculto").setParameter("idProg", getIdProg()).setParameter("numeroGrupo", grupo).setParameter("oculto", false).getResultList();
                for (Itemhorario itemhorario : itemsHorario) {
                    setDia(getNumeroDia(itemhorario.getDia1().getDia()));
                    setHora(itemhorario.getHorainicio().getHoraInicio());
                    String docente = itemhorario.getIdDocente().getNombreDocente();
                    String edificio = String.valueOf(itemhorario.getIdSalon().getIdEdificio().getNumeroEd());
                    String salon = String.valueOf(itemhorario.getIdSalon().getNumeroSalon());
                    String informacion = getModelo().getValueAt(getHora() - HORA_INICIAL, getDia()) + SEPARADOR + " Docente: " + docente + " Ed: " + edificio + " Salón: " + salon + SEPARADOR;
                    getModelo().setValueAt(informacion, getHora() - HORA_INICIAL, getDia());
                    cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] = cantidadItemsHorario[getHora() - HORA_INICIAL][getDia()] + 1;
                }
            } catch (NullPointerException ex) {
            }
            actualizarDatosInformacion();
            notifyObservers();
        }
    }

    private void borrarItemHorario(int fila, int columna) {
        setDia(columna);
        setHora(fila);
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        grupoPK = (GrupoPK) listaGr.getSelectedValue();
        grupo = entity.find(Grupo.class, grupoPK);
        entity.getTransaction().begin();
        entity.createQuery("DELETE FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.grupo =:numeroGrupo AND i.dia1.dia =:dia AND i.horainicio.horaInicio =:hora")
                .setParameter("idProg", getIdProg()).setParameter("numeroGrupo", grupo)
                .setParameter("dia", getNombreDia())
                .setParameter("hora", getNumeroHora()).executeUpdate();
        entity.getTransaction().commit();
        getModelo().setValueAt("", fila, columna);
        actualizarDatos(listaGr);
        notifyObservers();
    }

    public void borrarItemHorario() {
        borrarItemHorario(getSelectedRow(), getSelectedColumn());
    }

    public Itemhorario getItemHorarioSeleccionado() {
        setDia(getSelectedColumn());
        setHora(getSelectedRow());
        EntityManager entity = Manejador.getManejador().gentEntityManager();
        Itemhorario item = (Itemhorario) entity.createQuery("SELECT i FROM Itemhorario i WHERE "
                + "i.itemhorarioPK.idProg = :idProg AND i.grupo =:numeroGrupo AND i.dia1.dia =:dia "
                + "AND i.horainicio.horaInicio =:horaI").setParameter("idProg", getIdProg())
                .setParameter("numeroGrupo", grupo).setParameter("dia", getNombreDia())
                .setParameter("horaI", getNumeroHora()).getSingleResult();
        return item;
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

}
