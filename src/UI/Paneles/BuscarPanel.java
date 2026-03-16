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
import service.BusquedaService;
import UI.Componentes.PostCard;
import UI.Componentes.BotonRedondeado;
import UI.Componentes.PanelRedondeado;
import UI.Componentes.ResultadoBuscarCard;
import UI.Componentes.MessageBar;
import UI.Core.SessionManager;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import interfaces.AppNavigator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BuscarPanel extends JPanel {
    
    private final SessionManager sessionManager;
    private final AppNavigator appNavigator;
    private final BusquedaService busquedaService;
    
    private MessageBar messageBar;
    private JTextField TxtBuscar;
    private JComboBox<String> CBTipoBusqueda;
    private JPanel ResultadosPanel;
    private JScrollPane ScrollPane;
    
    public BuscarPanel(SessionManager sessionManager, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.appNavigator = appNavigator;
        busquedaService = new BusquedaService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        messageBar = new MessageBar();
        
        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        
        TxtBuscar = new JTextField();
        TxtBuscar.setFont(UIConstantes.TEXTO_FONT);
        TxtBuscar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        
        CBTipoBusqueda = new JComboBox<>(new String[]{"Usuarios", "Hashtags"});
        CBTipoBusqueda.setFont(UIConstantes.TEXTO_FONT);
        
        BotonRedondeado btnbuscar = new BotonRedondeado("Buscar");
        btnbuscar.setPreferredSize(new Dimension(120, 42));
        btnbuscar.addActionListener(e -> Buscar());
        
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        derecha.setOpaque(false);
        derecha.add(CBTipoBusqueda);
        derecha.add(btnbuscar);
        
        top.add(TxtBuscar, BorderLayout.CENTER);
        top.add(derecha, BorderLayout.EAST);
        
        ResultadosPanel = new JPanel();
        ResultadosPanel.setOpaque(false);
        ResultadosPanel.setLayout(new BoxLayout(ResultadosPanel, BoxLayout.Y_AXIS));
        ResultadosPanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));
        
        ScrollPane = new JScrollPane(ResultadosPanel);
        ScrollPane.setBorder(null);
        ScrollPane.getViewport().setBackground(InstaColores.FONDO);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(messageBar, BorderLayout.NORTH);
        add(top, BorderLayout.NORTH);
        add(ScrollPane, BorderLayout.CENTER);
        
        MostrarMensajeInicial();
    }
    
    private void MostrarMensajeInicial() {
        ResultadosPanel.removeAll();
        
        JLabel lbl = new JLabel("Busca usuario o hashtags.");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lbl.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ResultadosPanel.add(Box.createVerticalStrut(30));
        ResultadosPanel.add(lbl);
        
        ResultadosPanel.revalidate();
        ResultadosPanel.repaint();
    }
    
    private void Buscar() {
        String texto = TxtBuscar.getText().trim();
        String tipo = (String) CBTipoBusqueda.getSelectedItem();
        
        if (texto.isBlank()) {
            messageBar.MostrarError("Escribe algo para buscar");
            ProgramarOcultarBarra();
            return;
        }
        
        ResultadosPanel.removeAll();
        
        if (tipo.equals("Usuarios")) {
            BuscarUsuarios(texto);
        } else {
            BuscarHashtags(texto);
        }
        
        ResultadosPanel.revalidate();
        ResultadosPanel.repaint();
        
        SwingUtilities.invokeLater(() -> ScrollPane.getVerticalScrollBar().setValue(0));
    }
    
    private void BuscarUsuarios(String texto) {
        ArrayList<Usuario> usuarios = busquedaService.BuscarUsuariosPorUsername(texto);
        
        if (usuarios.isEmpty()) {
            MostrarSinResultados("No se encontraron usuarios");
            return;
        }
        
        for (Usuario usuario : usuarios) {
            ResultadoBuscarCard card = new ResultadoBuscarCard(usuario, username -> appNavigator.irAPerfil(username));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            ResultadosPanel.add(card);
            ResultadosPanel.add(Box.createVerticalStrut(10));
        }
    }
    
    private void BuscarHashtags(String texto) {
        ArrayList<Publicacion> publicaciones = busquedaService.BuscarPublicacionesPorHashtag(texto);
        
        if (publicaciones.isEmpty()) {
            MostrarSinResultados("No se encontraron publicaciones con ese hashtag.");
            return;
        }
        
        for (Publicacion publicacion : publicaciones) {
            PostCard card = new PostCard(publicacion, username -> appNavigator.irAPerfil(username), sessionManager.getUsuarioActual());
            card.setAlignmentX(Component.CENTER_ALIGNMENT);
            ResultadosPanel.add(card);
            ResultadosPanel.add(Box.createVerticalStrut(18));
        }
    }
    
    private void MostrarSinResultados(String mensaje) {
        JLabel lbl = new JLabel(mensaje);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lbl.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ResultadosPanel.add(Box.createVerticalStrut(30));
        ResultadosPanel.add(lbl);
    }
    
    private void ProgramarOcultarBarra() {
        Timer timer = new Timer(3000, e -> messageBar.Ocultar());
        timer.setRepeats(false);
        timer.start();
    }
}
