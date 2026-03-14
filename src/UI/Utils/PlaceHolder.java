/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Utils;

/**
 *
 * @author USUARIO
 */

import UI.Styles.InstaColores;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class PlaceHolder {
    
    private PlaceHolder() {
        
    }
    
    public static Image CrearPlaceHolderCircular(int width, int height, String texto) {
        BufferedImage imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagen.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(new Color(230, 230, 230));
        g2d.fillOval(0, 0, width, height);
        
        g2d.setColor(InstaColores.TEXTO_SECUNDARIO);
        g2d.setFont(new Font("SansSerif", Font.BOLD, Math.max(14, width / 4)));
        
        FontMetrics fm = g2d.getFontMetrics();
        int textowidth = fm.stringWidth(texto);
        int textoheight = fm.getAscent();
        
        g2d.drawString(texto, (width - textowidth) / 2, (height - textoheight) / 2 - 4);
        
        return imagen;
    }
}
