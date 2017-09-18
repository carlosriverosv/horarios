/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import generalities.Utilidades;
import dataDB.Edificio;
import dataDB.Salon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdImportarArchSalones extends CmdImportarArchivo {

    public CmdImportarArchSalones(JMenuItem jmenu) {
        super(jmenu);
        nombreColumnas = Salon.COLUMNAS_ARCHIVO.trim();
    }

    //Para comprender este algoritmo, revise la implementación del método que tiene este mismo nombre
    //en la clase CargarArchAsig
    @Override
    public String importar() {
        String errores = "";
        try {
            ArrayList<String> archSalones = Utilidades.leerArchivo(origen);
            archSalones.remove(0);
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            int numeroDeImportado = 0;
            try {
                for (String cadaSalon : archSalones) {
                    int posInicial = 0;
                    int numeroDato = 0;
                    String[] datosSalon = new String[5];
                    int i = 0;
                    while (i < cadaSalon.length()) {
                        if (cadaSalon.charAt(i) == CmdImportarArchivo.SIMBOLO) {
                            datosSalon[numeroDato] = cadaSalon.substring(posInicial, i);
                            posInicial = i + 1;
                            numeroDato++;
                        }
                        i++;
                    }
                    datosSalon[numeroDato] = cadaSalon.substring(posInicial, cadaSalon.length());
                    try {
                        try {
                            //datosSalon[0] : nombre del salón
                            //datosSalon[1] : número del edificio
                            //datosSalon[2] : capacidad del salón
                            //datosSalon[3] : observaciones
                            //datosSalon[4] : activo /no activo
                            Edificio edificio = (Edificio) entityM.createNamedQuery("Edificio.findByNumeroEd").setParameter("numeroEd", datosSalon[1]).getSingleResult();
                            Salon salon = new Salon(null, datosSalon[0].toUpperCase(), Integer.parseInt(datosSalon[2]), datosSalon[4].equalsIgnoreCase("activo") || datosSalon[4].equalsIgnoreCase("si") ? true : false);
                            salon.setIdEdificio(edificio);
                            salon.setObservaciones(datosSalon[3].toUpperCase());
                            entityM.getTransaction().begin();
                            entityM.persist(salon);
                            entityM.getTransaction().commit();
                        } catch (NoResultException ex) {
                            errores += "-No fue posible importar el salón " + datosSalon[0] + " del edificio " + datosSalon[1] + " , porque el edificio '" + datosSalon[1] + "' no existe." + '\n';
                            throw new RollbackException();
                        } catch (NumberFormatException ex) {
                            errores += "-No fue posible importar el salón " + datosSalon[0] + " porque la capacidad debe ser un valor numérico." + '\n';
                            throw new RollbackException();
                        }
                    } catch (RollbackException ex) {
                    }
                    numeroDeImportado++;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "El formato del archivo es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                errores = "Se importaron solamente los primeros " + numeroDeImportado + " salones.";
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No fue posible leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            errores = "No se importó ningun dato.";
        }
        return errores;
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
