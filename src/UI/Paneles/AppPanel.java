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

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel implements AppNavigator {
    
    private final SessionManager sessionManager;
    private final NavigationListener navigationListener;
    
    private CardLayout ContenedorLayout;
    private JPanel ContenedorPanel;
    
    private SidebarMenu Sidebar;
    
    private FeedPanel feedPanel;
    private PerfilPanel perfilPanel;
    private BuscarPanel BuscarPanel;
    private PlaceHolderViewPanel InboxPanel;
    private CrearPostPanel CrearPostPanel;
    
    public AppPanel(SessionManager sessionManager, NavigationListener navigationListener) {
        this.sessionManager = sessionManager;
        this.navigationListener = navigationListener;
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        Sidebar = new SidebarMenu(sessionManager, navigationListener, this);
        
        ContenedorLayout = new CardLayout();
        ContenedorPanel = new JPanel(ContenedorLayout);
        ContenedorPanel.setBackground(InstaColores.FONDO);
        
        feedPanel = new FeedPanel(sessionManager, this);
        perfilPanel = new PerfilPanel(sessionManager, this);
        BuscarPanel = new BuscarPanel(sessionManager, this);
        InboxPanel = new PlaceHolderViewPanel("Inbox", "Aqui ira la mensajeria privada");
        CrearPostPanel = new CrearPostPanel(sessionManager, this);
        
        ContenedorPanel.add(feedPanel, "FEED");
        ContenedorPanel.add(perfilPanel, "PERFIL");
        ContenedorPanel.add(BuscarPanel, "BUSCAR");
        ContenedorPanel.add(InboxPanel, "INBOX");
        ContenedorPanel.add(CrearPostPanel, "CREAR_POST");
        
        add(Sidebar, BorderLayout.WEST);
        add(ContenedorPanel, BorderLayout.CENTER);
    }
    
    public void RefrescarDatos() {
        Sidebar.RefrescarSesion();
        irAFeed();
    }
    
    @Override
    public void irAFeed() {
        feedPanel.CardarFeed();
        ContenedorLayout.show(ContenedorPanel, "FEED");
    }
    
    @Override
    public void irAPerfil(String usuarioperfil) {
        perfilPanel.CargarPerfil(usuarioperfil);
        ContenedorLayout.show(ContenedorPanel, "PERFIL");
    }
    
    @Override
    public void irABuscar() {
        ContenedorLayout.show(ContenedorPanel, "BUSCAR");
    }
    
    @Override
    public void irAInbox() {
        ContenedorLayout.show(ContenedorPanel, "INBOX");
    }
    
    @Override
    public void irACrearPost() {
        ContenedorLayout.show(ContenedorPanel, "CREAR_POST");
    }
}
