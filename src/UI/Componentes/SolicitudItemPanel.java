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
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SolicitudItemPanel extends JPanel {
    
    public SolicitudItemPanel(String emisor, Runnable accionaceptar, Runnable accionrechazar) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), new EmptyBorder(12, 14, 12, 14)));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel lblusuario = new JLabel("@" + emisor);
        lblusuario.setFont(UIConstantes.TEXTO_FONT);
        lblusuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        JPanel panelbotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelbotones.setOpaque(false);
        
        JButton btnaceptar = new JButton("Aceptar");
        btnaceptar.setFocusPainted(false);
        btnaceptar.setFont(UIConstantes.PEQUENO_FONT);
        btnaceptar.addActionListener(e -> accionaceptar.run());
        
        JButton btnrechazar = new JButton("Rechazar");
        btnrechazar.setFocusPainted(false);
        btnrechazar.setFont(UIConstantes.PEQUENO_FONT);
        btnrechazar.addActionListener(e -> accionrechazar.run());
        
        panelbotones.add(btnaceptar);
        panelbotones.add(btnrechazar);
        
        add(lblusuario, BorderLayout.WEST);
        add(panelbotones, BorderLayout.EAST);
    }
}
