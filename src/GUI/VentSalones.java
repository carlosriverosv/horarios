/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdEliminarSalon;
import business.CmdModSalon;
import business.ButtonHandler;
import business.JChkVerOcultos;
import business.TablaSalones;
import data.Manejador;
import dataDB.Edificio;
import javax.persistence.EntityManager;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Carlos
 */
public class VentSalones extends VentTabla implements ListSelectionListener {

    /**
     * Creates new form VentSalones
     */

    public VentSalones(boolean editable, boolean eliminable) {
        super(editable, eliminable);
        initComponents();
        inicializarBotones(editable, eliminable);
        ListSelectionModel cellSelectionModel = tabla.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(this);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new TablaSalones();
        btnModificar = new javax.swing.JButton();
        jToolBar1 = new BarraHerramientasTabla(tabla, this);
        btnEliminar = new javax.swing.JButton();
        jChkVerOcultos = new JChkVerOcultos(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Salones");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());

        jScrollPane1.setViewportView(tabla);

        btnModificar.addActionListener(new ButtonHandler(new CmdModSalon(this)));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/editarPeque.png"))); // NOI18N
        btnModificar.setText("Modificar");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnEliminar.addActionListener(new ButtonHandler(new CmdEliminarSalon(this, btnEliminar)));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnEliminar.setText("Ocultar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jChkVerOcultos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(jChkVerOcultos))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /*
    private javax.swing.JButton btnEliminar;
    */
    /*
    private javax.swing.JButton btnModificar;
    */
    /*
    private javax.swing.JCheckBox jChkVerOcultos;
    */
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    /*
    private javax.swing.JTable tabla;
    */
    // End of variables declaration//GEN-END:variables

    public Object[] getSeleccionado() {
        try {
            return new Object[]{tabla.getValueAt(tabla.getSelectedRow(), 0), tabla.getModel().getValueAt(tabla.getSelectedRow(), 1)};
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void removerFila() {
        ((TablaSalones) tabla).removerFila();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            EntityManager entidadManejadora = Manejador.getManejador().gentEntityManager();
            if (!editable || !(((Edificio) (entidadManejadora.createNamedQuery("Edificio.findByNumeroEd").setParameter("numeroEd", getSeleccionado()[1]).getSingleResult())).getActivo())) {
                btnModificar.setEnabled(false);
                btnModificar.setToolTipText("No es posible modificar este sal�n porque el edificio al que pertence se encuentra inactivo. "
                        + System.lineSeparator() + "Si desea modificarlo, primero debe activar el edificio al que pertenece.");
            } else {
                btnModificar.setEnabled(true);
                btnModificar.setToolTipText("");
            }
        } catch (NullPointerException ex) {
        }
    }
}
