/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author USUARIO
 */

import enums.TipoMensaje;
import modelo.Mensaje;
import service.InboxService;
import util.FechaUtil;
import interfaces.MensajeChatListener;

import java.util.ArrayList;

public class ManejoConexionChat {
    
    private final String UsuarioActual;
    private final InboxService inboxService;
    
    private SocketClient socketClient;
    private MensajeChatListener Listener;
    
    public ManejoConexionChat(String UsuarioActual) {
        this.UsuarioActual = UsuarioActual;
        inboxService = new InboxService();
    }
    
    public boolean Conectar(String host, int puerto) {
        if (UsuarioActual == null || UsuarioActual.trim().isEmpty()) {
            return false;
        }
        
        socketClient = new SocketClient(host, puerto, UsuarioActual);
        
        socketClient.setListener(new MensajeChatListener() {
            @Override
            public void AlRecibirMensaje(PaqueteMensaje paquete) {
                if (Listener != null) {
                    Listener.AlRecibirMensaje(paquete);
                }
            }
            
            @Override
            public void AlCambiarEstadoConexion(boolean conectado) {
                if (Listener != null) {
                    Listener.AlCambiarEstadoConexion(conectado);
                }
            }
        });
        
        return socketClient.Conectar();
    }
    
    public void Desconectar() {
        if (socketClient != null) {
            socketClient.Desconectar();
        }
    }
    
    public boolean EnviarTexto(String receptor, String contenido) {
        boolean guardado = inboxService.EnviarMensajeTexto(UsuarioActual, receptor, contenido);
        
        if (!guardado) {
            return false;
        }
        
        PaqueteMensaje paquete = new PaqueteMensaje(UsuarioActual, receptor, FechaUtil.hoy(), FechaUtil.ahora(), contenido, TipoMensaje.TEXTO, false);
        
        if (socketClient != null && socketClient.isConectado()) {
            socketClient.EnviarMensaje(paquete);
        }
        
        return true;
    }
    
    public boolean EnviarSticker(String receptor, String rutasticker) {
        System.out.println("ruta sticker enviada: " + rutasticker);
        System.out.println("longitud ruta sticker: " + rutasticker.length());
        boolean guardado = inboxService.EnviarSticker(UsuarioActual, receptor, rutasticker);
        
        if (!guardado) {
            return false;
        }
        
        PaqueteMensaje paquete = new PaqueteMensaje(UsuarioActual, receptor, FechaUtil.hoy(), FechaUtil.ahora(), rutasticker, TipoMensaje.STICKER, false);
        
        if (socketClient != null && socketClient.isConectado()) {
            socketClient.EnviarMensaje(paquete);
        }
        
        return true;
    }
    
    public ArrayList<Mensaje> ObtenerConversacion(String otrousuario) {
        return inboxService.ObtenerConversacion(UsuarioActual, otrousuario);
    }
    
    public ArrayList<String> ListarConversaciones() {
        return inboxService.ListarConversaciones(UsuarioActual);
    }
    
    public int ContarNoLeidos() {
        return inboxService.ContarNoLeidos(UsuarioActual);
    }
    
    public int ContarNoLeidosDe(String otrousuario) {
        return inboxService.ContarNoLeidosDe(UsuarioActual, otrousuario);
    }
    
    public boolean MarcarConversacionComoLeida(String otrousuario) {
        return inboxService.MarcarConversacionesComoLeida(UsuarioActual, otrousuario);
    }
    
    public boolean EliminarConversacion(String otrousuario) {
        return inboxService.EliminarConversacion(UsuarioActual, otrousuario);
    }
    
    public ArrayList<String> ListarConversacionesOrdenadas() {
        return inboxService.ListarConversacionesOrdenadas(UsuarioActual);
    }
    
    public String ObtenerPreviewUltimoMensajeDe(String otrousuario) {
        return inboxService.ObtenerPrevoewUltimoMensaje(UsuarioActual, otrousuario);
    }    
    
    public String ObtenerHoraUltimoMensajeDe(String otrousuario) {
        return inboxService.ObtenerHoraUltimoMensajeDe(UsuarioActual, otrousuario);
    }
    
    public void setListener(MensajeChatListener Listener) {
        this.Listener = Listener;
    }
    
    public boolean isConectado() {
        return socketClient != null && socketClient.isConectado();
    }
    
    public String getUsuarioActual() {
        return UsuarioActual;
    }
}
