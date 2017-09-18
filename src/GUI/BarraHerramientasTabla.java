/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import business.CmdExportar;
import business.CmdImprimir;
import business.ButtonHandler;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JToolBar;

/**
 *
 * @author Carlos
 */

/*
Esta clase modela la barra de herramientas que se encuentra en todas las
ventanas de visualización de datos. Por ejemplo, en la ventana de Docentes, en la
de asignaturas, en la de planes de estudio, etc. Contiene dos botones: el botón
de imprimir y el botón de exportar.
 */
public class BarraHerramientasTabla extends JToolBar {

    private final javax.swing.JButton btnExportar;
    private final javax.swing.JButton btnImprimir;

    /**
     *
     * @param tabla representa la tabla que se encuentra en la ventana donde
     * está la barra de herramientas
     * @param ventana es la ventana en la cual se encuentra la barra de
     * herramientas
     */
    public BarraHerramientasTabla(JTable tabla, JFrame ventana) {
        btnImprimir = new JButton();
        btnImprimir.addActionListener(new ButtonHandler(new CmdImprimir(tabla, ventana)));
        btnExportar = new JButton();
        btnExportar.addActionListener(new ButtonHandler(new CmdExportar(tabla, ventana)));
        setRollover(true);
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/imprimir.png"))); // NOI18N
        btnImprimir.setToolTipText("Imprimir");
        btnImprimir.setFocusable(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/iconos/exportar.png"))); // NOI18N
        btnExportar.setToolTipText("Exportar");
        btnExportar.setFocusable(false);
        btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btnImprimir);
        add(btnExportar);
    }
}
