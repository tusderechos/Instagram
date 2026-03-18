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
import enums.EstadoPerfil;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import UI.Utils.PlaceHolder;
import interfaces.PerfilHeaderListener;

import javax.swing.*;
import java.awt.*;

public class PerfilHeader extends JPanel {
    
    private LabelImagenCircular LblFoto;
    private JLabel LblUsuario;
    private JLabel LblNombre;
    private JLabel LblPosts;
    private JLabel LblFollowers;
    private JLabel LblFollowing;
    private JLabel LblPrivacidad;
    private BotonRedondeado BtnAccion;
    
    private final PerfilHeaderListener Listener;
    
    public PerfilHeader(PerfilHeaderListener Listener) {
        this.Listener = Listener;
        
        setLayout(new BorderLayout(26, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        
        Image placeholder = PlaceHolder.CrearPlaceHolderCircular(UIConstantes.AVATAR_GRANDE, UIConstantes.AVATAR_GRANDE, "U");
        LblFoto = new LabelImagenCircular(placeholder);
        LblFoto.setPreferredSize(new Dimension(UIConstantes.AVATAR_GRANDE, UIConstantes.AVATAR_GRANDE));
        LblFoto.setMinimumSize(new Dimension(UIConstantes.AVATAR_GRANDE, UIConstantes.AVATAR_GRANDE));
        LblFoto.setMaximumSize(new Dimension(UIConstantes.AVATAR_GRANDE, UIConstantes.AVATAR_GRANDE));
        
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        izquierda.setOpaque(false);
        izquierda.setPreferredSize(new Dimension(190, 140));
        izquierda.add(LblFoto);
        
        JPanel derecha = new JPanel();
        derecha.setOpaque(false);
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        derecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel filatop = new JPanel(new BorderLayout(12, 0));
        filatop.setOpaque(false);
        filatop.setAlignmentX(Component.LEFT_ALIGNMENT);
        filatop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        
        LblUsuario = new JLabel("@usuario");
        LblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 24));
        LblUsuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        LblUsuario.setVerticalAlignment(SwingConstants.CENTER);
        
        BtnAccion = new BotonRedondeado("Seguir");
        BtnAccion.setPreferredSize(new Dimension(175, UIConstantes.ALTURA_BOTON_PEQUENO));
        BtnAccion.setMaximumSize(new Dimension(175, UIConstantes.ALTURA_BOTON_PEQUENO));
        BtnAccion.setMinimumSize(new Dimension(175, UIConstantes.ALTURA_BOTON_PEQUENO));
        BtnAccion.addActionListener(e -> {
            if (Listener != null) {
                Listener.onAccionPrincipal();
            }
        });
        
        filatop.add(LblUsuario, BorderLayout.WEST);
        filatop.add(BtnAccion, BorderLayout.EAST);
        
        JPanel filastats = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 0));
        filastats.setOpaque(false);
        filastats.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        LblPosts = CrearLabelStat("0 publicaciones");
        LblFollowers = CrearLabelStat("0 followers");
        LblFollowing = CrearLabelStat("0 following");
        
        filastats.add(LblPosts);
        filastats.add(LblFollowers);
        filastats.add(LblFollowing);
        
        LblNombre = new JLabel("Nombre completo");
        LblNombre.setFont(new Font("SansSerif", Font.BOLD, 15));
        LblNombre.setForeground(InstaColores.TEXTO_PRIMARIO);
        LblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        LblPrivacidad = new JLabel("Cuenta publica");
        LblPrivacidad.setFont(UIConstantes.PEQUENO_FONT);
        LblPrivacidad.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblPrivacidad.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        derecha.add(filatop);
        derecha.add(Box.createVerticalStrut(18));
        derecha.add(filastats);
        derecha.add(Box.createVerticalStrut(16));
        derecha.add(LblNombre);
        derecha.add(Box.createVerticalStrut(6));
        derecha.add(LblPrivacidad);
        
        add(izquierda, BorderLayout.WEST);
        add(derecha, BorderLayout.CENTER);
    }
    
    private JLabel CrearLabelStat(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setForeground(InstaColores.TEXTO_PRIMARIO);
        return lbl;
    }
    
    public void ActualizarDatos(Usuario usuario, EstadoPerfil estadoperfil, int posts, int followers, int following) {        
        if (usuario == null) {
            return;
        }
                
        LblUsuario.setText("@" + usuario.getUsuario());
        LblNombre.setText(usuario.getNombreCompleto());
        LblPosts.setText(posts + " publicaciones");
        LblFollowers.setText(followers + " followers");
        LblFollowing.setText(following + " following");
        
        String privacidad = usuario.getTipoCuenta().name().equals("PRIVADA") ? "Cuenta privada" : "Cuenta publica";
        LblPrivacidad.setText(privacidad);
        
        String inicial = usuario.getUsuario() != null && !usuario.getUsuario().isBlank() ? usuario.getUsuario().substring(0, 1).toUpperCase() : "U";
        
        if (usuario.getFotoPerfil() != null && !usuario.getFotoPerfil().isBlank()) {
            ImageIcon icono = new ImageIcon(usuario.getFotoPerfil());
            
            if (icono.getIconWidth() > 0) {
                LblFoto.setImage(icono.getImage());
            } else {
                LblFoto.setImage(PlaceHolder.CrearPlaceHolderCircular(UIConstantes.AVATAR_GRANDE, UIConstantes.AVATAR_GRANDE, inicial));
            }
        } else {
            LblFoto.setImage(PlaceHolder.CrearPlaceHolderCircular(UIConstantes.AVATAR_GRANDE, UIConstantes.AVATAR_GRANDE, inicial));
        }
        
        switch (estadoperfil) {
            case EDITAR_PERFIL -> BtnAccion.setText("Editar perfil");
            case SIGUIENDO -> BtnAccion.setText("Siguiendo");
            case SOLICITUD_ENVIADA -> BtnAccion.setText("Solicitud enviada");
            case SEGUIR -> BtnAccion.setText("Seguir");
            case NO_DISPONIBLE -> { 
                BtnAccion.setText("No disponible");
                BtnAccion.setEnabled(false);
                return;
            }
        }
        
        BtnAccion.setEnabled(true);
    }
}
