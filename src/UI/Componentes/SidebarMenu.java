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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarMenu extends JPanel {
    
    private final SessionManager sessionManager;
    private final NavigationListener navigationListener;
    private final AppNavigator appNavigator;
    
    private JLabel LblSesion;
    
    public SidebarMenu(SessionManager sessionManager, NavigationListener navigationListener, AppNavigator appNavigator) {
        this.sessionManager = sessionManager;
        this.navigationListener = navigationListener;
        this.appNavigator = appNavigator;
        
        setBackground(InstaColores.FONDO);
        setPreferredSize(new Dimension(260, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, InstaColores.DIVISOR));
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(28, 20, 34, 20));
        
        JLabel lbllogo = new JLabel("Instagram");
        lbllogo.setFont(UIConstantes.LOGO_FONT);
        lbllogo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbllogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("Navegacion");
        lblsubtitulo.setFont(UIConstantes.PEQUENO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        top.add(lbllogo);
        top.add(Box.createVerticalStrut(4));
        top.add(lblsubtitulo);
        top.add(Box.createVerticalStrut(28));
        top.add(CrearBotonMenu("🏠 Home", () -> appNavigator.irAFeed()));
        top.add(Box.createVerticalStrut(8));
        top.add(CrearBotonMenu("🔎 Buscar", () -> appNavigator.irABuscar()));
        top.add(Box.createVerticalStrut(8));
        top.add(CrearBotonMenu("➕ Crear", () -> appNavigator.irACrearPost()));
        top.add(Box.createVerticalStrut(8));
        top.add(CrearBotonMenu("💬 Inbox", () -> appNavigator.irAInbox()));
        top.add(Box.createVerticalStrut(8));
        top.add(CrearBotonMenu("👥 Solicitudes", () -> appNavigator.irASolicitudes()));
        top.add(Box.createVerticalStrut(8));
        top.add(CrearBotonMenu("👤 Perfil", () -> appNavigator.irAPerfil(sessionManager.getUsuarioActual())));
        
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(BorderFactory.createEmptyBorder(30, 20, 25, 20));
        
        JSeparator separador = new JSeparator();
        separador.setForeground(InstaColores.DIVISOR);
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblcuenta = new JLabel("Sesion activa");
        lblcuenta.setFont(UIConstantes.PEQUENO_FONT);
        lblcuenta.setForeground(InstaColores.TEXTO_APAGADO);
        lblcuenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        LblSesion = new JLabel("@");
        LblSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        LblSesion.setForeground(InstaColores.TEXTO_PRIMARIO);
        LblSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton btncerrar = new JButton("Cerrar Sesion");
        btncerrar.setFont(UIConstantes.TEXTO_FONT);
        btncerrar.setForeground(InstaColores.ERROR);
        btncerrar.setFocusPainted(false);
        btncerrar.setBorderPainted(false);
        btncerrar.setContentAreaFilled(false);
        btncerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btncerrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btncerrar.setHorizontalAlignment(SwingConstants.LEFT);
        btncerrar.setMargin(new Insets(0, 0, 0, 0));
        btncerrar.addActionListener(e -> {
            sessionManager.CerrarSesion();
            RefrescarSesion();
            navigationListener.irALogin();
        });
        
        bottom.add(separador);
        bottom.add(Box.createVerticalStrut(14));
        bottom.add(lblcuenta);
        bottom.add(Box.createVerticalStrut(4));
        bottom.add(LblSesion);
        bottom.add(Box.createVerticalStrut(12));
        bottom.add(btncerrar);
        
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        
        RefrescarSesion();
    }
    
    private JButton CrearBotonMenu(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 17));
        boton.setForeground(InstaColores.TEXTO_PRIMARIO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(InstaColores.CARD);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        boton.setPreferredSize(new Dimension(210, 46));
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER_SUAVE), BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(InstaColores.FONDO_SECUNDARIO);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(InstaColores.CARD);
            }
        });
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
