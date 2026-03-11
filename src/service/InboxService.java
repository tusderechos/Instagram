/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.raf.InboxRAF;
import modelo.Mensaje;
import modelo.Usuario;
import enums.TipoMensaje;
import util.FechaUtil;

import java.util.ArrayList;

public class InboxService {
    
    private final UsuarioService usuarioService;
    private final PerfilService perfilService;
    
    public InboxService() {
        usuarioService = new UsuarioService();
        perfilService = new PerfilService();
    }
    
    public boolean EnviarMensajeTexto(String emisor, String receptor, String contenido) {
        return EnviarMensaje(emisor, receptor, contenido, TipoMensaje.TEXTO);
    }
    
    public boolean EnviarSticker(String emisor, String receptor, String rutasticker) {
        return EnviarMensaje(emisor, receptor, rutasticker, TipoMensaje.STICKER);
    }
    
    private boolean EnviarMensaje(String emisor, String receptor, String contenido, TipoMensaje tipomensaje) {
        if (emisor == null || receptor == null || contenido == null) {
            return false;
        }
        
        if (emisor.equalsIgnoreCase(receptor)) {
            return false;
        }
        
        Usuario usuarioemisor = usuarioService.BuscarUsuario(emisor);
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        
        if (usuarioemisor == null || usuarioreceptor == null) {
            return false;
        }
        
        if (!usuarioemisor.isActivo() || !usuarioreceptor.isActivo()) {
            return false;
        }
        
        if (!perfilService.PuedeEnviarMensaje(emisor, receptor)) {
            return false;
        }
        
        if (contenido.length() > 300) {
            return false;
        }
        
        Mensaje mensaje = new Mensaje(emisor, receptor, FechaUtil.hoy(), FechaUtil.ahora(), contenido, tipomensaje);
        
        InboxRAF inboxreceptor = new InboxRAF(receptor);
        InboxRAF inboxemisor = new InboxRAF(emisor);
        
        boolean guardadoenreceptor = inboxreceptor.Agregar(mensaje);
        boolean guardadoenemisor = inboxemisor.Agregar(mensaje);
        
        return guardadoenreceptor && guardadoenemisor;
    }
    
    public ArrayList<Mensaje> ObtenerConversacion(String owner, String otrouser) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        Usuario usuariootro = usuarioService.BuscarUsuario(otrouser);
        
        if (usuarioowner == null || usuariootro == null) {
            return new ArrayList<>();
        }
        
        InboxRAF inboxRAF = new InboxRAF(owner);
        ArrayList<Mensaje> conversacion = inboxRAF.ObtenerConversacion(otrouser);
        
        OrdenarMensajesPorFecha(conversacion);
        return conversacion;
    }
    
    public ArrayList<String> ListarConversaciones(String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return new ArrayList<>();
        }
        
        InboxRAF inboxRAF = new InboxRAF(owner);
        ArrayList<Mensaje> mensajes = inboxRAF.ListarTodos();
        ArrayList<String> conversaciones = new ArrayList<>();
        
        for(Mensaje mensaje : mensajes) {
            String otrousuario;
            
            if (mensaje.getEmisor().equalsIgnoreCase(owner)) {
                otrousuario = mensaje.getReceptor();
            } else {
                otrousuario = mensaje.getEmisor();
            }
            
            if (!otrousuario.equalsIgnoreCase(owner) && !conversaciones.contains(otrousuario)) {
                conversaciones.add(otrousuario);
            }
        }
        
        return conversaciones;
    }
    
    public boolean MarcarConversacionesComoLeida(String owner, String otrousuario) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        Usuario usuariootro = usuarioService.BuscarUsuario(otrousuario);
        
        if (usuarioowner == null || usuariootro == null) {
            return false;
        }
        
        InboxRAF inboxRAF = new InboxRAF(owner);
        return inboxRAF.MarcarLeidos(otrousuario, owner);
    }
    
    public int ContarNoLeidos(String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return 0;
        }
        
        InboxRAF inboxRAF = new InboxRAF(owner);
        return inboxRAF.ContarNoLeidos(owner);
    }
    
    public int ContarNoLeidosDe(String owner, String otrousuario) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        Usuario usuariootro = usuarioService.BuscarUsuario(otrousuario);
        
        if (usuarioowner == null || usuariootro == null) {
            return 0;
        }
        
        InboxRAF inboxRAF = new InboxRAF(owner);
        return inboxRAF.ContarNoLeidosRecibidorDe(otrousuario, owner);
    }
    
    public boolean EliminarConversacion(String owner, String otrousuario) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        Usuario usuariootro = usuarioService.BuscarUsuario(otrousuario);
        
        if (usuarioowner == null || usuariootro == null) {
            return false;
        }
        
        InboxRAF inboxowner = new InboxRAF(owner);
        InboxRAF inboxotro = new InboxRAF(otrousuario);
        
        boolean eliminadoowner = inboxowner.EliminarConversacion(owner, otrousuario);
        boolean eliminadootro = inboxotro.EliminarConversacion(owner, otrousuario);
        
        return eliminadoowner && eliminadootro;
    }
    
    private void OrdenarMensajesPorFecha(ArrayList<Mensaje> mensajes) {
        OrdenarRec(mensajes, mensajes.size());
    }
    
    private void OrdenarRec(ArrayList<Mensaje> mensajes, int numero) {
        if (numero <= 1) {
            return;
        }
        
        for (int i = 0; i < numero - 1; i++) {
            String actual = mensajes.get(i).getFecha() + mensajes.get(i).getHora();
            String siguiente = mensajes.get(i + 1).getFecha() + mensajes.get(i + 1).getHora();
            
            if (actual.compareTo(siguiente) > 0) {
                Mensaje aux = mensajes.get(i);
                mensajes.set(i, mensajes.get(i + 1));
                mensajes.set(i + 1, aux);
            }
        }
        
        OrdenarRec(mensajes, numero - 1);
    }
}
