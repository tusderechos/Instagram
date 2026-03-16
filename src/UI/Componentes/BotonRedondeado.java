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
        
        Color fondo;
        
        if (!isEnabled()) {
            fondo = new Color(185, 185, 190);
        } else if (getModel().isPressed()) {
            fondo = InstaColores.AZUL_OSCURO;
        } else if (getModel().isRollover()) {
            fondo = InstaColores.AZUL.darker();
        } else {
            fondo = InstaColores.AZUL;
        }
        
        g2d.setColor(fondo);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), UIConstantes.ARCO_BOTON, UIConstantes.ARCO_BOTON));
        
        if (isEnabled()) {
            g2d.setColor(new Color(255, 255, 255, 35));
            g2d.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth() - 1, getHeight() - 1, UIConstantes.ARCO_BOTON, UIConstantes.ARCO_BOTON));
        }
        
        g2d.dispose();
        super.paintComponent(g);
    }
    
    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
    }
}
