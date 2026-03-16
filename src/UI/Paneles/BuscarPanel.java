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
        
        JPanel headercontainer = new JPanel();
        headercontainer.setOpaque(false);
        headercontainer.setLayout(new BoxLayout(headercontainer, BoxLayout.Y_AXIS));
        
        headercontainer.add(messageBar);
        headercontainer.add(CrearTopBusqueda());
        
        ResultadosPanel = new JPanel();
        ResultadosPanel.setOpaque(false);
        ResultadosPanel.setLayout(new BoxLayout(ResultadosPanel, BoxLayout.Y_AXIS));
        ResultadosPanel.setBorder(BorderFactory.createEmptyBorder(10, 24, 24, 24));
        
        ScrollPane = new JScrollPane(ResultadosPanel);
        ScrollPane.setBorder(null);
        ScrollPane.getViewport().setBackground(InstaColores.FONDO);
        ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(headercontainer, BorderLayout.NORTH);
        add(ScrollPane, BorderLayout.CENTER);
        
        MostrarMensajeInicial();
    }
    
    private JComponent CrearTopBusqueda() {
        PanelRedondeado topcard = new PanelRedondeado(UIConstantes.ARCO_CARD);
        topcard.setLayout(new BorderLayout(14, 0));
        topcard.setBackground(InstaColores.CARD);
        topcard.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(18, 24, 12, 24));
        
        JPanel izquierda = new JPanel();
        izquierda.setOpaque(false);
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        
        JLabel titulo = new JLabel("Buscar");
        titulo.setFont(UIConstantes.TITULO_FONT);
        titulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("Encuentra usuario o publicaciones por hashtag");
        subtitulo.setFont(UIConstantes.TEXTO_FONT);
        subtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        izquierda.add(titulo);
        izquierda.add(Box.createVerticalStrut(4));
        izquierda.add(subtitulo);
        
        JPanel barra = new JPanel(new BorderLayout(12, 0));
        barra.setOpaque(false);
        
        TxtBuscar = new JTextField();
        TxtBuscar.setFont(UIConstantes.TEXTO_FONT);
        TxtBuscar.setBackground(InstaColores.INPUT_BG);
        TxtBuscar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER_SUAVE), BorderFactory.createEmptyBorder(11, 12, 11, 12)));
        
        CBTipoBusqueda = new JComboBox<>(new String[]{"Usuarios", "Hashtags"});
        CBTipoBusqueda.setFont(UIConstantes.TEXTO_FONT);
        CBTipoBusqueda.setPreferredSize(new Dimension(140, UIConstantes.ALTURA_BOTON));
        
        BotonRedondeado btnbuscar = new BotonRedondeado("Buscar");
        btnbuscar.setPreferredSize(new Dimension(120, UIConstantes.ALTURA_BOTON));
        btnbuscar.addActionListener(e -> Buscar());
        
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        derecha.setOpaque(false);
        
        derecha.add(CBTipoBusqueda);
        derecha.add(btnbuscar);
        
        barra.add(TxtBuscar, BorderLayout.CENTER);
        barra.add(derecha, BorderLayout.EAST);
        
        topcard.add(izquierda, BorderLayout.NORTH);
        topcard.add(barra, BorderLayout.SOUTH);
        
        wrapper.add(topcard, BorderLayout.CENTER);
        return wrapper;
    }
    
    private void MostrarMensajeInicial() {
        ResultadosPanel.removeAll();
        
        PanelRedondeado cardinicio = new PanelRedondeado(UIConstantes.ARCO_CARD);
        cardinicio.setLayout(new BoxLayout(cardinicio, BoxLayout.Y_AXIS));
        cardinicio.setBackground(InstaColores.CARD);
        cardinicio.setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        cardinicio.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardinicio.setMaximumSize(new Dimension(720, 180));
        
        JLabel icono = new JLabel("#");
        icono.setFont(new Font("SansSerif", Font.BOLD, 28));
        icono.setForeground(InstaColores.TEXTO_APAGADO);
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titulo = new JLabel("Busca usuario o hashtags");
        titulo.setFont(UIConstantes.SUBTITULO_FONT);
        titulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("<html><div style='text-align:center;'>Escribe un nombre de usuario o un hashtag para comenzar.</div></html>");
        subtitulo.setFont(UIConstantes.TEXTO_FONT);
        subtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cardinicio.add(Box.createVerticalGlue());
        cardinicio.add(icono);
        cardinicio.add(Box.createVerticalStrut(10));
        cardinicio.add(titulo);
        cardinicio.add(Box.createVerticalStrut(8));
        cardinicio.add(subtitulo);
        cardinicio.add(Box.createVerticalGlue());
        
        ResultadosPanel.add(Box.createVerticalStrut(18));
        ResultadosPanel.add(cardinicio);
        
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
            MostrarSinResultados("No se encontraron usuarios", "Prueba con otro nomre o revisa el texto ingresado");
            return;
        }
        
        ResultadosPanel.add(Box.createVerticalStrut(8));
        
        for (Usuario usuario : usuarios) {
            ResultadoBuscarCard card = new ResultadoBuscarCard(usuario, username -> appNavigator.irAPerfil(username));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            ResultadosPanel.add(card);
            ResultadosPanel.add(Box.createVerticalStrut(12));
        }
    }
    
    private void BuscarHashtags(String texto) {
        ArrayList<Publicacion> publicaciones = busquedaService.BuscarPublicacionesPorHashtag(texto);
        
        if (publicaciones.isEmpty()) {
            MostrarSinResultados("No se encontraron publicaciones", "No hay resultados para ese hashtag.");
            return;
        }
        
        ResultadosPanel.add(Box.createVerticalStrut(8));
        
        for (Publicacion publicacion : publicaciones) {
            PostCard card = new PostCard(publicacion, username -> appNavigator.irAPerfil(username), sessionManager.getUsuarioActual());
            card.setAlignmentX(Component.CENTER_ALIGNMENT);
            ResultadosPanel.add(card);
            ResultadosPanel.add(Box.createVerticalStrut(22));
        }
    }
    
    private void MostrarSinResultados(String titulotexto, String subtitulotexto) {
        PanelRedondeado cardvacia = new PanelRedondeado(UIConstantes.ARCO_CARD);
        cardvacia.setLayout(new BoxLayout(cardvacia, BoxLayout.Y_AXIS));
        cardvacia.setBackground(InstaColores.CARD);
        cardvacia.setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        cardvacia.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardvacia.setMaximumSize(new Dimension(720, 180));
        
        JLabel icono = new JLabel("🔎");
        icono.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icono.setForeground(InstaColores.TEXTO_APAGADO);
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titulo = new JLabel(titulotexto);
        titulo.setFont(UIConstantes.SUBTITULO_FONT);
        titulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("<html><div style='text-align:center;'>" + subtitulotexto + "</div></html>");
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
        
        ResultadosPanel.add(Box.createVerticalStrut(18));
        ResultadosPanel.add(cardvacia);
    }
    
    private void ProgramarOcultarBarra() {
        Timer timer = new Timer(3000, e -> messageBar.Ocultar());
        timer.setRepeats(false);
        timer.start();
    }
}
