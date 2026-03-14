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
import UI.Styles.UIConstantes;

import javax.swing.JButton;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BotonRedondeado extends JButton {
    
    public BotonRedondeado(String texto) {
        super(texto);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(UIConstantes.BOTON_FONT);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (!isEnabled()) {
            g2d.setColor(new Color(170, 170, 170));
        } else if (getModel().isPressed()) {
            g2d.setColor(InstaColores.AZUL.darker());
        } else {
            g2d.setColor(InstaColores.AZUL);
        }
        
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 18, 18));
        
        super.paintComponent(g2d);
    }
    
    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
    }
}
