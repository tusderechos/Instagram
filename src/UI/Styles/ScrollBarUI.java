/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Styles;

/**
 *
 * @author USUARIO
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ScrollBarUI extends BasicScrollBarUI {
    
    @Override
    protected void configureScrollBarColors() {
        thumbColor = new Color(199, 199, 204);
        trackColor = InstaColores.FONDO;
    }
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return CrearBotonInvisible();
    }
    
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return CrearBotonInvisible();
    }
    
    private JButton CrearBotonInvisible() {
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(0, 0));
        boton.setMinimumSize(new Dimension(0, 0));
        boton.setMaximumSize(new Dimension(0, 0));
        boton.setVisible(false);
        return boton;
    }
    
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(InstaColores.FONDO);
        g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2d.dispose();
    }
    
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int x = thumbBounds.x + 3;
        int y = thumbBounds.y + 3;
        int width = thumbBounds.width - 6;
        int height = thumbBounds.height - 6;
        
        g2d.setColor(new Color(199, 199, 204));
        g2d.fillRoundRect(x, y, width, height, 14, 14);
        
        g2d.dispose();
    }
    
    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(8, 40);
    }
}
