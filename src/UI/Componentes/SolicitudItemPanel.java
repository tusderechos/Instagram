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

import javax.swing.*;
import java.awt.*;

public class SolicitudItemPanel extends PanelRedondeado {
    
    public SolicitudItemPanel(String emisor, Runnable accionaceptar, Runnable accionrechazar) {
        super(UIConstantes.ARCO_CARD);
        setLayout(new BorderLayout(12, 0));
        setBackground(InstaColores.CARD);
        setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));
        
        JPanel panelizquierdo = new JPanel();
        panelizquierdo.setOpaque(false);
        panelizquierdo.setLayout(new BoxLayout(panelizquierdo, BoxLayout.Y_AXIS));
        
        JLabel lblusuario = new JLabel("@" + emisor);
        lblusuario.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblusuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblusuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblsub = new JLabel("Quiere seguir tu cuenta");
        lblsub.setFont(UIConstantes.PEQUENO_FONT);
        lblsub.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsub.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelizquierdo.add(lblusuario);
        panelizquierdo.add(Box.createVerticalStrut(4));
        panelizquierdo.add(lblsub);
        
        JPanel panelbotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        panelbotones.setOpaque(false);
        
        BotonRedondeado btnaceptar = new BotonRedondeado("Aceptar");
        btnaceptar.setPreferredSize(new Dimension(105, UIConstantes.ALTURA_BOTON_PEQUENO));
        btnaceptar.addActionListener(e -> accionaceptar.run());
        
        JButton btnrechazar = new JButton("Rechazar");
        btnrechazar.setFocusPainted(false);
        btnrechazar.setFont(UIConstantes.PEQUENO_FONT);
        btnrechazar.setForeground(InstaColores.TEXTO_PRIMARIO);
        btnrechazar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER_SUAVE), BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        btnrechazar.setContentAreaFilled(true);
        btnrechazar.setBackground(InstaColores.CARD);
        btnrechazar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnrechazar.addActionListener(e -> accionrechazar.run());
        
        panelbotones.add(btnaceptar);
        panelbotones.add(btnrechazar);
        
        add(lblusuario, BorderLayout.WEST);
        add(panelbotones, BorderLayout.EAST);
    }
}
