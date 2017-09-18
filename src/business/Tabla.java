/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Manejador;
import java.awt.Component;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public abstract class Tabla extends JTable {

    protected EntityManager entityM;
    protected DefaultTableModel model;
    private static final ImageIcon ACTIVO = new ImageIcon(Tabla.class.getResource("/GUI/iconos/aceptarPeque.png"));
    private static final ImageIcon INACTIVO = new ImageIcon(Tabla.class.getResource("/GUI/iconos/eliminarPeque.png"));

    public Tabla() {
        super();
        entityM = Manejador.getManejador().gentEntityManager();
        model = new DefaultTableModel();
        setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(true);
        setAutoResizeMode(AUTO_RESIZE_NEXT_COLUMN);
        setAutoscrolls(true);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    protected void vaciarTabla() {
        if (model.getRowCount() != 0) {
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        }
    }
    
    public void completarTabla(boolean oculto) {
        vaciarTabla();
    }

    protected static class ActivoRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setText("");
            setHorizontalAlignment(SwingConstants.CENTER);
            String status = (String) value;
            setIcon(status.equalsIgnoreCase("Activo") || status.equalsIgnoreCase("si") ? ACTIVO : INACTIVO);
            return this;
        }
    }
}
