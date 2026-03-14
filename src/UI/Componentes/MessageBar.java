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

public class MessageBar extends JPanel{
    
    private JLabel LblMensaje;
    
    public MessageBar() {
        setLayout(new BorderLayout());
        setVisible(false);
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        
        LblMensaje = new JLabel("");
        LblMensaje.setFont(UIConstantes.TEXTO_FONT);
        LblMensaje.setForeground(Color.WHITE);
        
        add(LblMensaje, BorderLayout.CENTER);
    }
    
    public void MostrarInfo(String mensaje) {
        setBackground(InstaColores.AZUL);
        LblMensaje.setText(mensaje);
        setVisible(true);
    }
    
    public void MostrarError(String mensaje) {
        setBackground(InstaColores.ERROR);
        LblMensaje.setText(mensaje);
        setVisible(true);
    }
    
    public void MostrarSuccess(String mensaje) {
        setBackground(InstaColores.SUCCESS);
        LblMensaje.setText(mensaje);
        setVisible(true);
    }
    
    public void Ocultar() {
        setVisible(false);
    }
}
