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
import UI.Componentes.PanelRedondeado;
import UI.Componentes.PostCard;
import UI.Core.SessionManager;
import interfaces.AppNavigator;
import UI.Styles.InstaColores;
import UI.Styles.ScrollBarUI;
import UI.Styles.UIConstantes;

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
        
        ContenedorPosts = new JPanel();
        ContenedorPosts.setOpaque(false);
        ContenedorPosts.setLayout(new BoxLayout(ContenedorPosts, BoxLayout.Y_AXIS));
        ContenedorPosts.setAlignmentX(Component.LEFT_ALIGNMENT);
        ContenedorPosts.setBorder(BorderFactory.createEmptyBorder(18, 230, 24, 230));
        
//        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 24));
//        wrapper.setOpaque(false);
//        wrapper.setBorder(BorderFactory.createEmptyBorder(18, 0, 24, 0));
//        wrapper.add(ContenedorPosts);
//        
//        ContenedorPosts.setPreferredSize(new Dimension(520, 1));
        
        ScrollPane = new JScrollPane(ContenedorPosts);
        ScrollPane.setBorder(null);
        ScrollPane.getViewport().setBackground(InstaColores.FONDO);
        ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        ScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(ScrollPane, BorderLayout.CENTER);
    }
    
    public void CardarFeed() {
        ContenedorPosts.removeAll();
        
        String user = sessionManager.getUsuarioActual();
        ArrayList<Publicacion> posts = feedService.GenerarTimeline(user);
        
        if (posts.isEmpty()) {
            MostrarEstadoVacio();
        } else {
            for (Publicacion post : posts) {
                PostCard card = new PostCard(post, usuario -> appNavigator.irAPerfil(usuario), sessionManager.getUsuarioActual());
                card.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                ContenedorPosts.add(card);
                ContenedorPosts.add(Box.createVerticalStrut(18));
            }
        }
        
        ContenedorPosts.revalidate();
        ContenedorPosts.repaint();
        
        SwingUtilities.invokeLater(() -> ScrollPane.getVerticalScrollBar().setValue(0));
    }
    
    private void MostrarEstadoVacio() {
        PanelRedondeado cardvacia = new PanelRedondeado(UIConstantes.ARCO_CARD);
        cardvacia.setLayout(new BoxLayout(cardvacia, BoxLayout.Y_AXIS));
        cardvacia.setBackground(InstaColores.CARD);
        cardvacia.setBorder(BorderFactory.createEmptyBorder(28, 34, 28, 34));
        cardvacia.setPreferredSize(new Dimension(470, 180));
        cardvacia.setMaximumSize(new Dimension(470, 180));
        cardvacia.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel icono = new JLabel("⌂");
        icono.setFont(new Font("SansSerif", Font.PLAIN, 30));
        icono.setForeground(InstaColores.TEXTO_APAGADO);
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titulo = new JLabel("Tu feed esta vacio");
        titulo.setFont(UIConstantes.SUBTITULO_FONT);
        titulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("<html><div style='text-aling:center;'>Sigue usuario o espera nuevas publicaciones para ver contenido aqui.</div></html>");
        subtitulo.setFont(UIConstantes.TEXTO_FONT);
        subtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cardvacia.add(Box.createVerticalGlue());
        cardvacia.add(icono);
        cardvacia.add(Box.createVerticalStrut(10));
        cardvacia.add(titulo);
        cardvacia.add(Box.createVerticalStrut(8));
        cardvacia.add(subtitulo);
        cardvacia.add(Box.createVerticalGlue());
        
        ContenedorPosts.add(Box.createVerticalStrut(20));
        ContenedorPosts.add(cardvacia);
    }
}
