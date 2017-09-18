/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import generalities.Utilidades;
import dataDB.Asignatura;
import dataDB.Asignaturaenplanestudio;
import dataDB.Planestudio;
import dataDB.Semestre;
import dataDB.Tipologia;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class CmdImportarPlanesEstudio extends CmdImportarArchivo {

    public CmdImportarPlanesEstudio(JMenuItem jmenu) {
        super(jmenu);
        nombreColumnas = Asignaturaenplanestudio.COLUMNAS_ARCHIVO.trim();
    }

    /*
     * Este método contiene el algoritmo para la importación del archivo de 
     * asignaturas.
     */

    @Override
    public String importar() {

        //En esta variable se almacenan los errores a lo largo de la importación
        String errores = "";
        try {
            //Se utiliza para almacenar todas las filas del archivo leído
            ArrayList<String> archAsignaturas = Utilidades.leerArchivo(origen);
            String nombreArchivo = origen.getName().replaceAll(" ", "");
            String codigoPlan = nombreArchivo.substring(0, nombreArchivo.indexOf("-"));
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
                        //Crea una asignatura con los datos almacenados en el arreglo
                        //datosAsignatura[0] : codigo asignatura
                        //datosAsignatura[1] : nombre de la asignatura
                        //datosAsignatura[2] : semestre
                        //datosAsignatura[3] : tipologia
                        try {
                            Asignatura asignatura = (Asignatura) entityM.createNamedQuery("Asignatura.findByCodigoAsig").setParameter("codigoAsig", Integer.parseInt(datosAsignatura[0])).getSingleResult();
                            try {
                                Planestudio planEstudio = (Planestudio) entityM.createNamedQuery("Planestudio.findByCodigoPlan").setParameter("codigoPlan", codigoPlan).getSingleResult();
                                try {
                                    Semestre semestre = (Semestre) entityM.createNamedQuery("Semestre.findByNumSemestre").setParameter("numSemestre", Integer.parseInt(datosAsignatura[2])).getSingleResult();
                                    try {
                                        Tipologia tipologia = (Tipologia) entityM.createNamedQuery("Tipologia.findByTipologia").setParameter("tipologia", datosAsignatura[3]).getSingleResult();
                                        Asignaturaenplanestudio asigEnPlan = new Asignaturaenplanestudio(asignatura.getIdAsignatura(), planEstudio.getIdPlan());
                                        entityM.getTransaction().begin();
                                        asigEnPlan.setAsignatura(asignatura);
                                        asigEnPlan.setIdTipologia(tipologia);
                                        asigEnPlan.setSemestre(semestre);
                                        asigEnPlan.setPlanestudio(planEstudio);
                                        List<Asignaturaenplanestudio> listaAsigEnPlan = asignatura.getAsignaturaenplanestudioList();
                                        listaAsigEnPlan.add(asigEnPlan);
                                        asignatura.setAsignaturaenplanestudioList(listaAsigEnPlan);
                                        entityM.persist(asigEnPlan);
                                        entityM.getTransaction().commit();
                                    } catch (NoResultException ex3) {
                                        if (!datosAsignatura[3].trim().isEmpty()) {
                                            errores += "-No fue posible vincular la asignatura " + datosAsignatura[1] + " al plan de estudios '" + codigoPlan + "', porque la tipología '" + datosAsignatura[3] + "' no existe." + '\n';
                                            throw new RollbackException();
                                        } else {
                                            errores += "-No fue posible vincular la asignatura " + datosAsignatura[1] + " al plan de estudios '" + codigoPlan + "', porque no hay un valor para la tipología " + '\n';
                                            throw new RollbackException();
                                        }
                                    }

                                } catch (NoResultException ex4) {
                                    Semestre semestre = new Semestre(Integer.parseInt(datosAsignatura[2]));
                                    entityM.getTransaction().begin();
                                    entityM.persist(semestre);
                                    entityM.getTransaction().commit();
                                    try {
                                        Tipologia tipologia = (Tipologia) entityM.createNamedQuery("Tipologia.findByTipologia").setParameter("tipologia", datosAsignatura[3]).getSingleResult();
                                        Asignaturaenplanestudio asigEnPlan = new Asignaturaenplanestudio(asignatura.getIdAsignatura(), planEstudio.getIdPlan());
                                        entityM.getTransaction().begin();
                                        asigEnPlan.setAsignatura(asignatura);
                                        asigEnPlan.setIdTipologia(tipologia);
                                        asigEnPlan.setSemestre(semestre);
                                        asigEnPlan.setPlanestudio(planEstudio);
                                        List<Asignaturaenplanestudio> listaAsigEnPlan = asignatura.getAsignaturaenplanestudioList();
                                        listaAsigEnPlan.add(asigEnPlan);
                                        asignatura.setAsignaturaenplanestudioList(listaAsigEnPlan);
                                        entityM.persist(asigEnPlan);
                                        entityM.getTransaction().commit();
                                    } catch (NoResultException ex3) {
                                        if (!datosAsignatura[3].trim().isEmpty()) {
                                            errores += "-No fue posible vincular la asignatura " + datosAsignatura[1] + " al plan de estudios '" + codigoPlan + "', porque la tipología '" + datosAsignatura[3] + "' no existe." + '\n';
                                            throw new RollbackException();
                                        } else {
                                            errores += "-No fue posible vincular la asignatura " + datosAsignatura[1] + " al plan de estudios '" + codigoPlan + "', porque no hay un valor para la tipología " + '\n';
                                            throw new RollbackException();
                                        }
                                    }
                                } catch (NumberFormatException ex5) {
                                    if (!datosAsignatura[2].trim().isEmpty()) {
                                        errores += "-No fue posible vincular la asignatura " + datosAsignatura[1] + " porque el valor del número de semestre debe ser numérico." + '\n';
                                        throw new RollbackException();
                                    } else {
                                        errores += "-No fue posible vincular la asignatura " + datosAsignatura[1] + " porque el número del semestre está vacío." + '\n';
                                        throw new RollbackException();
                                    }
                                }
                            } catch (NoResultException ex2) {
                                errores += "-No fue posible vincular la asignatura " + datosAsignatura[0] + ", al plan de estudios '" + codigoPlan + "', porque este no existe." + '\n';
                                throw new RollbackException();
                            }
                        } catch (NoResultException ex1) {
                            errores += "-No fue posible importar la asignatura " + datosAsignatura[0] + " , porque no existe." + '\n';
                            throw new RollbackException();
                        } catch (NumberFormatException ex) {
                            errores += "-No fue posible importar la asignatura " + datosAsignatura[1] + " , porque el código '" + datosAsignatura[0] + "' debe ser numérico." + '\n';
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
