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
        
        if (tipomensaje == TipoMensaje.TEXTO && contenido.length() > 300) {
            return false;
        }
        
        Mensaje mensaje = new Mensaje(emisor, receptor, FechaUtil.hoy(), FechaUtil.ahora(), contenido, tipomensaje);
        
        System.out.println("tipo mensaje: " + tipomensaje);
        System.out.println("contenido recibido: " + contenido);
        System.out.println("longitud contenido: " + contenido.length());
        
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
    
    public ArrayList<String> ListarConversacionesOrdenadas(String owner) {
        ArrayList<String> conversaciones = ListarConversaciones(owner);
        OrdenarConversacionesPorUltimoMensaje(owner, conversaciones, conversaciones.size());
        return conversaciones;
    }
    
    public Mensaje ObtenerUltimoMensajeDe(String owner, String otrousuario) {
        ArrayList<Mensaje> conversacion = ObtenerConversacion(owner, otrousuario);
        
        if (conversacion.isEmpty()) {
            return null;
        }
        
        return conversacion.get(conversacion.size() - 1);
    }
    
    public String ObtenerPrevoewUltimoMensaje(String owner, String otrousuario) {
        Mensaje ultimo = ObtenerUltimoMensajeDe(owner, otrousuario);
        
        if (ultimo == null) {
            return "";
        }
        
        String preview;
        
        if (ultimo.getTipoMensaje() == TipoMensaje.STICKER) {
            preview = "Sticker";
        } else {
            preview = ultimo.getContenido();
        }
        
        if (preview == null) {
            return "";
        }
        
        preview = preview.trim();
        
        if (preview.length() > 22) {
            preview = preview.substring(0, 22) + "...";
        }
        
        return preview;
    }
    
    public String ObtenerHoraUltimoMensajeDe(String owner, String otrousuario) {
        Mensaje ultimo = ObtenerUltimoMensajeDe(owner, otrousuario);
        
        if (ultimo == null || ultimo.getHora() == null || ultimo.getHora().isBlank()) {
            return "";
        }
        
        if (ultimo.getHora().length() >= 5) {
            return ultimo.getHora().substring(0, 5);
        }
        
        return ultimo.getHora();
    }
    
    private void OrdenarConversacionesPorUltimoMensaje(String owner, ArrayList<String> conversaciones, int numero) {
        if (numero <= 1) {
            return;
        }
        
        for (int i = 0; i < numero - 1; i++) {
            String tiempoactual = ObtenerMarcaTiempoUltimoMensaje(owner, conversaciones.get(i));
            String tiemposiguiente = ObtenerMarcaTiempoUltimoMensaje(owner, conversaciones.get(i + 1));
            
            if (tiempoactual.compareTo(tiemposiguiente) < 0) {
                String aux = conversaciones.get(i);
                conversaciones.set(i, conversaciones.get(i + 1));
                conversaciones.set(i + 1, aux);
            }
        }
        
        OrdenarConversacionesPorUltimoMensaje(owner, conversaciones, numero - 1);
    }
    
    private String ObtenerMarcaTiempoUltimoMensaje(String owner, String otrousuario) {
        Mensaje ultimo = ObtenerUltimoMensajeDe(owner, otrousuario);
        
        if (ultimo == null) {
            return "";
        }
        
        String fecha = ultimo.getFecha() != null ? ultimo.getFecha() : "";
        String hora = ultimo.getHora() != null ? ultimo.getHora() : "";
        
        return fecha + hora;
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
