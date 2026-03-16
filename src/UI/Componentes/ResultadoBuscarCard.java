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
        super(UIConstantes.ARCO_CARD);
        this.usuario = usuario;
        this.Listener = Listener;
        
        setLayout(new BorderLayout(14, 0));
        setBackground(InstaColores.CARD);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 92));
        setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        
        String inicial = (usuario.getUsuario() != null && !usuario.getUsuario().isBlank()) ? usuario.getUsuario().substring(0, 1).toUpperCase() : "U";
        
        Image imagen;
        
        if (usuario.getFotoPerfil() != null && !usuario.getFotoPerfil().isBlank()) {
            ImageIcon icono = new ImageIcon(usuario.getFotoPerfil());
            
            if (icono.getIconWidth() > 0) {
                imagen = icono.getImage();
            } else {
                imagen = PlaceHolder.CrearPlaceHolderCircular(UIConstantes.AVATAR_MEDIANO, UIConstantes.AVATAR_MEDIANO, inicial);
            }
        } else {
            imagen = PlaceHolder.CrearPlaceHolderCircular(UIConstantes.AVATAR_MEDIANO, UIConstantes.AVATAR_MEDIANO, inicial);
        }
        
        LabelImagenCircular foto = new LabelImagenCircular(imagen);
        foto.setPreferredSize(new Dimension(UIConstantes.AVATAR_MEDIANO, UIConstantes.AVATAR_MEDIANO));
        foto.setMinimumSize(new Dimension(UIConstantes.AVATAR_MEDIANO, UIConstantes.AVATAR_MEDIANO));
        foto.setMaximumSize(new Dimension(UIConstantes.AVATAR_MEDIANO, UIConstantes.AVATAR_MEDIANO));
        
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        
        JLabel lblusuario = new JLabel("@" + usuario.getUsuario());
        lblusuario.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblusuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblusuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblnombre = new JLabel(usuario.getNombreCompleto());
        lblnombre.setFont(UIConstantes.PEQUENO_FONT);
        lblnombre.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblnombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        centro.add(Box.createVerticalGlue());
        centro.add(lblusuario);
        centro.add(Box.createVerticalStrut(4));
        centro.add(lblnombre);
        centro.add(Box.createVerticalGlue());
        
        BotonRedondeado btnabrir = new BotonRedondeado("Ver perfil");
        btnabrir.setPreferredSize(new Dimension(130, UIConstantes.ALTURA_BOTON_PEQUENO));
        btnabrir.setMaximumSize(new Dimension(130, UIConstantes.ALTURA_BOTON_PEQUENO));
        btnabrir.addActionListener(e -> {
            if (Listener != null) {
                Listener.onAbrirPerfil(usuario.getUsuario());
            }
        });
        
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 9));
        derecha.setOpaque(false);
        derecha.add(btnabrir);
        
        add(foto, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
        add(derecha, BorderLayout.EAST);
    }
}
