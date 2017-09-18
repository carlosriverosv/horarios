/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import generalities.Utilidades;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Carlos
 */
public abstract class Horario extends Tabla implements Observer {

    protected static Object[] horas = new Object[17];
    private int dia;
    private int hora;
    private int idProg;
    protected static final String SEPARADOR = "  ";
    protected int[][] cantidadItemsHorario;
    public static final int HORA_INICIAL = 6;
    public static final int HORA_FINAL = 22;
    public static final String NOMB_ARCHIVO_COLORES = "ConfColoresHorarios";

    public static Color UN_GRUPO;
    public static Color DOS_GRUPOS;
    public static Color TRES_GRUPOS;
    public static Color CUATRO_GRUPOS;
    public static Color CINCO_GRUPOS;

    public Horario(int idProg) {
        super();
        this.idProg = idProg;
        model.addColumn("Hora", crearHoras());
        model.addColumn("Lunes");
        model.addColumn("Martes");
        model.addColumn("Miércoles");
        model.addColumn("Jueves");
        model.addColumn("Viernes");
        model.addColumn("Sábado");
        model.addColumn("Domingo");
        setAutoscrolls(false);
        setCellSelectionEnabled(true);
        getTableHeader().setResizingAllowed(false);
        this.setModel(model);
        ajustarTamanoColumnas();
        setRenderColumnas();
        inicializarColores();
        //Configuración del tooltiptext
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(2000);
    }

    public static void inicializarColores() {
        Color[] colores = (Color[]) Utilidades.leerArchivo(NOMB_ARCHIVO_COLORES);
        if (colores != null) {
            UN_GRUPO = colores[0];
            DOS_GRUPOS = colores[1];
            TRES_GRUPOS = colores[2];
            CUATRO_GRUPOS = colores[3];
            CINCO_GRUPOS = colores[4];
        } else {
            UN_GRUPO = new Color(34, 139, 34);
            DOS_GRUPOS = new Color(255, 165, 0);
            TRES_GRUPOS = new Color(255, 215, 0);
            CUATRO_GRUPOS = Color.red;
            CINCO_GRUPOS = new Color(0, 191, 255);
        }
    }

    private void ajustarTamanoColumnas() {
        for (int columna = 0; columna < model.getColumnCount(); columna++) {
            this.getColumnModel().getColumn(columna).setPreferredWidth(60);
        }
    }

    private void setRenderColumnas() {
        for (int columna = 1; columna < model.getColumnCount(); columna++) {
            this.getColumnModel().getColumn(columna).setCellRenderer(new StatusColumnCellRenderer());
        }
    }

    private Object[] crearHoras() {
        for (int i = HORA_INICIAL; i <= HORA_FINAL; i++) {
            horas[i - HORA_INICIAL] = i + ":00";
        }
        return horas;
    }

    public DefaultTableModel getModelo() {
        return model;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setIdProg(int idProga) {
        idProg = idProga;
    }

    public int getIdProg() {
        return idProg;
    }

    public static int getNumeroDia(String dia) {
        if (dia.equalsIgnoreCase("lunes")) {
            return 1;
        } else if (dia.equalsIgnoreCase("martes")) {
            return 2;
        } else if (dia.equalsIgnoreCase("miercoles")) {
            return 3;
        } else if (dia.equalsIgnoreCase("jueves")) {
            return 4;
        } else if (dia.equalsIgnoreCase("viernes")) {
            return 5;
        } else if (dia.equalsIgnoreCase("sabado")) {
            return 6;
        } else {
            return 7;
        }
    }

    public String getNombreDia() {
        return getColumnName(getSelectedColumn()).toLowerCase();
    }

    public int getNumeroHora() {
        return hora + HORA_INICIAL;
    }

    public final void borrarContenido() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 1; j < getColumnCount(); j++) {
                setValueAt("", i, j);
                cantidadItemsHorario[i][j] = 0;
            }
        }
    }
    
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 15; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 300)
            width=300;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}

    @Override
    public final String getToolTipText(MouseEvent event) {
        Point p = event.getPoint();
        int fila = rowAtPoint(p);
        int columna = columnAtPoint(p);
        //Elimina el primer espacio y modifica todos los espacios por saltos de linea
        try {
            if (!getValueAt(fila, columna).toString().trim().isEmpty()) {
                if (!getValueAt(fila, columna).toString().contains(":00")) {
                    String texto = getValueAt(fila, columna).toString().substring(SEPARADOR.length()).replaceAll(SEPARADOR, "<br>");
                    return "<html>" + texto + "</html>";
                }
            }
            return "";
        } catch (NullPointerException ex) {
            return "";
        }
    }

    public class StatusColumnCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

            Component celda = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (isSelected) {
                celda.setBackground(selectionBackground);
            } else {
                switch (cantidadItemsHorario[row][col]) {
                    case 0:
                        celda.setBackground(Color.white);
                        break;
                    case 1:
                        celda.setBackground(Horario.UN_GRUPO);
                        break;
                    case 2:
                        celda.setBackground(Horario.DOS_GRUPOS);
                        break;
                    case 3:
                        celda.setBackground(Horario.TRES_GRUPOS);
                        break;
                    case 4:
                        celda.setBackground(Horario.CUATRO_GRUPOS);
                        break;
                    default:
                        celda.setBackground(Horario.CINCO_GRUPOS);
                }
            }
            return celda;
        }
    }
}
