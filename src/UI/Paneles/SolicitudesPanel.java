/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import UI.Componentes.SolicitudItemPanel;
import UI.Core.SessionManager;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import interfaces.AppNavigator;
import modelo.Solicitud;
import service.SolicitudService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import service.FollowService;

public class SolicitudesPanel extends JPanel {
    
    private final SessionManager sessionManager;
    private final AppNavigator appNavigator;
    private final SolicitudService solicitudService;
    
    private JPanel PanelLista;
    private JLabel LblEstado;
    
    public SolicitudesPanel(SessionManager sessionManager, AppNavigator appNavigator) {
     this.sessionManager = sessionManager;
     this.appNavigator = appNavigator;
     solicitudService = new SolicitudService();
     
        setLayout(new BorderLayout());
        setBackground(InstaColores.FONDO);
        
        JLabel lbltitulo = new JLabel("Solicitudes");
        lbltitulo.setFont(UIConstantes.SUBTITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        PanelLista = new JPanel();
        PanelLista.setLayout(new BoxLayout(PanelLista, BoxLayout.Y_AXIS));
        PanelLista.setBackground(InstaColores.FONDO);
        PanelLista.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        JScrollPane scroll = new JScrollPane(PanelLista);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblEstado.setBorder(new EmptyBorder(0, 20, 15, 20));
        
        add(lbltitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(LblEstado, BorderLayout.SOUTH);
    }
    
    public void CargarSolicitudes() {
        PanelLista.removeAll();
        
        String usuarioactual = sessionManager.getUsuarioActual();
        
        if (usuarioactual == null || usuarioactual.isBlank()) {
            MostrarEstado("No hay sesion activa");
            RefrescarVista();
            return;
        }
        
        ArrayList<Solicitud> pendientes = solicitudService.ListarPendientesDe(usuarioactual);
        
        if (pendientes.isEmpty()) {
            MostrarEstado("No tienes solcitudes pendientes");
            RefrescarVista();
            return;
        }
        
        for (Solicitud solicitud : pendientes) {
            String emisor = solicitud.getEmisor();
            
            SolicitudItemPanel item = new SolicitudItemPanel(emisor, () -> AceptarSolicitud(emisor), () -> RechazarSolicitud(emisor));
            
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            PanelLista.add(item);
            PanelLista.add(Box.createVerticalStrut(10));
        }
        
        MostrarEstado("Solicitudes pendientes: " + pendientes.size());
        RefrescarVista();
    }
    
    private void AceptarSolicitud(String emisor) {
        String receptor = sessionManager.getUsuarioActual();
        
        FollowService followservice = new FollowService();
        boolean aceptada = followservice.AceptarSolicitudYSeguir(receptor, emisor);
        
        if (!aceptada) {
            MostrarEstado("No se pudo aceptar la solicitud de @" + emisor);
            return;
        }
        
        MostrarEstado("Aceptaste la solicitud de @" + emisor);
        CargarSolicitudes();
    }
    
    private void RechazarSolicitud(String emisor) {
        String receptor = sessionManager.getUsuarioActual();
        boolean rechazada = solicitudService.RechazarSolicitud(receptor, emisor);
        
        if (!rechazada) {
            MostrarEstado("No se pudo rechazar la solicitud de @" + emisor);
            return;
        }
        
        MostrarEstado("Rechazaste la solicitud de @" + emisor);
        CargarSolicitudes();
    }
    
    private void MostrarEstado(String mensaje) {
        LblEstado.setText(mensaje);
    }
    
    private void RefrescarVista() {
        PanelLista.revalidate();
        PanelLista.repaint();
    }
}
