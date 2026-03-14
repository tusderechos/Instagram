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
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        
        PanelRedondeado card = new PanelRedondeado(24);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(650, 620));
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setBackground(Color.WHITE);
        
        JLabel lbltitulo = new JLabel("Crear publicacion");
        lbltitulo.setFont(UIConstantes.TITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblcaption = new JLabel("Contenido");
        lblcaption.setFont(UIConstantes.PEQUENO_FONT);
        lblcaption.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblcaption.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        TxtContenido = new JTextArea(6, 20);
        TxtContenido.setLineWrap(true);
        TxtContenido.setWrapStyleWord(true);
        TxtContenido.setFont(UIConstantes.TEXTO_FONT);
        TxtContenido.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        
        JScrollPane scrolltexto = new JScrollPane(TxtContenido);
        scrolltexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrolltexto.setPreferredSize(new Dimension(580, 150));
        scrolltexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        JLabel lblimagen = new JLabel("Imagen");
        lblimagen.setFont(UIConstantes.PEQUENO_FONT);
        lblimagen.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblimagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel panelruta = new JPanel(new BorderLayout(8, 0));
        panelruta.setOpaque(false);
        panelruta.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelruta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        TxtRutaImagen = new JTextField();
        TxtRutaImagen.setEditable(false);
        TxtRutaImagen.setFont(UIConstantes.TEXTO_FONT);
        TxtRutaImagen.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        
        BotonRedondeado btnseleccionar = new BotonRedondeado("Seleccionar");
        btnseleccionar.setPreferredSize(new Dimension(140, 40));
        btnseleccionar.addActionListener(e -> SeleccionarImagen());
        
        panelruta.add(TxtRutaImagen, BorderLayout.CENTER);
        panelruta.add(btnseleccionar, BorderLayout.EAST);
        
        LblPreview = new JLabel("Preview", SwingConstants.CENTER);
        LblPreview.setOpaque(true);
        LblPreview.setBackground(new Color(245, 245, 245));
        LblPreview.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblPreview.setBorder(BorderFactory.createLineBorder(InstaColores.BORDER));
        LblPreview.setAlignmentX(Component.LEFT_ALIGNMENT);
        LblPreview.setPreferredSize(new Dimension(580, 240));
        LblPreview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        acciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        BotonRedondeado btnpublicar = new BotonRedondeado("Publicar");
        btnpublicar.setPreferredSize(new Dimension(140, 42));
        btnpublicar.addActionListener(e -> Publicar());
        
        BotonRedondeado btncancelar = new BotonRedondeado("Cancelar");
        btncancelar.setPreferredSize(new Dimension(140, 42));
        btncancelar.addActionListener(e -> LimpiarFormulario());
        
        acciones.add(btncancelar);
        acciones.add(btnpublicar);
        
        card.add(lbltitulo);
        card.add(Box.createVerticalStrut(18));
        card.add(lblcaption);
        card.add(Box.createVerticalStrut(6));
        card.add(scrolltexto);
        card.add(Box.createVerticalStrut(14));
        card.add(lblimagen);
        card.add(Box.createVerticalStrut(6));
        card.add(panelruta);
        card.add(Box.createVerticalStrut(14));
        card.add(LblPreview);
        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(18));
        card.add(acciones);
        
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);
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
            Image escalada = icono.getImage().getScaledInstance(580, 240, Image.SCALE_SMOOTH);
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
