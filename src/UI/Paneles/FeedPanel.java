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
import service.FeedService;
import UI.Componentes.PostCard;
import UI.Core.SessionManager;
import interfaces.AppNavigator;
import UI.Styles.InstaColores;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FeedPanel extends JPanel {
    
    private final SessionManager sessionManager;
    private final AppNavigator appNavigator;
    private final FeedService feedService;
    
    private JPanel ContenedorPosts;
    private JScrollPane ScrollPane;
    
    public FeedPanel(SessionManager sessionManager, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.appNavigator = appNavigator;
        feedService = new FeedService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
        wrapper.setOpaque(false);
        
        ContenedorPosts = new JPanel();
        ContenedorPosts.setOpaque(false);
        ContenedorPosts.setLayout(new BoxLayout(ContenedorPosts, BoxLayout.Y_AXIS));
        
        wrapper.add(ContenedorPosts);
        
        ScrollPane = new JScrollPane(wrapper);
        ScrollPane.setBorder(null);
        ScrollPane.getViewport().setBackground(InstaColores.FONDO);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(ScrollPane, BorderLayout.CENTER);
    }
    
    public void CardarFeed() {
        ContenedorPosts.removeAll();
        
        String user = sessionManager.getUsuarioActual();
        ArrayList<Publicacion> posts = feedService.GenerarTimeline(user);
        
        if (posts.isEmpty()) {
            JLabel vacio = new JLabel("Todavia no hay publicaciones para mostrar");
            vacio.setFont(new Font("SansSerif", Font.PLAIN, 16));
            vacio.setForeground(InstaColores.TEXTO_SECUNDARIO);
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            ContenedorPosts.add(Box.createVerticalStrut(30));
            ContenedorPosts.add(vacio);
        } else {
            for (Publicacion post : posts) {
                PostCard card = new PostCard(post, usuario -> appNavigator.irAPerfil(usuario), sessionManager.getUsuarioActual());
                card.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                ContenedorPosts.add(card);
                ContenedorPosts.add(Box.createVerticalStrut(18));
            }
        }
        
        ContenedorPosts.revalidate();
        ContenedorPosts.repaint();
        
        SwingUtilities.invokeLater(() -> ScrollPane.getVerticalScrollBar().setValue(0));
    }
}
