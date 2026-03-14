/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;

import javax.swing.*;
import java.awt.*;

public class PlaceHolderViewPanel extends JPanel {
    
    private JLabel LblTitulo;
    private JLabel LblDescripcion;
    
    public PlaceHolderViewPanel(String titulo, String descripcion) {
        setLayout(new GridBagLayout());
        setBackground(InstaColores.FONDO);
        
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        
        LblTitulo = new JLabel(titulo);
        LblTitulo.setFont(UIConstantes.TITULO_FONT);
        LblTitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        LblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        LblDescripcion = new JLabel(descripcion);
        LblDescripcion.setFont(UIConstantes.TEXTO_FONT);
        LblDescripcion.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contenido.add(LblTitulo);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(LblDescripcion);
        
        add(contenido);
    }
    
    public void setContenido(String titulo, String descripcion) {
        LblTitulo.setText(titulo);
        LblDescripcion.setText(descripcion);
    }
}
