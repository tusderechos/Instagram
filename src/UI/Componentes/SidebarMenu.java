/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import interfaces.AppNavigator;
import interfaces.NavigationListener;
import UI.Core.SessionManager;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;

import javax.swing.*;
import java.awt.*;

public class SidebarMenu extends JPanel {
    
    private final SessionManager sessionManager;
    private final NavigationListener navigationListener;
    private final AppNavigator appNavigator;
    
    private JLabel LblSesion;
    
    public SidebarMenu(SessionManager sessionManager, NavigationListener navigationListener, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.navigationListener = navigationListener;
        this.appNavigator = appNavigator;
        
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, InstaColores.BORDER));
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JLabel lbllogo = new JLabel("Instagram");
        lbllogo.setFont(UIConstantes.LOGO_FONT);
        lbllogo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbllogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        top.add(lbllogo);
        top.add(Box.createVerticalStrut(35));
        top.add(CrearBotonMenu("Home", () -> appNavigator.irAFeed()));
        top.add(Box.createVerticalStrut(10));
        top.add(CrearBotonMenu("Buscar", () -> appNavigator.irABuscar()));
        top.add(Box.createVerticalStrut(10));
        top.add(CrearBotonMenu("Crear", () -> appNavigator.irACrearPost()));
        top.add(Box.createVerticalStrut(10));
        top.add(CrearBotonMenu("Inbox", () -> appNavigator.irAInbox()));
        top.add(Box.createVerticalStrut(10));
        top.add(CrearBotonMenu("Solicitudes", () -> appNavigator.irASolicitudes()));
        top.add(Box.createVerticalStrut(10));
        top.add(CrearBotonMenu("Perfil", () -> appNavigator.irAPerfil(sessionManager.getUsuarioActual())));
        
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(BorderFactory.createEmptyBorder(30, 20, 25, 20));
        
        LblSesion = new JLabel("@");
        LblSesion.setFont(UIConstantes.PEQUENO_FONT);
        LblSesion.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton btncerrar = new JButton("Cerrar Sesion");
        btncerrar.setFont(UIConstantes.TEXTO_FONT);
        btncerrar.setForeground(InstaColores.TEXTO_PRIMARIO);
        btncerrar.setFocusPainted(false);
        btncerrar.setBorderPainted(false);
        btncerrar.setContentAreaFilled(false);
        btncerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btncerrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btncerrar.addActionListener(e -> {
            sessionManager.CerrarSesion();
            RefrescarSesion();
            navigationListener.irALogin();
        });
        
        bottom.add(LblSesion);
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(btncerrar);
        
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        
        RefrescarSesion();
    }
    
    private JButton CrearBotonMenu(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 18));
        boton.setForeground(InstaColores.TEXTO_PRIMARIO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.addActionListener(e -> accion.run());
        
        return boton;
    }
    
    public void RefrescarSesion() {
        String usuario = sessionManager.getUsuarioActual();
        
        if (usuario == null || usuario.isBlank()) {
            LblSesion.setText("@");
        } else {
            LblSesion.setText("@" + usuario);
        }
    }
}
