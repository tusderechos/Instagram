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
import service.UsuarioService;
import UI.Componentes.BotonRedondeado;
import UI.Componentes.PanelRedondeado;
import UI.Core.SessionManager;
import interfaces.NavigationListener;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {
    
    private final UsuarioService usuarioService;
    private final SessionManager sessionManager;
    private final NavigationListener navigationListener;
    
    private JTextField TxtUsuario;
    private JPasswordField TxtContrasena;
    private JLabel LblEstado;
    
    private static final String HOST_CHAT = "localhost";
    private static final int PUERTO_CHAT = 5050;
    
    public LoginPanel(SessionManager sessionManager, NavigationListener navigationListener) {
        usuarioService = new UsuarioService();
        this.sessionManager = sessionManager;
        this.navigationListener = navigationListener;
        
        setLayout(new GridBagLayout());
        setBackground(InstaColores.FONDO);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        
        PanelRedondeado maincard = new PanelRedondeado(UIConstantes.ARCO_CARD);
        maincard.setLayout(new BoxLayout(maincard, BoxLayout.Y_AXIS));
        maincard.setPreferredSize(new Dimension(420, 430));
        maincard.setBackground(InstaColores.CARD);
        maincard.setBorder(new EmptyBorder(30, 36, 28, 36));
        
        JLabel lbllogo = new JLabel("Instagram");
        lbllogo.setFont(UIConstantes.LOGO_FONT);
        lbllogo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbllogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("Inicia sesion para continuar");
        lblsubtitulo.setFont(UIConstantes.TEXTO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblusuario = CrearLabelCampo("Usuario");
        TxtUsuario = CrearCampoTexto("Usuario");
        
        JLabel lblcontrasena = CrearLabelCampo("Contraseña");
        TxtContrasena = CrearCampoPassword("Contraseña");
        
        BotonRedondeado btnlogin = new BotonRedondeado("Iniciar Sesion");
        btnlogin.setPreferredSize(new Dimension(320, UIConstantes.ALTURA_BOTON));
        btnlogin.setMaximumSize(new Dimension(320, UIConstantes.ALTURA_BOTON));
        btnlogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnlogin.addActionListener(e -> IniciarSesion());
        
        JButton btnirregistro = new JButton("No tienes cuenta? Registrate");
        btnirregistro.setFont(UIConstantes.PEQUENO_FONT);
        btnirregistro.setForeground(InstaColores.AZUL);
        btnirregistro.setContentAreaFilled(false);
        btnirregistro.setBorderPainted(false);
        btnirregistro.setFocusPainted(false);
        btnirregistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnirregistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnirregistro.addActionListener(e -> navigationListener.irARegistro());
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setForeground(InstaColores.ERROR);
        LblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        LblEstado.setMaximumSize(new Dimension(320, 20));
        
        maincard.add(Box.createVerticalStrut(2));
        maincard.add(lbllogo);
        maincard.add(Box.createVerticalStrut(6));
        maincard.add(lblsubtitulo);
        maincard.add(Box.createVerticalStrut(22));
        
        maincard.add(lblusuario);
        maincard.add(Box.createVerticalStrut(5));
        maincard.add(TxtUsuario);
        maincard.add(Box.createVerticalStrut(12));
        
        maincard.add(lblcontrasena);
        maincard.add(Box.createVerticalStrut(5));
        maincard.add(TxtContrasena);
        maincard.add(Box.createVerticalStrut(20));
        
        maincard.add(btnlogin);
        maincard.add(Box.createVerticalStrut(12));
        maincard.add(LblEstado);
        maincard.add(Box.createVerticalStrut(20));
        maincard.add(btnirregistro);
        
        add(maincard, gbc);
    }
    
    private JLabel CrearLabelCampo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }
    
    private JTextField CrearCampoTexto(String texto) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(320, UIConstantes.ALTURA_BOTON));
        field.setPreferredSize(new Dimension(320, UIConstantes.ALTURA_BOTON));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        field.setToolTipText(texto);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return field;
    }
    
    private JPasswordField CrearCampoPassword(String texto) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(320, UIConstantes.ALTURA_BOTON));
        field.setPreferredSize(new Dimension(320, UIConstantes.ALTURA_BOTON));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        field.setToolTipText(texto);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return field;
    }
    
    private void IniciarSesion() {
        String usuario = TxtUsuario.getText().trim();
        String contrasena = new String(TxtContrasena.getPassword());
        
        if (usuario.isBlank() || contrasena.isBlank()) {
            MostrarError("Completa usuario y contrasena");
            return;
        }
        
        boolean login = usuarioService.Login(usuario, contrasena);
        
        if (!login) {
            MostrarError("Credenciales invalidas o cuenta desactivada");
            return;
        }
        
        ManejoConexionChat manejochat = new ManejoConexionChat(usuario);
        boolean conectadochat = manejochat.Conectar(HOST_CHAT, PUERTO_CHAT);
        
        if (!conectadochat) {
            String estado = manejochat.getUltimoErrorConexion();
            
            if (estado.contains("LOGIN_DUPLICADO")) {
                MostrarError("Esta cuenta ya esta abierta en otra sesion");
            } else {
                MostrarError("No se pudo iniciar la sesion de chat");
            }
            
            sessionManager.setManejoConexionChat(null);
            return;
        }
        
        sessionManager.setUsuarioActual(usuario);
        sessionManager.setManejoConexionChat(manejochat);
        
        LimpiarCampos();
        LimpiarEstado();
        
        navigationListener.irAApp();
    }   
    
    public void MostrarMensajePendiente() {
        String mensaje = sessionManager.ConsumirMensajePendienteLogin();
        
        if (mensaje != null && !mensaje.isBlank()) {
            MostrarError(mensaje);
        } else {
            LimpiarEstado();
        }
    }
    
    private void MostrarError(String mensaje) {
        LblEstado.setForeground(InstaColores.ERROR);
        LblEstado.setText(mensaje);
    }
    
    private void MostrarAviso(String mensaje) {
        LblEstado.setForeground(InstaColores.AZUL_OSCURO);
        LblEstado.setText(mensaje);
    }
    
    private void LimpiarEstado() {
        LblEstado.setText(" ");
    }
    
    private void LimpiarCampos() {
        TxtUsuario.setText("");
        TxtContrasena.setText("");
    }
}
