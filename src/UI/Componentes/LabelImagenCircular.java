/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class LabelImagenCircular extends JLabel {
    
    private Image Imagen;
    
    public LabelImagenCircular(Image Imagen) {
        this.Imagen = Imagen;
        setPreferredSize(new Dimension(80, 80));
    }
    
    public void setImage(Image Imagen) {
        this.Imagen = Imagen;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (Imagen == null) {
            super.paintComponent(g);
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Shape circulo = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        g2d.setClip(circulo);
        g2d.drawImage(Imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
