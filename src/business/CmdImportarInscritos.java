/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import generalities.Utilidades;
import dataDB.Grupo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class CmdImportarInscritos implements CommandInterface {

    private String numProg;

    public CmdImportarInscritos(String numProg) {
        this.numProg = numProg;
    }

    @Override
    public void processEvent() {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".csv, .txt", "csv", "txt");
        String ubicacionArchivo = Utilidades.mostrarVentanaArchivo("Elija el archivo de inscritos a importar", JFileChooser.FILES_AND_DIRECTORIES, filtro, null);
        if (!ubicacionArchivo.isEmpty()) {
            File archivo = new File(ubicacionArchivo);
            try {
                String primeraLinea = Utilidades.leerLineaArchivo(archivo);
                if (primeraLinea.equalsIgnoreCase("Asignatura;Grupo;Inscritos")) {
                    ArrayList<String> archInscritos = Utilidades.leerArchivo(archivo);
                    archInscritos.remove(0);
                    EntityManager entityM = Manejador.getManejador().gentEntityManager();
                    String errores = "";
                    for (String fila : archInscritos) {
                        int posInicial = 0;
                        int numeroDato = 0;
                        String[] datosInscritos = new String[3];
                        int i = 0;
                        while (i < fila.length()) {
                            if (fila.charAt(i) == ';') {
                                datosInscritos[numeroDato] = fila.substring(posInicial, i);
                                posInicial = i + 1;
                                numeroDato++;
                            }
                            i++;
                        }
                        datosInscritos[numeroDato] = fila.substring(posInicial, fila.length());
                        try {
                            int codAsignatura = Integer.parseInt(datosInscritos[0]);
                            int numGrupo = Integer.parseInt(datosInscritos[1]);
                            int inscritos = Integer.parseInt(datosInscritos[2]);
                            Grupo grupo = (Grupo) entityM.createQuery("SELECT g FROM Grupo g WHERE g.asignatura.codigoAsig = :codAsig AND g.grupoPK.numeroGrupo = :numGrupo AND g.progacademica.numeroProg = :numProg").setParameter("codAsig", codAsignatura).setParameter("numGrupo", numGrupo).setParameter("numProg", numProg).getSingleResult();
                            entityM.getTransaction().begin();
                            grupo.setInscritos(inscritos);
                            entityM.getTransaction().commit();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Uno o más de los siguientes campos no es numérico "
                                    + "y por tanto no es posible realizar la importación de este registro " + "(Asignatura : " + datosInscritos[0]
                                    + " Grupo: " + datosInscritos[1] + " inscritos : " + datosInscritos[2] + "). ", "Resultado de importación", JOptionPane.ERROR_MESSAGE);
                        } catch (NoResultException ex) {
                            errores += "-La asignatura con código " + datosInscritos[0] + " no existe, o no existe el número de grupo " + datosInscritos[1] + " para esa asignatura." + '\n';
                        }
                    }
                    if (errores.equalsIgnoreCase("")) {
                        JOptionPane.showMessageDialog(null, "Fin de la importación del archivo. ", "Resultado de importación", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, errores, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El formato del archivo no es el especificado.", "Resultado de importación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Archivo no encontrado. " + ex, "Resultado de importación", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error leyendo el archivo. " + ex, "Resultado de importación", JOptionPane.ERROR_MESSAGE);
            }
        }
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
