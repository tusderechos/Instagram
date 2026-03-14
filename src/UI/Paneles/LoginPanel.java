/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

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
    
    public LoginPanel(SessionManager sessionManager, NavigationListener navigationListener) {
        usuarioService = new UsuarioService();
        this.sessionManager = sessionManager;
        this.navigationListener = navigationListener;
        
        setLayout(new GridBagLayout());
        setBackground(InstaColores.FONDO);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(12, 12, 12, 12);
        
        PanelRedondeado maincard = new PanelRedondeado(UIConstantes.ARCO_CARD);
        maincard.setLayout(new BoxLayout(maincard, BoxLayout.Y_AXIS));
        maincard.setPreferredSize(new Dimension(380, 450));
        maincard.setBorder(new EmptyBorder(35, 35, 35, 35));
        
        JLabel lbllogo = new JLabel("Instagram");
        lbllogo.setFont(UIConstantes.TITULO_FONT);
        lbllogo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbllogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        TxtUsuario = CrearCampoTexto("Usuario");
        TxtContrasena = CrearCampoPassword("Contraseña");
        
        BotonRedondeado btnlogin = new BotonRedondeado("Iniciar Sesion");
        btnlogin.setPreferredSize(new Dimension(290, 42));
        btnlogin.setMaximumSize(new Dimension(290, 42));
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
        
        maincard.add(Box.createVerticalStrut(10));
        maincard.add(lbllogo);
        maincard.add(Box.createVerticalStrut(35));
        maincard.add(TxtUsuario);
        maincard.add(Box.createVerticalStrut(12));
        maincard.add(TxtContrasena);
        maincard.add(Box.createVerticalStrut(18));
        maincard.add(btnlogin);
        maincard.add(Box.createVerticalStrut(12));
        maincard.add(LblEstado);
        maincard.add(Box.createVerticalGlue());
        maincard.add(btnirregistro);
        
        add(maincard, gbc);
    }
    
    private JTextField CrearCampoTexto(String texto) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(290, 42));
        field.setPreferredSize(new Dimension(290, 42));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        field.setToolTipText(texto);
        
        return field;
    }
    
    private JPasswordField CrearCampoPassword(String texto) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(290, 42));
        field.setPreferredSize(new Dimension(290, 42));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        field.setToolTipText(texto);
        
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
        
        sessionManager.setUsuarioActual(usuario);
        LimpiarCampos();
        LimpiarEstado();
        navigationListener.irAApp();
    }   
    
    private void MostrarError(String mensaje) {
        LblEstado.setForeground(InstaColores.ERROR);
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
