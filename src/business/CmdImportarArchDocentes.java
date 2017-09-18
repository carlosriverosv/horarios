/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import generalities.Utilidades;
import dataDB.Docente;
import dataDB.Tipocontrato;
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
public class CmdImportarArchDocentes extends CmdImportarArchivo {

    public CmdImportarArchDocentes(JMenuItem jmenu) {
        super(jmenu);
        nombreColumnas = Docente.COLUMNAS_ARCHIVO.trim();
    }

    //Para comprender este algoritmo, revise la implementación del método que tiene este mismo nombre
    //en la clase CargarArchAsig
    @Override
    public String importar() {
        String errores = "";
        try {
            ArrayList<String> archDocentes = Utilidades.leerArchivo(origen);
            archDocentes.remove(0);
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            int numeroDeImportado = 0;
            try {
                for (String cadaDocente : archDocentes) {
                    int posInicial = 0;
                    int numeroDato = 0;
                    String[] datosDocente = new String[5];
                    int i = 0;
                    while (i < cadaDocente.length()) {
                        if (cadaDocente.charAt(i) == CmdImportarArchivo.SIMBOLO) {
                            datosDocente[numeroDato] = cadaDocente.substring(posInicial, i);
                            posInicial = i + 1;
                            numeroDato++;
                        }
                        i++;
                    }
                    datosDocente[numeroDato] = cadaDocente.substring(posInicial, cadaDocente.length());
                    try {
                        try {
                            Tipocontrato tipoContrato = (Tipocontrato) entityM.createNamedQuery("Tipocontrato.findByTipoContrato").setParameter("tipoContrato", datosDocente[3]).getSingleResult();
                            Docente docente = new Docente(null, Integer.parseInt(datosDocente[0]), datosDocente[1].toUpperCase(), datosDocente[4].equalsIgnoreCase("activo") || datosDocente[4].equalsIgnoreCase("si") ? true : false);
                            docente.setCorreoElectronico(datosDocente[2]);
                            docente.setIdTipoContrato(tipoContrato);
                            entityM.getTransaction().begin();
                            entityM.persist(docente);
                            entityM.getTransaction().commit();
                        } catch (NoResultException ex) {
                            errores += "-No fue posible importar el docente " + datosDocente[1] + ", porque el tipo de contrato '" + datosDocente[3] + "' no existe." + '\n';
                            throw new RollbackException();
                        } catch(NumberFormatException ex) {
                            errores += "-No fue posible importar el docente " + datosDocente[1] + ", porque el documento ingresado '" + datosDocente[0] + "' debe ser numérico." + '\n';
                            throw new RollbackException();
                        }
                    } catch (RollbackException ex) {
                    }
                     numeroDeImportado++;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "El formato del archivo es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                errores = "Se importaron solamente los primeros " + numeroDeImportado + " Docentes.";
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
