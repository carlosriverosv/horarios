/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import generalities.Utilidades;
import dataDB.Asignatura;
import dataDB.Nivelestudio;
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
public class CmdImportarArchAsig extends CmdImportarArchivo {

    public CmdImportarArchAsig(JMenuItem jmenu) {
        super(jmenu);
        nombreColumnas = Asignatura.COLUMNAS_ARCHIVO;
    }

    @Override
    public String importar() {
        //En esta variable se almacenan los errores a lo largo de la importación
        String errores = "";
        try {
            //Se utiliza para almacenar todas las filas del archivo leído
            ArrayList<String> archAsignaturas = Utilidades.leerArchivo(origen);
            //Se elimina la primera fila del archivo, que corresponde al nombre de las columnas
            //que ya ha sido verificada en el método verificarArchivo()
            archAsignaturas.remove(0);
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            //Se itera sobre cada una de las filas leídas del archivo, donde cada una corresponda a una asignatura
            int numeroDeImportado = 0;
            try {
                for (String cadaAsignatura : archAsignaturas) {
                    try {
                        //Almacena la posición del cursor dentro de cada fila
                        int posicion1 = 0;
                        //Representa el número de columna del archivo a obtener
                        int numeroDato = 0;
                        //Se emplea para almacenar los datos de cada fila de manera separada
                        String[] datosAsignatura = new String[4];
                        //Representa el cursor dentro de la linea a recorrer
                        int posicion2 = 0;
                        //Se recorre el contenido de cada fila
                        while (posicion2 < cadaAsignatura.length()) {
                            //Se evalua si en la posición que se encuentra el cursor, se encuentra un caracter
                            //separador (;) . Esto proporciona los límites del dato a leer dentro de la fila.
                            if (cadaAsignatura.charAt(posicion2) == CmdImportarArchivo.SIMBOLO) {
                                //Almacena en la posición del arreglo indicada, un substring de la linea que contiene el dato
                                datosAsignatura[numeroDato] = cadaAsignatura.substring(posicion1, posicion2);
                                //Se ubica el cursor delante del símbolo del separador Ej(, ; .) para continuar la búsqueda
                                posicion1 = posicion2 + 1;
                                //Se incrementa la posición en el arreglo
                                numeroDato++;
                            }
                            //Incrementa la posición del cursor 
                            posicion2++;
                        }
                        //Almacena el último dato en la última posición del arreglo
                        datosAsignatura[numeroDato] = cadaAsignatura.substring(posicion1, cadaAsignatura.length());                 
                        try {
                            Asignatura asignatura = new Asignatura(null, Integer.parseInt(datosAsignatura[0]), datosAsignatura[1].toUpperCase(), datosAsignatura[3].equalsIgnoreCase("activa") || datosAsignatura[6].equalsIgnoreCase("si") ? true : false);
                            //Se obtiene el objeto nivel de estudio que corresponde al que está almacenado en el arreglo
                            //datosAsignatura[2]: nivel de estudio de la asignatura
                            Nivelestudio nivelEstudio = (Nivelestudio) entityM.createNamedQuery("Nivelestudio.findByNivelEstudio").setParameter("nivelEstudio", datosAsignatura[2]).getSingleResult();
                            asignatura.setIdNivelEstudio(nivelEstudio);
                            //Se intenta guardar la asignatura creada en la BD, si es posible
                            try {
                                entityM.getTransaction().begin();
                                entityM.persist(asignatura);
                                entityM.getTransaction().commit();
                            } catch (RollbackException ex) {
                                //Si no es posible guardar la asignatura en la BD porque ya se encuentra allí almacenada
                                //(por ejemplo el caso en el que se importa el archivo nuevamente con algunos cambios), 
                                //se obtiene la asignatura de la BD para continuar con el proceso de vinculación a un plan de estudios
                                asignatura = (Asignatura) entityM.createNamedQuery("Asignatura.findByCodigoAsig").setParameter("codigoAsig", Integer.parseInt(datosAsignatura[0])).getSingleResult();
                                asignatura.setIdNivelEstudio(nivelEstudio);
                            }
                        } catch (NoResultException ex1) {
                            errores += "-No fue posible importar la asignatura " + datosAsignatura[1] + " , porque el nivel de estudio '" + datosAsignatura[2] + "' no existe." + '\n';
                            throw new RollbackException();
                        } catch (NumberFormatException ex) {
                            errores += "-No fue posible importar la asignatura " + datosAsignatura[1] + " , porque el código de asignatura '" + datosAsignatura[0] + "' debe ser numérico" + '\n';
                            throw new RollbackException();
                        }
                    } catch (RollbackException ex) {
                    }
                    numeroDeImportado++;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "El formato del archivo es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                errores = "Se importaron solamente las primeras " + numeroDeImportado + " asignaturas.";
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No fue posible leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            errores = "No se importó ningun dato.";
        }
        //Se llega a este punto una vez se ha recorrido todas las filas del archivo leído, donde cada una de
        //ellas representa una asignatura, y se retorna esta variable donde se han acumulado los errores encontrados
        //en el proceso de importación para cada una de las asignaturas.
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
