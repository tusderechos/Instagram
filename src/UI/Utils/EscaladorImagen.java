/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Utils;

/**
 *
 * @author USUARIO
 */

import javax.swing.ImageIcon;
import java.awt.Image;

public final class EscaladorImagen {
    
    private EscaladorImagen() {
        
    }
    
    public static ImageIcon EscalarImagen(ImageIcon icono, int width, int height) {
        if (icono == null) {
            return null;
        }
        
        Image escalada = icono.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
}
