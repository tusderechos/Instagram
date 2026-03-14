/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Frame;

/**
 *
 * @author USUARIO
 */

import Data.Paths.Paths;
import UI.Core.SessionManager;
import interfaces.NavigationListener;
import UI.Paneles.AppPanel;
import UI.Paneles.LoginPanel;
import UI.Paneles.RegistroPanel;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements NavigationListener {
    
    private final CardLayout cardLayout;
    private final JPanel ContenedorMain;
    
    private final SessionManager sessionManager;
    
    private final LoginPanel loginPanel;
    private final RegistroPanel registroPanel;
    private final AppPanel appPanel;
    
    public MainFrame() {
        Paths.InicializarSistema();
        
        sessionManager = new SessionManager();
        cardLayout = new CardLayout();
        ContenedorMain = new JPanel(cardLayout);
        
        loginPanel = new LoginPanel(sessionManager, this);
        registroPanel = new RegistroPanel(this);
        appPanel = new AppPanel(sessionManager, this);
        
        setTitle("Insta - Red Social");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIConstantes.DESKTOP_WIDTH, UIConstantes.DESKTOP_HEIGHT);
        setMinimumSize(new Dimension(1180, 700));
        setLocationRelativeTo(null);
        
        ContenedorMain.setBackground(InstaColores.FONDO);
        
        ContenedorMain.add(loginPanel, "LOGIN");
        ContenedorMain.add(registroPanel, "REGISTRO");
        ContenedorMain.add(appPanel, "APP");
        
        setContentPane(ContenedorMain);
        irALogin();
    }
    
    @Override
    public void irALogin() {
        cardLayout.show(ContenedorMain, "LOGIN");
    }
    
    @Override
    public void irARegistro() {
        cardLayout.show(ContenedorMain, "REGISTRO");
    }
    
    @Override
    public void irAApp() {
        appPanel.RefrescarDatos();
        cardLayout.show(ContenedorMain, "APP");
    }
}
