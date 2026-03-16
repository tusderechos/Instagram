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
    
    private final JLabel LblIcono;
    private final JLabel LblMensaje;
    
    public MessageBar() {
        setLayout(new BorderLayout());
        setVisible(false);
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        
        LblIcono = new JLabel("i");
        LblIcono.setFont(new Font("SansSerif", Font.BOLD, 15));
        LblIcono.setForeground(Color.WHITE);
        LblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        LblIcono.setPreferredSize(new Dimension(20, 20));
        
        LblMensaje = new JLabel("");
        LblMensaje.setFont(UIConstantes.TEXTO_FONT);
        LblMensaje.setForeground(Color.WHITE);
        
        add(LblIcono, BorderLayout.WEST);
        add(LblMensaje, BorderLayout.CENTER);
    }
    
    public void MostrarInfo(String mensaje) {
        AplicarEstado(InstaColores.AZUL_OSCURO, "i", mensaje);
    }
    
    public void MostrarError(String mensaje) {
        AplicarEstado(InstaColores.ERROR, "!", mensaje);
    }
    
    public void MostrarSuccess(String mensaje) {
        AplicarEstado(InstaColores.SUCCESS, "✔", mensaje);
    }
    
    public void MostrarWarning(String mensaje) {
        AplicarEstado(InstaColores.WARNING, "!", mensaje);
    }
    
    private void AplicarEstado(Color fondo, String icono, String mensaje) {
        setBackground(fondo);
        LblIcono.setText(icono);
        LblMensaje.setText(mensaje != null ? mensaje : "");
        setVisible(true);
        revalidate();
        repaint();
    }
    
    public void Ocultar() {
        setVisible(false);
        LblMensaje.setText("");
    }
}
