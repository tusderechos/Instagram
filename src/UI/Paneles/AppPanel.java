/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import UI.Componentes.SidebarMenu;
import UI.Core.SessionManager;
import interfaces.NavigationListener;
import interfaces.AppNavigator;
import UI.Styles.InstaColores;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel implements AppNavigator {
    
    private final SessionManager sessionManager;
    private final NavigationListener navigationListener;
    private final UsuarioService usuarioService;
    
    private CardLayout ContenedorLayout;
    private JPanel ContenedorPanel;
    
    private SidebarMenu Sidebar;
    
    private FeedPanel feedPanel;
    private PerfilPanel perfilPanel;
    private BuscarPanel buscarPanel;
    private InboxPanel inboxPanel;
    private CrearPostPanel crearPostPanel;
    private SolicitudesPanel solicitudesPanel;
    
    public AppPanel(SessionManager sessionManager, NavigationListener navigationListener) {
        this.sessionManager = sessionManager;
        this.navigationListener = navigationListener;
        usuarioService = new UsuarioService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        Sidebar = new SidebarMenu(sessionManager, navigationListener, this);
        
        ContenedorLayout = new CardLayout();
        ContenedorPanel = new JPanel(ContenedorLayout);
        ContenedorPanel.setBackground(InstaColores.FONDO);
        
        feedPanel = new FeedPanel(sessionManager, this);
        perfilPanel = new PerfilPanel(sessionManager, this, navigationListener);
        buscarPanel = new BuscarPanel(sessionManager, this);
        inboxPanel = new InboxPanel(sessionManager, this);
        solicitudesPanel = new SolicitudesPanel(sessionManager, this);
        crearPostPanel = new CrearPostPanel(sessionManager, this);
        
        ContenedorPanel.add(feedPanel, "FEED");
        ContenedorPanel.add(perfilPanel, "PERFIL");
        ContenedorPanel.add(buscarPanel, "BUSCAR");
        ContenedorPanel.add(inboxPanel, "INBOX");
        ContenedorPanel.add(solicitudesPanel, "SOLICITUDES");
        ContenedorPanel.add(crearPostPanel, "CREAR_POST");
        
        add(Sidebar, BorderLayout.WEST);
        add(ContenedorPanel, BorderLayout.CENTER);
    }
    
    private boolean SesionValida() {
        String usuarioactual = sessionManager.getUsuarioActual();
        
        if (usuarioactual == null || usuarioactual.isBlank()) {
            sessionManager.setMensajePendienteLogin("Debes iniciar sesion");
            sessionManager.CerrarSesion();
            navigationListener.irALogin();
            return false;
        }
        
        UsuarioService usuarioservice = new UsuarioService();
        
        if (!usuarioservice.UsuarioEsValido(usuarioactual)) {
            sessionManager.setMensajePendienteLogin("Tu cuenta esta desactivada o ya no esta disponible");
            sessionManager.CerrarSesion();
            navigationListener.irALogin();
            return false;
        }
        
        return true;
    }
    
    public void RefrescarDatos() {
        Sidebar.RefrescarSesion();
        inboxPanel.ReiniciarInbox();
        buscarPanel.ReiniciarBusqueda();
        irAFeed();
    }
    
    @Override
    public void irAFeed() {
        if (!SesionValida()) {
            return;
        }
        
        feedPanel.CardarFeed();
        ContenedorLayout.show(ContenedorPanel, "FEED");
    }
    
    @Override
    public void irAPerfil(String usuarioperfil) {
        if (!SesionValida()) {
            return;
        }
        
        perfilPanel.CargarPerfil(usuarioperfil);
        ContenedorLayout.show(ContenedorPanel, "PERFIL");
    }
    
    @Override
    public void irABuscar() {
        if (!SesionValida()) {
            return;
        }
        
        ContenedorLayout.show(ContenedorPanel, "BUSCAR");
    }
    
    @Override
    public void irAInbox() {
        if (!SesionValida()) {
            return;
        }
        
        inboxPanel.CargarInbox();
        ContenedorLayout.show(ContenedorPanel, "INBOX");
    }
    
    @Override
    public void irAInboxConUsuario(String usuario) {
        if (!SesionValida()) {
            return;
        }
        
        inboxPanel.CargarInbox();
        inboxPanel.AbrirChatDirecto(usuario);
        ContenedorLayout.show(ContenedorPanel, "INBOX");
    }
    
    @Override
    public void irASolicitudes() {
        if (!SesionValida()) {
            return;
        }
        
        solicitudesPanel.CargarSolicitudes();
        ContenedorLayout.show(ContenedorPanel, "SOLICITUDES");
    }
    
    @Override
    public void irACrearPost() {
        if (!SesionValida()) {
            return;
        }
        
        ContenedorLayout.show(ContenedorPanel, "CREAR_POST");
    }
}
