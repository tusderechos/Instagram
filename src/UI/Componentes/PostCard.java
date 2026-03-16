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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PostCard extends PanelRedondeado {
    
    private final Publicacion publicacion;
    private final PostCardListener Listener;
    private final String UsuarioActual;
    
    private LikeService likeService;
    private ComentarioService comentarioService;
    
    private JButton BtnLike;
    private JButton BtnComentar;
    private JLabel LblLikes;
    private JLabel LblComentarios;
    
    public PostCard(Publicacion publicacion, PostCardListener Listener, String UsuarioActual) {
        super(18);
        this.publicacion = publicacion;
        this.Listener = Listener;
        this.UsuarioActual = UsuarioActual;
        
        likeService = new LikeService();
        comentarioService = new ComentarioService();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(470, 700));
        setPreferredSize(new Dimension(470, 620));
        
        add(CrearHeader(), BorderLayout.NORTH);
        add(CrearCentro(), BorderLayout.CENTER);
        add(CrearFooter(), BorderLayout.SOUTH);
        
        RefrescarEstadoInteracciones();
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
        
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        acciones.setOpaque(false);
        acciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        BtnLike = new JButton();
        BtnLike.setBorderPainted(false);
        BtnLike.setContentAreaFilled(false);
        BtnLike.setFocusPainted(false);
        BtnLike.setFont(new Font("SansSerif", Font.PLAIN, 18));
        BtnLike.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        BtnComentar = new JButton("💬");
        BtnComentar.setBorderPainted(false);
        BtnComentar.setContentAreaFilled(false);
        BtnComentar.setFocusPainted(false);
        BtnComentar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        BtnComentar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        acciones.add(Box.createHorizontalStrut(14));
        acciones.add(BtnComentar);
        acciones.add(LblComentarios);
        
        JLabel lblcontenido = new JLabel("<html><b>" + publicacion.getAutor() + " </b> " + Escapar(publicacion.getContenido()) + "</html>");
        lblcontenido.setFont(UIConstantes.TEXTO_FONT);
        lblcontenido.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblcontenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblfecha = new JLabel(FormatearFechaHora());
        lblfecha.setFont(UIConstantes.PEQUENO_FONT);
        lblfecha.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblfecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        footer.add(acciones);
        footer.add(Box.createVerticalStrut(8));
        footer.add(lblcontenido);
        footer.add(Box.createVerticalStrut(8));
        
        JPanel previewcomentarios = CrearPreviewComentarios();
        
        if (previewcomentarios.getComponentCount() > 0) {
            footer.add(previewcomentarios);
            footer.add(Box.createVerticalStrut(8));
        }
        
        footer.add(lblfecha);
               
        return footer;
    }
    
    private JPanel CrearPreviewComentarios() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        ArrayList<Comentario> comentarios = comentarioService.ListarComentarios(publicacion.getID(), publicacion.getAutor());
        
        int limite = Math.min(2, comentarios.size());
        
        for (int i = 0; i < limite; i++) {
            Comentario comentario = comentarios.get(i);
            
            JLabel lblcomentario = new JLabel("<html><b>" + Escapar(comentario.getUsuario()) + " </b> " + Escapar(comentario.getTexto()) + "</html>");
            lblcomentario.setFont(UIConstantes.PEQUENO_FONT);
            lblcomentario.setForeground(InstaColores.TEXTO_PRIMARIO);
            lblcomentario.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            panel.add(lblcomentario);
            panel.add(Box.createVerticalStrut(4));
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
            
            panel.add(btnvertodos);
        }
        
        return panel;
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
            BtnLike.setForeground(Color.RED);
        } else {
            BtnLike.setText("♡");
            BtnLike.setForeground(InstaColores.TEXTO_PRIMARIO);
        }
        
        int totallikes = likeService.ContarLikes(publicacion.getID(), publicacion.getAutor());
        int totalcomentarios = comentarioService.ListarComentarios(publicacion.getID(), publicacion.getAutor()).size();
        
        LblLikes.setText(totallikes + (totallikes == 1 ? " like" : " likes"));
        LblComentarios.setText(totalcomentarios + (totalcomentarios == 1 ? " comentario" : " comentarios"));
        
        revalidate();
        repaint();
    }
    
    private String FormatearFechaHora() {
        String fecha = publicacion.getFecha() != null ? publicacion.getFecha() : "";
        String hora = publicacion.getHora() != null ? publicacion.getHora() : "";
        
        if (hora.length() >= 5) {
            hora = hora.substring(0, 5);
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
