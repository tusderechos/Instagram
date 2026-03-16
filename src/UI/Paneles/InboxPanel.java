/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import Red.ManejoConexionChat;
import Red.PaqueteMensaje;
import UI.Componentes.MensajeBurbuja;
import UI.Componentes.StickerPickerPanel;
import UI.Componentes.BotonRedondeado;
import UI.Componentes.PanelRedondeado;
import UI.Core.SessionManager;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import interfaces.AppNavigator;
import interfaces.MensajeChatListener;
import modelo.Mensaje;
import UI.Componentes.ConversacionesInboxRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class InboxPanel extends JPanel implements MensajeChatListener {
    
    private final SessionManager sessionManager;
    private final AppNavigator appNavigator;
    
    private DefaultListModel<String> ModeloConversaciones;
    private JList<String> ListaConversaciones;
    
    private JLabel LblTituloChat;
    private JPanel PanelMensajes;
    private JScrollPane ScrollMensajes;
    private JTextField TxtMensaje;
    private JLabel LblEstado;
    
    private String UsuarioChatActual;
    
    public InboxPanel(SessionManager sessionManager, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.appNavigator = appNavigator;
        UsuarioChatActual = null;
        
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        JPanel panelizquierdo = new JPanel(new BorderLayout());
        panelizquierdo.setPreferredSize(new Dimension(295, 0));
        panelizquierdo.setBackground(InstaColores.CARD);
        panelizquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, InstaColores.DIVISOR));
        
        JLabel lbltitulo = new JLabel("Inbox");
        lbltitulo.setFont(UIConstantes.SUBTITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setBorder(new EmptyBorder(20, 20, 16, 20));
        
        ModeloConversaciones = new DefaultListModel<>();
        
        ListaConversaciones = new JList<>(ModeloConversaciones);
        ListaConversaciones.setFont(UIConstantes.TEXTO_FONT);
        ListaConversaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListaConversaciones.setBackground(InstaColores.CARD);
        ListaConversaciones.setForeground(InstaColores.TEXTO_PRIMARIO);
        ListaConversaciones.setBorder(new EmptyBorder(10, 10, 10, 10));
        ListaConversaciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccionado = ListaConversaciones.getSelectedValue();
                
                if (seleccionado != null) {
                    AbrirConversacion(seleccionado);
                }
            }
        });
        ListaConversaciones.setCellRenderer(new ConversacionesInboxRenderer(sessionManager));
        ListaConversaciones.setFixedCellHeight(75);
        
        JScrollPane scrollconversaciones = new JScrollPane(ListaConversaciones);
        scrollconversaciones.setBorder(null);
        scrollconversaciones.getViewport().setBackground(InstaColores.CARD);
        
        panelizquierdo.add(lbltitulo, BorderLayout.NORTH);
        panelizquierdo.add(scrollconversaciones, BorderLayout.CENTER);
        
        
        JPanel panelderecho = new JPanel(new BorderLayout());
        panelderecho.setBackground(InstaColores.FONDO);
        
        JPanel headchat = new JPanel(new BorderLayout());
        headchat.setBackground(InstaColores.CARD);
        headchat.setBorder(new EmptyBorder(18, 22, 18, 22));
        
        LblTituloChat = new JLabel("Selecciona una conversacion");
        LblTituloChat.setFont(UIConstantes.SUBTITULO_FONT);
        LblTituloChat.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        headchat.add(LblTituloChat, BorderLayout.WEST);
        
        PanelMensajes = new JPanel();
        PanelMensajes.setLayout(new BoxLayout(PanelMensajes, BoxLayout.Y_AXIS));
        PanelMensajes.setBackground(InstaColores.FONDO);
        PanelMensajes.setBorder(new EmptyBorder(14, 14, 14, 14));
        
        ScrollMensajes = new JScrollPane(PanelMensajes);
        ScrollMensajes.setBorder(null);
        ScrollMensajes.getViewport().setBackground(InstaColores.FONDO);
        ScrollMensajes.getVerticalScrollBar().setUnitIncrement(14);
                
        JPanel panelinferior = new JPanel();
        panelinferior.setLayout(new BoxLayout(panelinferior, BoxLayout.Y_AXIS));
        panelinferior.setOpaque(false);
        panelinferior.setBorder(new EmptyBorder(12, 18, 18, 18));
        
        PanelRedondeado filainput = new PanelRedondeado(UIConstantes.ARCO_CARD);
        filainput.setLayout(new BorderLayout(10, 0));
        filainput.setBackground(InstaColores.CARD);
        filainput.setMostrarSombra(false);
        filainput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnsticker = new JButton("🙂");
        btnsticker.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnsticker.setFocusPainted(false);
        btnsticker.setBorderPainted(false);
        btnsticker.setContentAreaFilled(false);
        btnsticker.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnsticker.addActionListener(e -> AbrirSelectorStickers());
        
        TxtMensaje = new JTextField();
        TxtMensaje.setFont(UIConstantes.TEXTO_FONT);
        TxtMensaje.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER_SUAVE), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        TxtMensaje.addActionListener(e -> EnviarMensajeActual());
        
        BotonRedondeado btnenviar = new BotonRedondeado("Enviar");
        btnenviar.setPreferredSize(new Dimension(100, UIConstantes.ALTURA_BOTON));
        btnenviar.addActionListener(e -> EnviarMensajeActual());
        
        filainput.add(btnsticker, BorderLayout.WEST);
        filainput.add(TxtMensaje, BorderLayout.CENTER);
        filainput.add(btnenviar, BorderLayout.EAST);
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblEstado.setBorder(new EmptyBorder(8, 4, 0, 4));
        
        panelinferior.add(filainput);
        panelinferior.add(LblEstado);
        
        panelderecho.add(headchat, BorderLayout.NORTH);
        panelderecho.add(ScrollMensajes, BorderLayout.CENTER);
        panelderecho.add(panelinferior, BorderLayout.SOUTH);
        
        add(panelizquierdo, BorderLayout.WEST);
        add(panelderecho, BorderLayout.CENTER);
    }
    
    private void RegistrarListenerChat() {
        ManejoConexionChat chat = ObtenerChat();
        
        if (chat != null) {
            chat.setListener(this);
        }
    }
    
    private ManejoConexionChat ObtenerChat() {
        return sessionManager.getManejoConexionChat();
    }
    
    public void CargarInbox() {
        RegistrarListenerChat();
        CargarConversaciones();
        
        if (UsuarioChatActual != null && !UsuarioChatActual.isBlank()) {
            CargarMensajes(UsuarioChatActual);
        } else {
            LimpiarVistaChat();
        }
    }
    
    public void CargarConversaciones() {
        ModeloConversaciones.clear();
        
        ManejoConexionChat chat = ObtenerChat();
        
        if (chat == null) {
            MostrarEstado("Chat no disponible");
            return;
        }
        
        ArrayList<String> conversaciones = chat.ListarConversacionesOrdenadas();
        
        for (String usuario : conversaciones) {
            if (usuario != null && !usuario.isBlank() && !usuario.equalsIgnoreCase(sessionManager.getUsuarioActual())) {
                ModeloConversaciones.addElement(usuario);
            }
        }
        
        if (ModeloConversaciones.isEmpty()) {
            MostrarEstado("No tienes conversaciones todavia");
        } else {
            LimpiarEstado();
        }
    }
    
    private void AbrirConversacion(String otrousuario) {
        UsuarioChatActual = otrousuario;
        LblTituloChat.setText("Chat con @" + otrousuario);
        
        ManejoConexionChat chat = ObtenerChat();
        
        if (chat != null) {
            chat.MarcarConversacionComoLeida(otrousuario);
        }
        
        CargarMensajes(otrousuario);
        CargarConversaciones();
        LimpiarEstado();
        TxtMensaje.requestFocusInWindow();
    }
    
    private void CargarMensajes(String otrousuario) {
        ManejoConexionChat chat = ObtenerChat();
        
        PanelMensajes.removeAll();
        
        if (chat == null) {
            MostrarEstado("No hay conexion de chat");
            RefrescarPanelMensajes();
            return;
        }
        
        ArrayList<Mensaje> mensajes = chat.ObtenerConversacion(otrousuario);
        
        if (mensajes.isEmpty()) {
            PanelRedondeado vacio = new PanelRedondeado(UIConstantes.ARCO_CARD);
            vacio.setLayout(new BoxLayout(vacio, BoxLayout.Y_AXIS));
            vacio.setBackground(InstaColores.CARD);
            vacio.setBorder(BorderFactory.createEmptyBorder(26, 28, 26, 28));
            vacio.setMaximumSize(new Dimension(340, 120));
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblvacio = new JLabel("No hay mensajes todavia");
            lblvacio.setFont(UIConstantes.TEXTO_FONT);
            lblvacio.setForeground(InstaColores.TEXTO_SECUNDARIO);
            lblvacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            vacio.add(lblvacio);
            PanelMensajes.add(Box.createVerticalStrut(20));
            PanelMensajes.add(lblvacio);
        } else {
            for (Mensaje mensaje : mensajes) {                
                boolean esmio = mensaje.getEmisor().equalsIgnoreCase(sessionManager.getUsuarioActual());
                MensajeBurbuja burbuja = new MensajeBurbuja(mensaje, esmio);
                burbuja.setAlignmentX(Component.LEFT_ALIGNMENT);
                PanelMensajes.add(burbuja);
            }
        }
        
        RefrescarPanelMensajes();
        BajarScroll();
    }
    
    private void EnviarMensajeActual() {
        String contenido = TxtMensaje.getText().trim();
        
        if (UsuarioChatActual == null || UsuarioChatActual.isBlank()) {
            MostrarEstado("Selecciona una conversacion primero");
            return;
        }
        
        if (contenido.isBlank()) {
            MostrarEstado("Escribe un mensaje");
            return;
        }
                
        ManejoConexionChat chat = ObtenerChat();
        
        if (chat == null) {
            MostrarEstado("No hay conexion de chat");
            return;
        }
        
        boolean enviado = chat.EnviarTexto(UsuarioChatActual, contenido);
                
        if (!enviado) {
            MostrarEstado("No se pudo enviar el mensaje");
            return;
        }
        
        TxtMensaje.setText("");
        CargarMensajes(UsuarioChatActual);
        CargarConversaciones();
        LimpiarEstado();
        TxtMensaje.requestFocusInWindow();
    }
    
    public void AbrirChatDirecto(String otrousuario) {
        if (otrousuario == null || otrousuario.isBlank()) {
            return;
        }
        
        if (otrousuario.equalsIgnoreCase(sessionManager.getUsuarioActual())) {
            MostrarEstado("No puedes abrir un chat contigo mismo");
            return;
        }
        
        UsuarioChatActual = otrousuario;
        LblTituloChat.setText("Chat con @" + otrousuario);
        
        boolean existeenlinea = false; 
        
        for (int i = 0; i < ModeloConversaciones.size(); i++) {
            String actual = ModeloConversaciones.getElementAt(i);
            
            if (actual.equalsIgnoreCase(otrousuario)) {
                existeenlinea = true;
                ListaConversaciones.setSelectedIndex(i);
                break;
            }
        }
        
        if (!existeenlinea) {
            ModeloConversaciones.addElement(otrousuario);
            ListaConversaciones.setSelectedValue(otrousuario, true);
        }
        
        ManejoConexionChat chat = ObtenerChat();
        
        if (chat != null) {
            chat.MarcarConversacionComoLeida(otrousuario);
        }
        
        CargarMensajes(otrousuario);
        LimpiarEstado();
        TxtMensaje.requestFocusInWindow();
    }
    
    private void AbrirSelectorStickers() {
        if (UsuarioChatActual == null || UsuarioChatActual.isBlank()) {
            MostrarEstado("Seleccionar una conversacion primero");
            return;
        }
        
        JDialog dialogo = new JDialog(SwingUtilities.getWindowAncestor(this), "Stickers", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout());
        dialogo.getContentPane().setBackground(InstaColores.FONDO);
        
        StickerPickerPanel picker = new StickerPickerPanel(
                sessionManager.getUsuarioActual(),
                rutasticker -> {
                    ManejoConexionChat chat = ObtenerChat();
                    
                    if (chat == null) {
                        MostrarEstado("No hay conexion de chat");
                        return;
                    }
                    
                    boolean enviado = chat.EnviarSticker(UsuarioChatActual, rutasticker);
                    
                    if (!enviado) {
                        MostrarEstado("No se pudo enviar el sticker");
                        return;
                    }
                    
                    dialogo.dispose();
                    CargarMensajes(UsuarioChatActual);
                    CargarConversaciones();
                    LimpiarEstado();
                },
                () -> {
                }
        );
        
        dialogo.add(picker, BorderLayout.CENTER);
        dialogo.setSize(380, 470);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }
    
    private void LimpiarVistaChat() {
        LblTituloChat.setText("Selecciona una conversacion");
        PanelMensajes.removeAll();
        
        PanelRedondeado cardinfo = new PanelRedondeado(UIConstantes.ARCO_CARD);
        cardinfo.setLayout(new BoxLayout(cardinfo, BoxLayout.Y_AXIS));
        cardinfo.setBackground(InstaColores.CARD);
        cardinfo.setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        cardinfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblinfo = new JLabel("Selecciona una conversacion para comenzar");
        lblinfo.setFont(UIConstantes.TEXTO_FONT);
        lblinfo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblinfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cardinfo.add(lblinfo);
        
        PanelMensajes.add(Box.createVerticalStrut(22));
        PanelMensajes.add(cardinfo);
        
        RefrescarPanelMensajes();
    }
    
    public void ReiniciarInbox() {
        UsuarioChatActual = null;
        ModeloConversaciones.clear();
        ListaConversaciones.clearSelection();
        TxtMensaje.setText("");
        LimpiarEstado();
        LimpiarVistaChat();
    }
    
    private void RefrescarPanelMensajes() {
        PanelMensajes.revalidate();
        PanelMensajes.repaint();
    }
    
    private void BajarScroll() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar barra = ScrollMensajes.getVerticalScrollBar();
            barra.setValue(barra.getMaximum());
        });
    }
    
    private void MostrarEstado(String mensaje) {
        LblEstado.setText(mensaje);
    }
    
    private void LimpiarEstado() {
        LblEstado.setText(" ");
    }
    
    @Override
    public void AlRecibirMensaje(PaqueteMensaje paquete) {
        SwingUtilities.invokeLater(() -> {
            CargarConversaciones();
            
            if (UsuarioChatActual != null) {
                boolean pertenecechatactual = paquete.getEmisor().equalsIgnoreCase(UsuarioChatActual) || paquete.getReceptor().equalsIgnoreCase(UsuarioChatActual);
                
                if (pertenecechatactual) {
                    CargarMensajes(UsuarioChatActual);
                }
            }
        });
    }
    
    @Override
    public void AlCambiarEstadoConexion(boolean conectado) {
        SwingUtilities.invokeLater(() -> {
            if (conectado) {
                MostrarEstado("Chat conectado");
            } else {
                MostrarEstado("Chat desconectado");
            }
        });
    }
}
 