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

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class LabelImagenCircular extends JLabel {
    
    private Image Imagen;
    
    public LabelImagenCircular(Image Imagen) {
        this.Imagen = Imagen;
        setPreferredSize(new Dimension(80, 80));
        setMinimumSize(new Dimension(80, 80));
        setMaximumSize(new Dimension(80, 80));
        setOpaque(false);
    }
    
    public void setImage(Image Imagen) {
        this.Imagen = Imagen;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        g2d.setColor(InstaColores.FONDO_SECUNDARIO);
        g2d.fillOval(0, 0, w - 1, h - 1);
        
        if (Imagen != null) {
            Shape circulo = new Ellipse2D.Double(0, 0, w, h);
            g2d.setClip(circulo);
            g2d.drawImage(Imagen, 0, 0, w, h, this);
            g2d.setClip(null);
        }
        
        g2d.setColor(InstaColores.BORDER_SUAVE);
        g2d.drawOval(0, 0, w - 1, h - 1);
        
        g2d.dispose();
        super.paintComponent(g);
    }
}
