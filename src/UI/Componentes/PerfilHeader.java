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
        
        setLayout(new BorderLayout(20, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Image placeholder = PlaceHolder.CrearPlaceHolderCircular(110, 110, "U");
        LblFoto = new LabelImagenCircular(placeholder);
        LblFoto.setPreferredSize(new Dimension(110, 110));
        
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        izquierda.setOpaque(false);
        izquierda.setPreferredSize(new Dimension(180, 130));
        izquierda.add(LblFoto);
        
        JPanel derecha = new JPanel();
        derecha.setOpaque(false);
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        
        JPanel filatop = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filatop.setOpaque(false);
        
        LblUsuario = new JLabel("@usuario");
        LblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 24));
        LblUsuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        BtnAccion = new BotonRedondeado("Seguir");
        BtnAccion.setPreferredSize(new Dimension(170, 38));
        BtnAccion.setMaximumSize(new Dimension(170, 38));
        BtnAccion.addActionListener(e -> {
            if (Listener != null) {
                Listener.onAccionPrincipal();
            }
        });
        
        filatop.add(LblUsuario);
        filatop.add(BtnAccion);
        
        JPanel filastats = new JPanel(new FlowLayout(FlowLayout.LEFT, 26, 0));
        filastats.setOpaque(false);
        
        LblPosts = CrearLabelStat("0 publicaciones");
        LblFollowers = CrearLabelStat("0 followers");
        LblFollowing = CrearLabelStat("0 following");
        
        filastats.add(LblPosts);
        filastats.add(LblFollowers);
        filastats.add(LblFollowing);
        
        LblNombre = new JLabel("Nombre completo");
        LblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        LblNombre.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        LblPrivacidad = new JLabel("Cuenta publica");
        LblPrivacidad.setFont(UIConstantes.PEQUENO_FONT);
        LblPrivacidad.setForeground(InstaColores.TEXTO_SECUNDARIO);
        
        derecha.add(filatop);
        derecha.add(Box.createVerticalStrut(16));
        derecha.add(filastats);
        derecha.add(Box.createVerticalStrut(14));
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
            
            System.out.println("Ancho icono: " + icono.getIconWidth());
            System.out.println("Alto icono: " + icono.getIconHeight());
            
            if (icono.getIconWidth() > 0) {
                LblFoto.setImage(icono.getImage());
            } else {
                LblFoto.setImage(PlaceHolder.CrearPlaceHolderCircular(110, 110, inicial));
            }
        } else {
            LblFoto.setImage(PlaceHolder.CrearPlaceHolderCircular(110, 110, inicial));
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
