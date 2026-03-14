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

public class ConfirmDialog extends JDialog {
    
    private boolean Confirmado = false;
    
    public ConfirmDialog(Frame padre, String titulo, String mensaje) {
        super(padre, titulo, true);
        
        setSize(360, 200);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(InstaColores.FONDO);
        
        JLabel lblmensaje = new JLabel("<html>" + mensaje + "</html>", SwingConstants.CENTER);
        lblmensaje.setFont(UIConstantes.TEXTO_FONT);
        lblmensaje.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel botones = new JPanel(new FlowLayout());
        botones.setOpaque(false);
        
        BotonRedondeado btnsi = new BotonRedondeado("Si");
        BotonRedondeado btnno = new BotonRedondeado("No");
        
        btnsi.addActionListener(e -> {
            Confirmado = true;
            dispose();
        });
        
        btnno.addActionListener(e -> dispose());
        
        botones.add(btnsi);
        botones.add(btnno);
        
        add(lblmensaje, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }
    
    public boolean isConfirmado() {
        return Confirmado;
    }
}
