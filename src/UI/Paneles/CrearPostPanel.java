/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import enums.TipoMedia;
import service.PublicacionService;
import UI.Componentes.BotonRedondeado;
import UI.Componentes.PanelRedondeado;
import UI.Componentes.MessageBar;
import UI.Core.SessionManager;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import UI.Utils.FileUtils;
import interfaces.AppNavigator;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CrearPostPanel extends JPanel {
    
    private final SessionManager sessionManager;
    private final AppNavigator appNavigator;
    private final PublicacionService publicacionService;
    
    private MessageBar messageBar;
    private JTextArea TxtContenido;
    private JTextField TxtRutaImagen;
    private JLabel LblPreview;
    private String RutaSeleccionada;
    
    public CrearPostPanel(SessionManager sessionManager, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.appNavigator = appNavigator;
        publicacionService = new PublicacionService();
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        messageBar = new MessageBar();
        
        JPanel wrappergeneral = new JPanel();
        wrappergeneral.setOpaque(false);
        wrappergeneral.setLayout(new BoxLayout(wrappergeneral, BoxLayout.Y_AXIS));
        
        wrappergeneral.add(messageBar);
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        
        PanelRedondeado card = new PanelRedondeado(UIConstantes.ARCO_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(700, 690));
        card.setBackground(InstaColores.CARD);
        card.setBorder(BorderFactory.createEmptyBorder(24, 26, 24, 26));
        
        JLabel lbltitulo = new JLabel("Crear publicacion");
        lbltitulo.setFont(UIConstantes.TITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("Comparte una foto o escribe algo para tu perfil");
        lblsubtitulo.setFont(UIConstantes.TEXTO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblcaption = new JLabel("Contenido");
        lblcaption.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblcaption.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblcaption.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        TxtContenido = new JTextArea(6, 20);
        TxtContenido.setLineWrap(true);
        TxtContenido.setWrapStyleWord(true);
        TxtContenido.setFont(UIConstantes.TEXTO_FONT);
        TxtContenido.setBackground(InstaColores.INPUT_BG);
        TxtContenido.setBorder(BorderFactory.createLineBorder(InstaColores.BORDER));
        
        JScrollPane scrolltexto = new JScrollPane(TxtContenido);
        scrolltexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrolltexto.setPreferredSize(new Dimension(620, 150));
        scrolltexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrolltexto.setBorder(BorderFactory.createLineBorder(InstaColores.BORDER));
        
        JLabel lblimagen = new JLabel("Imagen");
        lblimagen.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblimagen.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblimagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel panelruta = new JPanel(new BorderLayout(10, 0));
        panelruta.setOpaque(false);
        panelruta.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelruta.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIConstantes.ALTURA_BOTON));
        
        TxtRutaImagen = new JTextField();
        TxtRutaImagen.setEditable(false);
        TxtRutaImagen.setFont(UIConstantes.TEXTO_FONT);
        TxtRutaImagen.setBackground(InstaColores.INPUT_BG);
        TxtRutaImagen.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        
        BotonRedondeado btnseleccionar = new BotonRedondeado("Seleccionar");
        btnseleccionar.setPreferredSize(new Dimension(150, UIConstantes.ALTURA_BOTON));
        btnseleccionar.addActionListener(e -> SeleccionarImagen());
        
        panelruta.add(TxtRutaImagen, BorderLayout.CENTER);
        panelruta.add(btnseleccionar, BorderLayout.EAST);
        
        LblPreview = new JLabel("Preview", SwingConstants.CENTER);
        LblPreview.setOpaque(true);
        LblPreview.setBackground(InstaColores.FONDO_SECUNDARIO);
        LblPreview.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblPreview.setBorder(BorderFactory.createLineBorder(InstaColores.BORDER));
        LblPreview.setAlignmentX(Component.LEFT_ALIGNMENT);
        LblPreview.setPreferredSize(new Dimension(620, 250));
        LblPreview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        acciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        BotonRedondeado btnpublicar = new BotonRedondeado("Publicar");
        btnpublicar.setPreferredSize(new Dimension(150, UIConstantes.ALTURA_BOTON));
        btnpublicar.addActionListener(e -> Publicar());
        
        BotonRedondeado btncancelar = new BotonRedondeado("Cancelar");
        btncancelar.setPreferredSize(new Dimension(140, UIConstantes.ALTURA_BOTON));
        btncancelar.addActionListener(e -> LimpiarFormulario());
        
        acciones.add(btncancelar);
        acciones.add(btnpublicar);
        
        card.add(lbltitulo);
        card.add(Box.createVerticalStrut(6));
        card.add(lblsubtitulo);
        card.add(Box.createVerticalStrut(20));
        card.add(lblcaption);
        card.add(Box.createVerticalStrut(6));
        card.add(scrolltexto);
        card.add(Box.createVerticalStrut(14));
        card.add(lblimagen);
        card.add(Box.createVerticalStrut(6));
        card.add(panelruta);
        card.add(Box.createVerticalStrut(14));
        card.add(LblPreview);
        card.add(Box.createVerticalStrut(18));
        card.add(acciones);
        
        wrapper.add(card);
        wrappergeneral.add(wrapper);
        
        add(wrappergeneral, BorderLayout.CENTER);
    }
    
    private void SeleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            RutaSeleccionada = archivo.getAbsolutePath();
            TxtRutaImagen.setText(RutaSeleccionada);
            CargarPreview(RutaSeleccionada);
        }
    }
    
    private void CargarPreview(String ruta) {
        if (ruta == null || ruta.isBlank()) {
            LblPreview.setIcon(null);
            LblPreview.setText("Preview");
            return;
        }
        
        ImageIcon icono = new ImageIcon(ruta);
        
        if (icono.getIconWidth() > 0) {
            Image escalada = icono.getImage().getScaledInstance(620, 250, Image.SCALE_SMOOTH);
            LblPreview.setIcon(new ImageIcon(escalada));
            LblPreview.setText("");
        } else {
            LblPreview.setText(null);
            LblPreview.setText("No se pudo cargar la imagen");
        }
    }
    
    private void Publicar() {
        String usuario = sessionManager.getUsuarioActual();
        String contenido = TxtContenido.getText().trim();
        
        if (usuario == null || usuario.isBlank()) {
            messageBar.MostrarError("No hay sesion activa");
            ProgramarOcultarBarra();
            return;
        }
        
        if (contenido.isBlank()) {
            messageBar.MostrarError("Debes escribir contenido para la publicacion");
            ProgramarOcultarBarra();
            return;
        }
        
        if (contenido.length() > 220) {
            messageBar.MostrarError("El contenido supera los 220 caracteres");
            ProgramarOcultarBarra();
            return;
        }
        
        String rutafinal = "";
        TipoMedia tipomedia = TipoMedia.NINGUNO;
        
        if (RutaSeleccionada != null && !RutaSeleccionada.isBlank()) {
            rutafinal = FileUtils.CopiarImagenPost(RutaSeleccionada, usuario);
            
            if (rutafinal.isBlank()) {
                messageBar.MostrarError("No se pudo copiar la imagen");
                ProgramarOcultarBarra();
                return;
            }
            
            tipomedia = TipoMedia.IMAGEN;
        }
        
        boolean creado = publicacionService.CrearPublicacion(usuario, contenido, rutafinal, tipomedia);
        
        if (!creado) {
            messageBar.MostrarError("No se pudo crear la publicacion");
            ProgramarOcultarBarra();
            return;
        }
        
        messageBar.MostrarSuccess("Publicacion creada con exito");
        ProgramarOcultarBarra();
        LimpiarFormulario();
        
        //Despues de publicar, refresar al feed
        Timer timer = new Timer(1200, e -> appNavigator.irAFeed());
        timer.setRepeats(false);
        timer.start();
    }
    
    private void LimpiarFormulario() {
        TxtContenido.setText("");
        TxtRutaImagen.setText("");
        RutaSeleccionada = "";
        LblPreview.setIcon(null);
        LblPreview.setText("Preview");
    }
    
    private void ProgramarOcultarBarra() {
        Timer timer = new Timer(3000, e -> messageBar.Ocultar());
        timer.setRepeats(false);
        timer.start();
    }
}
