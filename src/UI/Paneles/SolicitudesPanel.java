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
import UI.Componentes.PanelRedondeado;
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
        
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(20, 24, 12, 24));
        
        JLabel lbltitulo = new JLabel("Solicitudes");
        lbltitulo.setFont(UIConstantes.TITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("Gestiona las solicitudes pendientes que recibiste");
        lblsubtitulo.setFont(UIConstantes.TEXTO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        header.add(lbltitulo);
        header.add(Box.createVerticalStrut(4));
        header.add(lblsubtitulo);
        
        PanelLista = new JPanel();
        PanelLista.setLayout(new BoxLayout(PanelLista, BoxLayout.Y_AXIS));
        PanelLista.setBackground(InstaColores.FONDO);
        PanelLista.setBorder(new EmptyBorder(8, 24, 20, 24));
        
        JScrollPane scroll = new JScrollPane(PanelLista);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(InstaColores.FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblEstado.setBorder(new EmptyBorder(0, 24, 16, 24));
        
        add(lbltitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(LblEstado, BorderLayout.SOUTH);
    }
    
    public void CargarSolicitudes() {
        PanelLista.removeAll();
        
        String usuarioactual = sessionManager.getUsuarioActual();
        
        if (usuarioactual == null || usuarioactual.isBlank()) {
            MostrarEstado("No hay sesion activa");
            MostrarEstadoVacio("Sesion no disponible", "Debes iniciar sesion para ver tus solicitudes");
            RefrescarVista();
            return;
        }
        
        ArrayList<Solicitud> pendientes = solicitudService.ListarPendientesDe(usuarioactual);
        
        if (pendientes.isEmpty()) {
            MostrarEstado("No tienes solcitudes pendientes");
            MostrarEstadoVacio("Sin solicitudes", "Cuando recibas solicitudes de seguimiento, apareceran aqui.");
            RefrescarVista();
            return;
        }
        
        for (Solicitud solicitud : pendientes) {
            String emisor = solicitud.getEmisor();
            
            SolicitudItemPanel item = new SolicitudItemPanel(emisor, () -> AceptarSolicitud(emisor), () -> RechazarSolicitud(emisor));
            
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            PanelLista.add(item);
            PanelLista.add(Box.createVerticalStrut(12));
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
    
    private void MostrarEstadoVacio(String titulo, String subtitulo) {
        PanelRedondeado cardvacia = new PanelRedondeado(UIConstantes.ARCO_CARD);
        cardvacia.setLayout(new BoxLayout(cardvacia, BoxLayout.Y_AXIS));
        cardvacia.setBackground(InstaColores.CARD);
        cardvacia.setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        cardvacia.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardvacia.setMaximumSize(new Dimension(700, 180));
        
        JLabel icono = new JLabel("👥");
        icono.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icono.setForeground(InstaColores.TEXTO_APAGADO);
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lbltitulo = new JLabel(titulo);
        lbltitulo.setFont(UIConstantes.SUBTITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_APAGADO);
        lbltitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("<html><div style='text-align:center;'>" + subtitulo + "</div></html>");
        lblsubtitulo.setFont(UIConstantes.TEXTO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cardvacia.add(Box.createVerticalGlue());
        cardvacia.add(icono);
        cardvacia.add(Box.createVerticalStrut(10));
        cardvacia.add(lbltitulo);
        cardvacia.add(Box.createVerticalStrut(8));
        cardvacia.add(lblsubtitulo);
        cardvacia.add(Box.createVerticalGlue());
        
        PanelLista.add(Box.createVerticalStrut(18));
        PanelLista.add(cardvacia);
    }
        
    private void RefrescarVista() {
        PanelLista.revalidate();
        PanelLista.repaint();
    }
}
