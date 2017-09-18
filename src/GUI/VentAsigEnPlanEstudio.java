/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.ButtonHandler;
import business.TablaAsignaturas;
import dataDB.Planestudio;

/**
 *
 * @author Carlos
 */
public class VentAsigEnPlanEstudio extends VentTabla {

    private final Planestudio planEstudio;

    /**
     * Creates new form VentAsigEnPlanEstudio
     *
     * @param planEstudio representa el plan de estudio seleccionado para ver
     * sus asignaturas
     */
    public VentAsigEnPlanEstudio(Planestudio planEstudio, boolean editable, boolean eliminable) {
        super(editable, eliminable);
        this.planEstudio = planEstudio;
        initComponents();
        setTitle("Asignaturas del plan de estudio " + planEstudio.getCodigoPlan() + " - " + planEstudio.getNombrePlan());
        inicializarBotones(editable, eliminable);
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
        tabla = new TablaAsignaturas(planEstudio);
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jToolBar1 = new BarraHerramientasTabla(tabla, this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/calendario.png")).getImage());

        jScrollPane1.setViewportView(tabla);

        btnModificar.addActionListener(new ButtonHandler(new business.CmdModAsigPlanEstudio(this)));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/editarPeque.png"))); // NOI18N
        btnModificar.setText("Modificar");

        btnEliminar.addActionListener(new ButtonHandler(new business.CmdEliminarAsigPlanEstudio(this)));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/eliminarPeque.png"))); // NOI18N
        btnEliminar.setText("Eliminar");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    /*
    private javax.swing.JTable tabla;
    */
    // End of variables declaration//GEN-END:variables

    public int getSeleccionado() {
        try {
            return (Integer) tabla.getValueAt(tabla.getSelectedRow(), 0);
        } catch (IndexOutOfBoundsException ex) {
            return 0; //Significa que no se ha seleccionado niguna asignatura en el plan de estudios
        }
    }

    public Planestudio getPlanestudio() {
        return this.planEstudio;
    }
}