/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import modelo.Usuario;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import UI.Utils.PlaceHolder;
import interfaces.ResultadoBusquedaListener;

import javax.swing.*;
import java.awt.*;

public class ResultadoBuscarCard extends PanelRedondeado {
    
    private final Usuario usuario;
    private final ResultadoBusquedaListener Listener;
    
    public ResultadoBuscarCard(Usuario usuario, ResultadoBusquedaListener Listener) {
        super(18);
        this.usuario = usuario;
        this.Listener = Listener;
        
        setLayout(new BorderLayout(12, 6));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        String inicial = (usuario.getUsuario() != null && !usuario.getUsuario().isBlank()) ? usuario.getUsuario().substring(0, 1).toUpperCase() : "U";
        
        Image imagen;
        
        if (usuario.getFotoPerfil() != null && !usuario.getFotoPerfil().isBlank()) {
            ImageIcon icono = new ImageIcon(usuario.getFotoPerfil());
            
            if (icono.getIconWidth() > 0) {
                imagen = icono.getImage();
            } else {
                imagen = PlaceHolder.CrearPlaceHolderCircular(54, 54, inicial);
            }
        } else {
            imagen = PlaceHolder.CrearPlaceHolderCircular(54, 54, inicial);
        }
        
        LabelImagenCircular foto = new LabelImagenCircular(imagen);
        foto.setPreferredSize(new Dimension(54, 54));
        foto.setMinimumSize(new Dimension(54, 54));
        foto.setMaximumSize(new Dimension(54, 54));
        
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        
        JLabel lblusuario = new JLabel("@" + usuario.getUsuario());
        lblusuario.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblusuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        JLabel lblnombre = new JLabel(usuario.getNombreCompleto());
        lblnombre.setFont(UIConstantes.PEQUENO_FONT);
        lblnombre.setForeground(InstaColores.TEXTO_SECUNDARIO);
        
        centro.add(lblusuario);
        centro.add(Box.createVerticalStrut(4));
        centro.add(lblnombre);
        
        BotonRedondeado btnabrir = new BotonRedondeado("Ver perfil");
        btnabrir.setPreferredSize(new Dimension(130, 36));
        btnabrir.setMaximumSize(new Dimension(130, 36));
        btnabrir.addActionListener(e -> {
            if (Listener != null) {
                Listener.onAbrirPerfil(usuario.getUsuario());
            }
        });
        
        add(foto, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
        add(btnabrir, BorderLayout.EAST);
    }
}
