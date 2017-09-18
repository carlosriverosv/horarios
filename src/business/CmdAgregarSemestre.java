/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import GUI.VentVincularAsigPlanEstudio;
import data.Manejador;
import dataDB.Semestre;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Carlos
 */
public class CmdAgregarSemestre implements CommandInterface {

    private VentVincularAsigPlanEstudio ventana;

    public CmdAgregarSemestre(VentVincularAsigPlanEstudio ventana) {
        this.ventana = ventana;
    }

    @Override
    public void processEvent() {
        EntityManager entityM = Manejador.getManejador().gentEntityManager();
        entityM.getTransaction().begin();
        List<Semestre> semestres = entityM.createNamedQuery("Semestre.findAll").getResultList();
        int i = 1;
        //Como la lista está ordenada ascendentemente, cuando se llegue al final de la misma o cuando no se encuentre
        //un número de semestre, se termina el ciclo y se crea un nuevo semestre con este valor.
        for (; i <= semestres.size(); i++) {
            if (i != semestres.get(i - 1).getNumSemestre()) {
                break;
            }
        }
        Semestre semestre = new Semestre(i);
        entityM.persist(semestre);
        entityM.getTransaction().commit();
        ComboBoxNumSemestre.actualizar();
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
