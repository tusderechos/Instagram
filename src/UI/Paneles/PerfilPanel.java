/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import modelo.Publicacion;
import modelo.Usuario;
import enums.EstadoPerfil;
import service.FollowService;
import service.PerfilService;
import service.PublicacionService;
import service.SolicitudService;
import service.UsuarioService;
import UI.Componentes.PostCard;
import UI.Componentes.PerfilHeader;
import UI.Componentes.MessageBar;
import UI.Componentes.ConfirmDialog;
import UI.Core.SessionManager;
import interfaces.AppNavigator;
import UI.Styles.InstaColores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PerfilPanel extends JPanel {
    
    private MessageBar messageBar;
    
    private final SessionManager sessionManager;
    private final AppNavigator appNavigator;
    
    private final UsuarioService usuarioService;
    private final PerfilService perfilService;
    private final FollowService followService;
    private final SolicitudService solicitudService;
    private final PublicacionService publicacionService;
    
    private String UsuarioPerfilAbierto;
    
    private PerfilHeader Header;
    private JPanel PanelContenido;
    private JPanel PanelGrid;
    private JScrollPane ScrollPane;
    private JLabel LblEstadoPrivado;
    
    public PerfilPanel(SessionManager sessionManager, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.appNavigator = appNavigator;
        
        usuarioService = new UsuarioService();
        perfilService = new PerfilService();
        followService = new FollowService();
        solicitudService = new SolicitudService();
        publicacionService = new PublicacionService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        messageBar = new MessageBar();
        
        PanelContenido = new JPanel();
//        PanelContenido.setOpaque(false);
//        PanelContenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        PanelContenido.setBackground(InstaColores.FONDO);
        PanelContenido.setLayout(new BoxLayout(PanelContenido, BoxLayout.Y_AXIS));
        PanelContenido.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));

        Header = new PerfilHeader(this::ManejarAccionPrincipal);
        Header.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JSeparator separador = new JSeparator();
        separador.setForeground(InstaColores.BORDER);
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        LblEstadoPrivado = new JLabel("", SwingConstants.CENTER);
        LblEstadoPrivado.setFont(new Font("SansSerif", Font.PLAIN, 16));
        LblEstadoPrivado.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblEstadoPrivado.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblEstadoPrivado.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        
        PanelGrid = new JPanel(new GridLayout(0, 3, 6, 6));
        PanelGrid.setOpaque(false);
        PanelGrid.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JPanel gridwrapper = new JPanel(new BorderLayout());
        gridwrapper.setOpaque(false);
        gridwrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        gridwrapper.add(PanelGrid, BorderLayout.NORTH);
        
        PanelContenido.add(Header);
        PanelContenido.add(Box.createVerticalStrut(10));
        PanelContenido.add(separador);
        PanelContenido.add(Box.createVerticalStrut(20));
        PanelContenido.add(LblEstadoPrivado);
        PanelContenido.add(PanelGrid);
        
        ScrollPane = new JScrollPane(PanelContenido);
        ScrollPane.setBorder(null);
        ScrollPane.getViewport().setBackground(InstaColores.FONDO);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(messageBar, BorderLayout.NORTH);
        add(ScrollPane, BorderLayout.CENTER);
        
        LblEstadoPrivado.setText("Selecciona un perfil");
        LblEstadoPrivado.setVisible(true);
    }
    
    public void CargarPerfil(String usuarioperfil) {
        System.out.println("CargarPerfil ejecutado con: " + usuarioperfil);
        
        if (usuarioperfil == null || usuarioperfil.isBlank()) {
            MostrarPerfilNoDisponible();
            return;
        }
        
        UsuarioPerfilAbierto = usuarioperfil;
        
        String viewer = sessionManager.getUsuarioActual();
        Usuario usuario = usuarioService.BuscarUsuario(usuarioperfil);
        
        if (usuario == null || !usuario.isActivo()) {
            MostrarPerfilNoDisponible();
            return;
        }
        
        EstadoPerfil estadoperfil = perfilService.ObtenerEstadoPerfil(viewer, usuarioperfil);
        int posts = publicacionService.ContarPublicacionesDe(usuarioperfil);
        int followers = followService.ContarFollowersDe(usuarioperfil);
        int following = followService.ContarFollowingDe(usuarioperfil);
        
        Header.ActualizarDatos(usuario, estadoperfil, posts, followers, following);
        
        boolean puedeverposts = perfilService.PuedeVerPublicaciones(viewer, usuarioperfil);
        
        PanelGrid.removeAll();
        
        if (!puedeverposts) {
            LblEstadoPrivado.setText("Esta cuenta es privada. Debes seguirla para ver sus publicaciones");
            LblEstadoPrivado.setVisible(true);
        } else {
//            LblEstadoPrivado.setVisible(false);
            CargarGridPublicaciones(usuarioperfil);
        }
        
        PanelContenido.revalidate();
        PanelContenido.repaint();
        revalidate();
        repaint();
        
        SwingUtilities.invokeLater(() -> ScrollPane.getVerticalScrollBar().setValue(0));
    }
    
    private void MostrarPerfilNoDisponible() {
        PanelGrid.removeAll();
        LblEstadoPrivado.setText("Perfil no disponible");
        LblEstadoPrivado.setVisible(true);
        
        PanelContenido.revalidate();
        PanelContenido.repaint();
        revalidate();
        repaint();
    }
    
    private void CargarGridPublicaciones(String usuarioperfil) {
        ArrayList<Publicacion> publicaciones = publicacionService.ListarPublicacionesDe(usuarioperfil);
        
        if (publicaciones.isEmpty()) {
            LblEstadoPrivado.setText("Este usuario todavia no tiene publicaciones");
            LblEstadoPrivado.setVisible(true);
            return;
        }
        
        LblEstadoPrivado.setVisible(false);
        
        for (Publicacion post : publicaciones) {
            JPanel minipost = CrearMiniPost(post);
            PanelGrid.add(minipost);
        }
    }
    
    private JPanel CrearMiniPost(Publicacion post) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(220, 220));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(InstaColores.BORDER));
        
        JLabel lblimagen = new JLabel();
        lblimagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblimagen.setVerticalAlignment(SwingConstants.CENTER);
        lblimagen.setOpaque(true);
        lblimagen.setBackground(new Color(245, 245, 245));
        
        if (post.getRutaImagen() != null && !post.getRutaImagen().isBlank()) {
            ImageIcon icono = new ImageIcon(post.getRutaImagen());
            
            if (icono.getIconWidth() > 0) {
                Image escalada = icono.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
                lblimagen.setIcon(new ImageIcon(escalada));
            } else {
                lblimagen.setText("Imagen");
                lblimagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
            }
        } else {
            lblimagen.setText("Sin imagen");
            lblimagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
        }
        
        panel.add(lblimagen, BorderLayout.CENTER);
        
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MostrarDetallePost(post);
            }
        });
        
        return panel;
    }
    
    private void MostrarDetallePost(Publicacion post) {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Publicacion", true);
        dialogo.setSize(650, 720);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout());
        dialogo.getContentPane().setBackground(InstaColores.FONDO);
        
        PostCard card = new PostCard(post, usuario -> appNavigator.irAPerfil(usuario));
        dialogo.add(card, BorderLayout.CENTER);
        
        dialogo.setVisible(true);
    }
    
    private void ManejarAccionPrincipal() {
        String viewer = sessionManager.getUsuarioActual();
        
        if (viewer == null || UsuarioPerfilAbierto == null) {
            return;
        }
        
        EstadoPerfil estado = perfilService.ObtenerEstadoPerfil(viewer, UsuarioPerfilAbierto);
        
        switch (estado) {
            case EDITAR_PERFIL -> { 
                messageBar.MostrarInfo("Editor de perfil aun no disponible");
                ProgramarOcultarBarra();
            }
            case SEGUIR -> ManejarSeguir(viewer, UsuarioPerfilAbierto);
            case SOLICITUD_ENVIADA -> { 
                messageBar.MostrarInfo("Ya enviaste una solicitud a este usuario");
                ProgramarOcultarBarra();
            }
            case SIGUIENDO -> ManejarDejarDeSeguir(viewer, UsuarioPerfilAbierto);
            case NO_DISPONIBLE -> { 
                messageBar.MostrarInfo("Este perfil no esta disponible");
                ProgramarOcultarBarra();
            }
        }
        
        CargarPerfil(UsuarioPerfilAbierto);
    }
    
    private void ManejarSeguir(String viewer, String owner) {
        Usuario usuarioobjetivo = usuarioService.BuscarUsuario(owner);
        
        if (usuarioobjetivo == null) {
            return;
        }
        
        boolean exito;
        
        if (usuarioobjetivo.getTipoCuenta().name().equals("PUBLICA")) {
            exito = followService.Seguir(viewer, owner);
            
            if (exito) {
                messageBar.MostrarSuccess("Ahora sigues a @" + owner);
            } else {
                messageBar.MostrarError("No se pudo seguir a @" + owner);
            }
        } else {
            exito = solicitudService.EnviarSolicitud(viewer, owner);
            
            if (exito) {
                messageBar.MostrarInfo("Solicitud enviada a @" + owner);
            } else {
                messageBar.MostrarError("No se pudo enviar la solicitud");
            }
        }
        
        ProgramarOcultarBarra();
    }
    
    private void ManejarDejarDeSeguir(String viewer, String owner) {
        ConfirmDialog dialogo = new ConfirmDialog((Frame) SwingUtilities.getWindowAncestor(this), "Confirmar", "Quieres dejar de seuir a @" + owner + "?");
        dialogo.setVisible(true);
        
        if (dialogo.isConfirmado()) {
            boolean exito = followService.DejarDeSeguir(viewer, owner);
            
            if (exito) {
                messageBar.MostrarInfo("Has dejaro de seguir a @" + owner);
            } else {
                messageBar.MostrarError("No se pudo dejar de seguir");
            }
            
            ProgramarOcultarBarra();
        }
    }
    
    private void ProgramarOcultarBarra() {
        Timer timer = new Timer(3000, e -> messageBar.Ocultar());
        timer.setRepeats(false);
        timer.start();
    }
}
