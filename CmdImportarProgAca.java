/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import generalities.Utilidades;
import dataDB.Asignatura;
import dataDB.Dia;
import dataDB.Docente;
import dataDB.Grupo;
import dataDB.Horainicio;
import dataDB.Itemhorario;
import dataDB.Progacademica;
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
public class CmdImportarProgAca extends CmdImportarArchivo {

    public CmdImportarProgAca(JMenuItem jmenu) {
        super(jmenu);
        nombreColumnas = Progacademica.columnasArchivo.replace(" ", "");
    }

    @Override
    public String importar() {
        int limiteMax = 0;
        int cuotas = 0;
        int capMax = 0;
        int capMin = 0;
        String principalOAsistente;
        boolean noLeer = true;
        String errores = "";
        Progacademica progAca;
        try {
            ArrayList<String> archProgAca = Utilidades.leerArchivo(origen);
            archProgAca.remove(0);
            EntityManager entityM = Manejador.getManejador().gentEntityManager();
            int numeroDeImportado = 0;
            String nombreProg = origen.getName().replace(".csv", "");
            try {
                progAca = new Progacademica(null, nombreProg);
                entityM.getTransaction().begin();
                entityM.persist(progAca);
                entityM.getTransaction().commit();
            } catch (RollbackException ex) {
                int respuesta = JOptionPane.showConfirmDialog(null, "Ya existe un programación académica con este nombre (" + nombreProg + "). ¿Desea sobreescribirla? Si no es así, modifique el nombre del archivo e impórtelo nuevamente.", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    progAca = (Progacademica) entityM.createNamedQuery("Progacademica.findByNumeroProg").setParameter("numeroProg", nombreProg).getSingleResult();
                } else {
                    noLeer = false;
                    throw new IOException();
                }
            }
            try {
                for (String cadaItemHorario : archProgAca) {
                    int posInicial = 0;
                    int numeroDato = 0;
                    String[] datosItemHorario = new String[Progacademica.nombreColumnas.length];
                    int i = 0;
                    while (i < cadaItemHorario.length()) {
                        if (cadaItemHorario.charAt(i) == CmdImportarArchivo.SIMBOLO) {
                            datosItemHorario[numeroDato] = cadaItemHorario.substring(posInicial, i);
                            posInicial = i + 1;
                            numeroDato++;
                        }
                        i++;
                    }
                    datosItemHorario[numeroDato] = cadaItemHorario.substring(posInicial, cadaItemHorario.length());
                    try {
                        try {
                            Asignatura asignatura = (Asignatura) entityM.createNamedQuery("Asignatura.findByCodigoAsig").setParameter("codigoAsig", Integer.parseInt(datosItemHorario[0])).getSingleResult();
                            String planesAsignatura = asignatura.getPlanesEstudioVinculados().replaceAll(" ", "");
                            int cursor1 = 0;
                            posInicial = 0;
                            while (cursor1 < planesAsignatura.length()) {
                                if (planesAsignatura.charAt(cursor1) == ',') {
                                    String plan = planesAsignatura.substring(posInicial, cursor1);
                                    posInicial = cursor1 + 1;
                                    if (!planesAsignatura.contains(plan)) {
                                        throw new NoResultException();
                                    }
                                }
                                cursor1++;
                            }
                            Grupo grupo = null;
                            int numGrupo = 0;
                            try {
                                numGrupo = Integer.parseInt(datosItemHorario[2]);
                                grupo = new Grupo(numGrupo, progAca.getIdProg(), asignatura.getIdAsignatura());
                                grupo.setAsignatura(asignatura);
                                grupo.setInscritos(0);
                                entityM.getTransaction().begin();
                                entityM.persist(grupo);
                                entityM.getTransaction().commit();
                            } catch (RollbackException ex) {
                                grupo = (Grupo) entityM.createQuery("SELECT g FROM Grupo g WHERE g.grupoPK.idAsignatura =:idAsignatura AND g.grupoPK.idProg =:idProg AND g.grupoPK.numeroGrupo =:numeroGrupo").setParameter("idAsignatura", asignatura.getIdAsignatura()).setParameter("idProg", progAca.getIdProg()).setParameter("numeroGrupo", numGrupo).getSingleResult();
                            } catch (NumberFormatException ex) {
                                errores += "El valor del grupo para la asignatura " + datosItemHorario[0] + " debe ser un número " + '\n';
                                throw new RollbackException();
                            }

                            try {
                                Docente docente = (Docente) entityM.createNamedQuery("Docente.findByDocumentoDoc").setParameter("documentoDoc", Integer.parseInt(datosItemHorario[11])).getSingleResult();
                                try {
                                    Salon salon = (Salon) entityM.createQuery("SELECT s FROM Salon s WHERE s.numeroSalon =:numSalon AND s.idEdificio.numeroEd =:numEdif").setParameter("numSalon", datosItemHorario[10]).setParameter("numEdif", datosItemHorario[9]).getSingleResult();
                                    try {
                                        Dia dia = (Dia) entityM.createNamedQuery("Dia.findByDia").setParameter("dia", datosItemHorario[17]).getSingleResult();
                                        try {
                                            if (planesAsignatura.trim().isEmpty()) {
                                                limiteMax = 0;
                                                cuotas = 0;
                                                capMax = 0;
                                                capMin = 0;
                                                principalOAsistente = "";
                                            } else {
                                                limiteMax = Integer.parseInt(datosItemHorario[3]);
                                                cuotas = Integer.parseInt(datosItemHorario[5]);
                                                capMax = Integer.parseInt(datosItemHorario[15]);
                                                capMin = Integer.parseInt(datosItemHorario[16]);
                                                principalOAsistente = datosItemHorario[13];
                                            }
                                            try {
                                                int horaI = Integer.parseInt(datosItemHorario[7].substring(0, datosItemHorario[7].length() - 3));
                                                Horainicio horaInicio = (Horainicio) entityM.createNamedQuery("Horainicio.findByHoraInicio").setParameter("horaInicio", horaI).getSingleResult();
                                                int horaF = Integer.parseInt(datosItemHorario[8].substring(0, datosItemHorario[8].length() - 3));
                                                Horainicio horaFinal = (Horainicio) entityM.createNamedQuery("Horainicio.findByHoraInicio").setParameter("horaInicio", horaF).getSingleResult();
                                                for (int hora = horaI; hora <= horaF - 1; hora++) {
                                                    Itemhorario itemH = new Itemhorario(dia.getDia(), hora, progAca.getIdProg(), numGrupo, asignatura.getIdAsignatura());
                                                    itemH.setGrupo(grupo);
                                                    itemH.setIdDocente(docente);
                                                    itemH.setIdSalon(salon);
                                                    itemH.setDia1(dia);
                                                    Horainicio horaItem = (Horainicio) entityM.createNamedQuery("Horainicio.findByHoraInicio").setParameter("horaInicio", hora).getSingleResult();
                                                    itemH.setHorainicio(horaItem);
                                                    itemH.setLimiteMax(limiteMax);
                                                    itemH.setCuotas(cuotas);
                                                    itemH.setCapMax(capMax);
                                                    itemH.setCapMin(capMin);
                                                    itemH.setPrincipalOAsistente(principalOAsistente);
                                                    entityM.getTransaction().begin();
                                                    entityM.persist(itemH);
                                                    entityM.getTransaction().commit();
                                                    itemH = null;
                                                }
                                            } catch (NoResultException ex) {
                                                errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque las horas no son válidas." + '\n';
                                                throw new RollbackException();
                                            } catch (NumberFormatException ex) {
                                                errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque los valores de las horas inicial y final deben ser numéricos." + '\n';
                                                throw new RollbackException();
                                            }
                                        } catch (NumberFormatException ex) {
                                            errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque uno o más de los valores: límite máximo, cuotas, capacidad máxima, caácidad minima no son numéricos." + '\n';
                                            throw new RollbackException();
                                        }
                                    } catch (NoResultException ex) {
                                        errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque el día '" + datosItemHorario[10] + "' no es válido." + '\n';
                                        throw new RollbackException();
                                    }
                                } catch (NoResultException ex) {
                                    errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque el salón " + datosItemHorario[10] + " del edificio " + datosItemHorario[9] + " no se encuentra registrado." + '\n';
                                    throw new RollbackException();
                                }
                            } catch (NoResultException ex) {
                                errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque el Docente con número de identificación " + datosItemHorario[11] + " no se encuentra registrado." + '\n';
                                throw new RollbackException();
                            } catch (NumberFormatException ex) {
                                errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " Grupo " + numGrupo + " porque la identificación del Docente (" + datosItemHorario[11] + " )debe ser numérica" + '\n';
                                throw new RollbackException();
                            }
                        } catch (NoResultException ex) {
                            errores += "-No fue posible crear el item de horario de la asignatura " + datosItemHorario[0] + " porque esta no existe o porque no se encuentra vinculada a los planes de estudio " + datosItemHorario[4] + '\n';
                            throw new RollbackException();
                        } catch (NumberFormatException ex) {
                            errores += "-No fue posible crear el item de horario porque el código de la asignatura '" + datosItemHorario[0] + "' debe ser un número" + '\n';
                            throw new RollbackException();
                        }
                    } catch (RollbackException ex) {
                    }
                    numeroDeImportado++;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "El formato del archivo es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                errores = "Se importaron solamente las primeras " + numeroDeImportado + " filas del archivo.";
            }
        } catch (IOException ex) {
            if (noLeer) {
                JOptionPane.showMessageDialog(null, "No fue posible leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
