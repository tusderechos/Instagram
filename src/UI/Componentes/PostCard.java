/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import UI.Dialogos.ComentariosDialog;
import modelo.Publicacion;
import modelo.Comentario;
import service.ComentarioService;
import service.LikeService;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import UI.Utils.PlaceHolder;
import interfaces.PostCardListener;
import modelo.Usuario;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PostCard extends PanelRedondeado {
    
    private final Publicacion publicacion;
    private final PostCardListener Listener;
    private final String UsuarioActual;
    
    private final LikeService likeService;
    private final ComentarioService comentarioService;
    
    private final UsuarioService usuarioService;
    
    private JButton BtnLike;
    private JButton BtnComentar;
    private JLabel LblLikes;
    private JLabel LblComentarios;
    private JPanel PanelPreviewComentarios;
    
    public PostCard(Publicacion publicacion, PostCardListener Listener, String UsuarioActual) {
        super(22);
        this.publicacion = publicacion;
        this.Listener = Listener;
        this.UsuarioActual = UsuarioActual;
        usuarioService = new UsuarioService();
        
        likeService = new LikeService();
        comentarioService = new ComentarioService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.CARD);
        setMaximumSize(new Dimension(500, 760));
        setPreferredSize(new Dimension(470, 645));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        add(CrearHeader(), BorderLayout.NORTH);
        add(CrearCentro(), BorderLayout.CENTER);
        add(CrearFooter(), BorderLayout.SOUTH);
        
        RefrescarEstadoInteracciones();
    }
    
    private JComponent CrearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(14, 14, 12, 14));
        
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        izquierda.setOpaque(false);
        
        String inicial = publicacion.getAutor() != null && !publicacion.getAutor().isBlank() ? publicacion.getAutor().substring(0, 1).toUpperCase() : "U";
        
        Image imagenperfil = null;
        Usuario autor = usuarioService.BuscarUsuario(publicacion.getAutor());
        
        if (autor != null && autor.getFotoPerfil() != null && !autor.getFotoPerfil().isBlank()) {
            ImageIcon icono = new ImageIcon(autor.getFotoPerfil());
            
            if (icono.getIconWidth() > 0) {
                imagenperfil = icono.getImage();
            }
        }
        
        if (imagenperfil == null) {
            imagenperfil = PlaceHolder.CrearPlaceHolderCircular(UIConstantes.AVATAR_PEQUENO, UIConstantes.AVATAR_PEQUENO, inicial);
        }
        
        LabelImagenCircular foto = new LabelImagenCircular(imagenperfil);
        foto.setPreferredSize(new Dimension(UIConstantes.AVATAR_PEQUENO, UIConstantes.AVATAR_PEQUENO));
        foto.setMinimumSize(new Dimension(UIConstantes.AVATAR_PEQUENO, UIConstantes.AVATAR_PEQUENO));
        foto.setMaximumSize(new Dimension(UIConstantes.AVATAR_PEQUENO, UIConstantes.AVATAR_PEQUENO));
        
        JPanel bloquetexto = new JPanel();
        bloquetexto.setOpaque(false);
        bloquetexto.setLayout(new BoxLayout(bloquetexto, BoxLayout.Y_AXIS));
        
        JButton botonuser = new JButton(publicacion.getAutor());
        botonuser.setFont(new Font("SansSerif", Font.BOLD, 13));
        botonuser.setForeground(InstaColores.TEXTO_PRIMARIO);
        botonuser.setBorderPainted(false);
        botonuser.setContentAreaFilled(false);
        botonuser.setFocusPainted(false);
        botonuser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonuser.setMargin(new Insets(0, 0, 0, 0));
        botonuser.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonuser.addActionListener(e -> {
            if (Listener != null) {
                Listener.onAbrirPerfil(publicacion.getAutor());
            }
        });
        
        JLabel lblsub = new JLabel(FormatearFechaHora());
        lblsub.setFont(UIConstantes.PEQUENO_FONT);
        lblsub.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsub.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        bloquetexto.add(botonuser);
        bloquetexto.add(Box.createVerticalStrut(2));
        bloquetexto.add(lblsub);
        
        izquierda.add(foto);
        izquierda.add(bloquetexto);
        
        header.add(izquierda, BorderLayout.WEST);
        return header;
    }
    
    private JComponent CrearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        
        JLabel imagen = new JLabel();
        imagen.setOpaque(true);
        imagen.setBackground(InstaColores.FONDO_SECUNDARIO);
        imagen.setHorizontalAlignment(SwingConstants.CENTER);
        imagen.setVerticalAlignment(SwingConstants.CENTER);
        imagen.setPreferredSize(new Dimension(470, 420));
        imagen.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, InstaColores.DIVISOR));
        
        if (publicacion.getRutaImagen() != null && !publicacion.getRutaImagen().isBlank()) {
            ImageIcon icono = new ImageIcon(publicacion.getRutaImagen());
            
            if (icono.getIconHeight() > 0) {
                Image escalada = icono.getImage().getScaledInstance(470, 420, Image.SCALE_SMOOTH);
                imagen.setIcon(new ImageIcon(escalada));
                imagen.setText("");
            } else {
                imagen.setText("Imagen no disponible");
                imagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
                imagen.setFont(UIConstantes.TEXTO_FONT);
            }
        } else {
            imagen.setText("Sin Imagen");
            imagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
            imagen.setFont(UIConstantes.TEXTO_FONT);
        }
        
        centro.add(imagen, BorderLayout.CENTER);
        return centro;
    }
    
    private JComponent CrearFooter() {        
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));
        
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        acciones.setOpaque(false);
        acciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        BtnLike = new JButton();
        BtnLike.setBorderPainted(false);
        BtnLike.setContentAreaFilled(false);
        BtnLike.setFocusPainted(false);
        BtnLike.setFont(new Font("SansSerif", Font.PLAIN, 18));
        BtnLike.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BtnLike.setMargin(new Insets(0, 0, 0, 0));
        
        BtnComentar = new JButton("💬");
        BtnComentar.setBorderPainted(false);
        BtnComentar.setContentAreaFilled(false);
        BtnComentar.setFocusPainted(false);
        BtnComentar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        BtnComentar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BtnComentar.setMargin(new Insets(0, 0, 0, 0));
        
        LblLikes = new JLabel();
        LblLikes.setFont(UIConstantes.PEQUENO_FONT);
        LblLikes.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        LblComentarios = new JLabel();
        LblComentarios.setFont(UIConstantes.PEQUENO_FONT);
        LblComentarios.setForeground(InstaColores.TEXTO_PRIMARIO);

        BtnLike.addActionListener(e -> ManejarLike());
        BtnComentar.addActionListener(e -> AbrirComentarios());
        
        acciones.add(BtnLike);
        acciones.add(LblLikes);
        acciones.add(Box.createHorizontalStrut(10));
        acciones.add(BtnComentar);
        acciones.add(LblComentarios);
        
        JLabel lblcontenido = new JLabel("<html><div style='width:410px;'><b>" + publicacion.getAutor() + " </b> " + Escapar(publicacion.getContenido()) + "</div></html>");
        lblcontenido.setFont(UIConstantes.TEXTO_FONT);
        lblcontenido.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblcontenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        PanelPreviewComentarios = new JPanel();
        PanelPreviewComentarios.setOpaque(false);
        PanelPreviewComentarios.setLayout(new BoxLayout(PanelPreviewComentarios, BoxLayout.Y_AXIS));
        PanelPreviewComentarios.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblfecha = new JLabel(FormatearFechaHora());
        lblfecha.setFont(UIConstantes.PEQUENO_FONT);
        lblfecha.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblfecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        footer.add(acciones);
        footer.add(Box.createVerticalStrut(10));
        footer.add(lblcontenido);
        footer.add(Box.createVerticalStrut(10));
        footer.add(PanelPreviewComentarios);
        footer.add(Box.createVerticalStrut(6));
        footer.add(lblfecha);
        
        ReconstuirPreviewComentarios();
               
        return footer;
    }
    
    private void ReconstuirPreviewComentarios() {
        PanelPreviewComentarios.removeAll();
        
        ArrayList<Comentario> comentarios = comentarioService.ListarComentarios(publicacion.getID(), publicacion.getAutor());
        int limite = Math.min(2, comentarios.size());
        
        for (int i = 0; i < limite; i++) {
            Comentario comentario = comentarios.get(i);
            
            JLabel lblcomentario = new JLabel("<html><div style='width:410px;'><b>" + Escapar(comentario.getUsuario()) + " </b> " + Escapar(comentario.getTexto()) + "</div></html>");
            lblcomentario.setFont(UIConstantes.PEQUENO_FONT);
            lblcomentario.setForeground(InstaColores.TEXTO_PRIMARIO);
            lblcomentario.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            PanelPreviewComentarios.add(lblcomentario);
            PanelPreviewComentarios.add(Box.createVerticalStrut(5));
        }
        
        if (comentarios.size() > 2) {
            JButton btnvertodos = new JButton("Ver todos los comentarios");
            btnvertodos.setFont(UIConstantes.PEQUENO_FONT);
            btnvertodos.setForeground(InstaColores.TEXTO_SECUNDARIO);
            btnvertodos.setBorderPainted(false);
            btnvertodos.setContentAreaFilled(false);
            btnvertodos.setFocusPainted(false);
            btnvertodos.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnvertodos.setAlignmentX(Component.LEFT_ALIGNMENT);
            btnvertodos.addActionListener(e -> AbrirComentarios());
            
            PanelPreviewComentarios.add(btnvertodos);
        }
        
        PanelPreviewComentarios.revalidate();
        PanelPreviewComentarios.repaint();        
    }
    
    private void ManejarLike() {
        if (UsuarioActual == null || UsuarioActual.isBlank()) {
            return;
        }
        
        likeService.ToogleLike(publicacion.getID(), publicacion.getAutor(), UsuarioActual);
        RefrescarEstadoInteracciones();
    }
    
    private void AbrirComentarios() {
        Window ventana = SwingUtilities.getWindowAncestor(this);
        JFrame padre = ventana instanceof JFrame ? (JFrame) ventana : null;
        
        ComentariosDialog dialogo = new ComentariosDialog(padre, publicacion.getID(), publicacion.getAutor(), UsuarioActual);
        dialogo.setVisible(true);
        
        RefrescarEstadoInteracciones();
    }
    
    private void RefrescarEstadoInteracciones() {
        boolean diolike = false;
        
        if (UsuarioActual != null && !UsuarioActual.isBlank()) {
            diolike = likeService.UsuarioDioLike(publicacion.getID(), publicacion.getAutor(), UsuarioActual);
        }
        
        if (diolike) {
            BtnLike.setText("❤");
            BtnLike.setForeground(InstaColores.LIKE);
        } else {
            BtnLike.setText("♡");
            BtnLike.setForeground(InstaColores.TEXTO_PRIMARIO);
        }
        
        int totallikes = likeService.ContarLikes(publicacion.getID(), publicacion.getAutor());
        int totalcomentarios = comentarioService.ListarComentarios(publicacion.getID(), publicacion.getAutor()).size();
        
        LblLikes.setText(totallikes + (totallikes == 1 ? " like" : " likes"));
        LblComentarios.setText(totalcomentarios + (totalcomentarios == 1 ? " comentario" : " comentarios"));
        
        ReconstuirPreviewComentarios();
        
        revalidate();
        repaint();
    }
    
    private String FormatearFechaHora() {
        String fecha = publicacion.getFecha() != null ? publicacion.getFecha() : "";
        String hora = publicacion.getHora() != null ? publicacion.getHora() : "";
        
        if (hora.length() >= 5) {
            hora = hora.substring(0, 5);
        }
        
        if (fecha.isBlank() && hora.isBlank()) {
            return "";
        }
        
        return fecha + " " + hora;
    }
    
    private String Escapar(String texto) {
        if (texto == null) {
            return "";
        }
        
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
