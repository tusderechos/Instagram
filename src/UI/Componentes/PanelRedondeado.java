/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import UI.Styles.InstaColores;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PanelRedondeado extends JPanel {
    
    private final int Arco;
    private boolean MostrarSombra;
    
    public PanelRedondeado(int Arco) {
        this.Arco = Arco;
        MostrarSombra = true;
        setOpaque(false);
        setBackground(InstaColores.CARD);
    }
    
    public void setMostrarSombra(boolean MostrarSombra) {
        this.MostrarSombra = MostrarSombra;
        repaint();
    } 
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int ancho = getWidth();
        int alto = getHeight();
        
        if (MostrarSombra) {
            g2d.setColor(InstaColores.SOMBRA_MINIMA);
            g2d.fill(new RoundRectangle2D.Double(1, 2, ancho - 2, alto - 2, Arco, Arco));
        }
        
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, ancho - 1, alto - 1, Arco, Arco));
        
        g2d.dispose();
        super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(InstaColores.BORDER_SUAVE);
        g2d.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth() - 2, getHeight() - 2, Arco, Arco));
        
        g2d.dispose();
    }
}
