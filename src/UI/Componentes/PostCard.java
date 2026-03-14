/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import modelo.Publicacion;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import UI.Utils.PlaceHolder;
import interfaces.PostCardListener;

import javax.swing.*;
import java.awt.*;

public class PostCard extends PanelRedondeado {
    
    private final Publicacion publicacion;
    private final PostCardListener Listener;
    
    public PostCard(Publicacion publicacion, PostCardListener Listener) {
        super(18);
        this.publicacion = publicacion;
        this.Listener = Listener;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(470, 700));
        setPreferredSize(new Dimension(470, 620));
        
        add(CrearHeader(), BorderLayout.NORTH);
        add(CrearCentro(), BorderLayout.CENTER);
        add(CrearFooter(), BorderLayout.SOUTH);
    }
    
    private JComponent CrearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        izquierda.setOpaque(false);
        
        Image imagen = PlaceHolder.CrearPlaceHolderCircular(36, 36, publicacion.getAutor().isBlank() ? "U" : publicacion.getAutor().substring(0, 1).toUpperCase());
        
        LabelImagenCircular foto = new LabelImagenCircular(imagen);
        foto.setPreferredSize(new Dimension(36, 36));
        
        JButton botonuser = new JButton(publicacion.getAutor());
        botonuser.setFont(new Font("SansSerif", Font.BOLD, 13));
        botonuser.setForeground(InstaColores.TEXTO_PRIMARIO);
        botonuser.setBorderPainted(false);
        botonuser.setContentAreaFilled(false);
        botonuser.setFocusPainted(false);
        botonuser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonuser.addActionListener(e -> {
            if (Listener != null) {
                Listener.onAbrirPerfil(publicacion.getAutor());
            }
        });
        
        izquierda.add(foto);
        izquierda.add(botonuser);
        
        header.add(izquierda, BorderLayout.WEST);
        return header;
    }
    
    private JComponent CrearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        
        JLabel imagen = new JLabel();
        imagen.setOpaque(true);
        imagen.setBackground(new Color(245, 245, 245));
        imagen.setHorizontalAlignment(SwingConstants.CENTER);
        imagen.setVerticalAlignment(SwingConstants.CENTER);
        imagen.setPreferredSize(new Dimension(470, 420));
        imagen.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, InstaColores.BORDER));
        
        if (publicacion.getRutaImagen() != null && !publicacion.getRutaImagen().isBlank()) {
            ImageIcon icono = new ImageIcon(publicacion.getRutaImagen());
            
            if (icono.getIconHeight() > 0) {
                Image escalada = icono.getImage().getScaledInstance(470, 420, Image.SCALE_SMOOTH);
                imagen.setIcon(new ImageIcon(escalada));
                imagen.setText("");
            } else {
                imagen.setText("Imagen no disponible");
                imagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
            }
        } else {
            imagen.setText("Sin Imagen");
            imagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
        }
        
        centro.add(imagen, BorderLayout.CENTER);
        return centro;
    }
    
    private JComponent CrearFooter() {
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBorder(BorderFactory.createEmptyBorder(12, 14, 14, 14));
        
        JLabel lblcontenido = new JLabel("<html><b>" + publicacion.getAutor() + "</b>" + Escapar(publicacion.getContenido()) + "</html>");
        lblcontenido.setFont(UIConstantes.TEXTO_FONT);
        lblcontenido.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblcontenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblfecha = new JLabel(publicacion.getFecha() + " " + publicacion.getHora());
        lblfecha.setFont(UIConstantes.PEQUENO_FONT);
        lblfecha.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblfecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        footer.add(lblcontenido);
        footer.add(Box.createVerticalStrut(8));
        footer.add(lblfecha);
        
        return footer;
    }
    
    private String Escapar(String texto) {
        if (texto == null) {
            return "";
        }
        
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
