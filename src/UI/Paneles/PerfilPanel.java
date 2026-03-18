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
import UI.Componentes.PanelRedondeado;
import UI.Componentes.BotonRedondeado;
import UI.Componentes.EditarPerfilDialog;
import UI.Core.SessionManager;
import interfaces.AppNavigator;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import interfaces.NavigationListener;

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
    
    private final NavigationListener navigationListener;
    
    private String UsuarioPerfilAbierto;
    
    private PerfilHeader Header;
    private JPanel PanelContenido;
    private JPanel PanelGrid;
    private JScrollPane ScrollPane;
    private JLabel LblEstadoPrivado;
    
    private JButton BtnMensaje;
    private JButton BtnDesactivarCuenta;
    private JPanel PanelAccionesSecundarias;
    
    public PerfilPanel(SessionManager sessionManager, AppNavigator appNavigator, NavigationListener navigationListener) {
        this.sessionManager = sessionManager;
        this.appNavigator = appNavigator;
        this.navigationListener = navigationListener;
        
        usuarioService = new UsuarioService();
        perfilService = new PerfilService();
        followService = new FollowService();
        solicitudService = new SolicitudService();
        publicacionService = new PublicacionService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        messageBar = new MessageBar();
        
        PanelContenido = new JPanel();
        PanelContenido.setBackground(InstaColores.FONDO);
        PanelContenido.setLayout(new BoxLayout(PanelContenido, BoxLayout.Y_AXIS));
        PanelContenido.setBorder(BorderFactory.createEmptyBorder(14, 34, 30, 34));

        Header = new PerfilHeader(this::ManejarAccionPrincipal);
        Header.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        PanelAccionesSecundarias = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        PanelAccionesSecundarias.setOpaque(false);
        PanelAccionesSecundarias.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        BtnMensaje = new BotonRedondeado("Mensaje");
        BtnMensaje.setPreferredSize(new Dimension(130, UIConstantes.ALTURA_BOTON_PEQUENO));
        BtnMensaje.setVisible(false);
        BtnMensaje.addActionListener(e -> AbrirChatConPerfilActual());
        
        BtnDesactivarCuenta = new BotonRedondeado("Desactivar cuenta");
        BtnDesactivarCuenta.setPreferredSize(new Dimension(180, UIConstantes.ALTURA_BOTON_PEQUENO));
        BtnDesactivarCuenta.setVisible(false);
        BtnDesactivarCuenta.addActionListener(e -> DesactivarCuentaActual());
        
        PanelAccionesSecundarias.add(BtnMensaje);
        PanelAccionesSecundarias.add(BtnDesactivarCuenta);
        
        JSeparator separador = new JSeparator();
        separador.setForeground(InstaColores.DIVISOR);
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        LblEstadoPrivado = new JLabel("", SwingConstants.CENTER);
        LblEstadoPrivado.setFont(UIConstantes.TEXTO_FONT);
        LblEstadoPrivado.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblEstadoPrivado.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblEstadoPrivado.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        
        PanelGrid = new JPanel(new GridLayout(0, 3, 10, 10));
        PanelGrid.setOpaque(false);
        PanelGrid.setBorder(BorderFactory.createEmptyBorder(6, 0, 20, 0));
        
        PanelContenido.add(Header);
        PanelContenido.add(Box.createVerticalStrut(8));
        PanelContenido.add(PanelAccionesSecundarias);
        PanelContenido.add(Box.createVerticalStrut(14));
        PanelContenido.add(separador);
        PanelContenido.add(Box.createVerticalStrut(20));
        PanelContenido.add(LblEstadoPrivado);
        PanelContenido.add(PanelGrid);
        
        ScrollPane = new JScrollPane(PanelContenido);
        ScrollPane.setBorder(null);
        ScrollPane.getViewport().setBackground(InstaColores.FONDO);
        ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(messageBar, BorderLayout.NORTH);
        add(ScrollPane, BorderLayout.CENTER);
        
        LblEstadoPrivado.setText("Selecciona un perfil");
        LblEstadoPrivado.setVisible(true);
    }
    
    public void CargarPerfil(String usuarioperfil) {        
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
        
        boolean mostrarbotonmensaje = viewer != null && !viewer.isBlank() && !viewer.equalsIgnoreCase(usuarioperfil) && perfilService.PuedeEnviarMensaje(viewer, usuarioperfil);
        BtnMensaje.setVisible(mostrarbotonmensaje);
        
        boolean esperfilpropio = viewer != null && viewer.equalsIgnoreCase(usuarioperfil);
        BtnDesactivarCuenta.setVisible(esperfilpropio);
        
        boolean puedeverposts = perfilService.PuedeVerPublicaciones(viewer, usuarioperfil);
        
        PanelGrid.removeAll();
        
        if (!puedeverposts) {
            MostrarEstadoCentral("Cuenta privada", "Debes seguir esta cuenta para ver sus publicaciones");
        } else {
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
        MostrarEstadoCentral("Perfil no disponible", "Este perfil ya no existe o no esta disponible");
        
        PanelContenido.revalidate();
        PanelContenido.repaint();
        revalidate();
        repaint();
    }
    
    private void MostrarEstadoCentral(String titulo, String subtitulo) {
        LblEstadoPrivado.setText("<html><div style='text-align:center;'>" + "<span style='font-weight:bold; color:#202020;'>" + titulo + "</span><br><br>" + "<span style ='color:#787880;'>" + subtitulo + "</span>" + "</div></html>");
        LblEstadoPrivado.setVisible(true);
    }
    
    private void CargarGridPublicaciones(String usuarioperfil) {
        ArrayList<Publicacion> publicaciones = publicacionService.ListarPublicacionesDe(usuarioperfil);
        
        if (publicaciones.isEmpty()) {
            MostrarEstadoCentral("Sin publicaciones", "Este usuario todavia no tiene publicaciones");
            return;
        }
        
        LblEstadoPrivado.setVisible(false);
        
        for (Publicacion post : publicaciones) {
            JPanel minipost = CrearMiniPost(post);
            PanelGrid.add(minipost);
        }
    }
    
    private JPanel CrearMiniPost(Publicacion post) {
        PanelRedondeado panel = new PanelRedondeado(18);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(220, 220));
        panel.setBackground(InstaColores.CARD);
        
        JLabel lblimagen = new JLabel();
        lblimagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblimagen.setVerticalAlignment(SwingConstants.CENTER);
        lblimagen.setOpaque(true);
        lblimagen.setBackground(InstaColores.FONDO_SECUNDARIO);
        lblimagen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
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
        dialogo.setSize(650, 800);
        dialogo.setLocationRelativeTo(this);
        dialogo.getContentPane().setBackground(InstaColores.FONDO);
        dialogo.setLayout(new BorderLayout());

        PostCard card = new PostCard(post, usuario -> appNavigator.irAPerfil(usuario), sessionManager.getUsuarioActual());
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(InstaColores.FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        contenedor.add(card);

        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(InstaColores.FONDO);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        dialogo.add(scroll, BorderLayout.CENTER);
        dialogo.setVisible(true);
    }        
    
    private void ManejarAccionPrincipal() {
        String viewer = sessionManager.getUsuarioActual();
        
        if (viewer == null || UsuarioPerfilAbierto == null) {
            return;
        }
        
        EstadoPerfil estado = perfilService.ObtenerEstadoPerfil(viewer, UsuarioPerfilAbierto);
        
        switch (estado) {
            case EDITAR_PERFIL -> AbrirEditorPerfil(viewer);
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
    
    private void AbrirChatConPerfilActual() {
        String viewer = sessionManager.getUsuarioActual();
        
        if (viewer == null || viewer.isBlank()) {
            return;
        }
        
        if (UsuarioPerfilAbierto == null || UsuarioPerfilAbierto.isBlank()) {
            return;
        }
        
        if (viewer.equalsIgnoreCase(UsuarioPerfilAbierto)) {
            messageBar.MostrarInfo("No puede enviarte mensajes a ti mismo");
            ProgramarOcultarBarra();
            return;
        }
        
        if (!perfilService.PuedeEnviarMensaje(viewer, UsuarioPerfilAbierto)) {
            messageBar.MostrarError("No puedes enviar mensajes a este usuario");
            ProgramarOcultarBarra();
            return;
        }
        
        appNavigator.irAInboxConUsuario(UsuarioPerfilAbierto);
    }
    
    private void DesactivarCuentaActual() {
        String usuarioactual = sessionManager.getUsuarioActual();
        
        if (usuarioactual == null || usuarioactual.isBlank()) {
            return;
        }
        
        ConfirmDialog dialogo = new ConfirmDialog((Frame) SwingUtilities.getWindowAncestor(this), "Confirmar", "Quieres desactivar tu cuenta?");
        dialogo.setVisible(true);
        
        if (!dialogo.isConfirmado()) {
            return;
        }
        
        boolean exito = usuarioService.DesactivarCuenta(usuarioactual);
        
        if (exito) {
            sessionManager.setMensajePendienteLogin("Tu cuenta fue desactivada correctamente");
            sessionManager.CerrarSesion();
            navigationListener.irALogin();
        } else {
            messageBar.MostrarError("No se pudo desactivar la cuenta");
            ProgramarOcultarBarra();
        }
    }
    
    private void AbrirEditorPerfil(String usuario) {
        Usuario usuarioeditor = usuarioService.BuscarUsuario(usuario);
        
        if (usuarioeditor == null) {
            messageBar.MostrarError("No se pudo cargar tu perfil");
            ProgramarOcultarBarra();
            return;
        }
        
        EditarPerfilDialog dialogo = new EditarPerfilDialog((Frame) SwingUtilities.getWindowAncestor(this), usuarioeditor);
        dialogo.setVisible(true);
        
        if (dialogo.isGuardado()) {
            messageBar.MostrarSuccess("Perfil actualizado correctamente");
            ProgramarOcultarBarra();
            CargarPerfil(usuario);
        }
    }
    
    private void ProgramarOcultarBarra() {
        Timer timer = new Timer(3000, e -> messageBar.Ocultar());
        timer.setRepeats(false);
        timer.start();
    }
}
