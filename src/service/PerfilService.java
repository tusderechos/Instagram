/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import modelo.Solicitud;
import modelo.Usuario;
import enums.EstadoPerfil;
import enums.EstadoSolicitud;
import enums.TipoCuenta;

public class PerfilService {
    
    private final UsuarioService usuarioService;
    private final FollowService followService;
    private final SolicitudService solicitudService;
    
    public PerfilService() {
        usuarioService = new UsuarioService();
        followService = new FollowService();
        solicitudService = new SolicitudService();
    }
    
    public boolean esPerfilPropio(String viewer, String owner) {
        if (viewer == null || owner == null) {
            return false;
        }
        
        return viewer.equalsIgnoreCase(owner);
    }
    
    public boolean PuedeVerPerfilCompleto(String viewer, String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return false;
        }
        
        if (esPerfilPropio(viewer, owner)) {
            return true;
        }
        
        Usuario usuarioviewer = usuarioService.BuscarUsuario(viewer);
        
        if (usuarioviewer == null || !usuarioviewer.isActivo()) {
            return false;
        }
        
        if (usuarioowner.getTipoCuenta() == TipoCuenta.PUBLICA) {
            return true;
        }
        
        return followService.SigueA(viewer, owner);
    }
    
    public boolean PuedeVerPublicaciones(String viewer, String owner) {
        return PuedeVerPerfilCompleto(viewer, owner);
    }
    
    public boolean PuedeEnviarMensaje(String viewer, String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return false;
        }
        
        if (esPerfilPropio(viewer, owner)) {
            return false;
        }
        
        Usuario usuarioviewer = usuarioService.BuscarUsuario(viewer);
        
        if (usuarioviewer == null || !usuarioviewer.isActivo()) {
            return false;
        }
        
        if (usuarioowner.getTipoCuenta() == TipoCuenta.PUBLICA) {
            return true;
        }
        
        return followService.SigueA(viewer, owner);
    }
    
    public EstadoPerfil ObtenerEstadoPerfil(String viewer, String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return EstadoPerfil.NO_DISPONIBLE;
        }
        
        if (esPerfilPropio(viewer, owner)) {
            return EstadoPerfil.EDITAR_PERFIL;
        }
        
        Usuario usuarioviewer = usuarioService.BuscarUsuario(viewer);
        
        if (usuarioviewer == null || !usuarioviewer.isActivo()) {
            return EstadoPerfil.NO_DISPONIBLE;
        }
        
        if (followService.SigueA(viewer, owner)) {
            return EstadoPerfil.SIGUIENDO;
        }
        
        if (usuarioowner.getTipoCuenta() == TipoCuenta.PRIVADA) {
            Solicitud solicitud = solicitudService.BuscarSolicitud(viewer, owner);
            
            if (solicitud != null && solicitud.isActivo() && solicitud.getEstado() == EstadoSolicitud.PENDIENTE) {
                return EstadoPerfil.SOLICITUD_ENVIADA;
            }
        }
        
        return EstadoPerfil.SEGUIR;
    }
    
    public boolean PerfilDisponible(String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        return usuarioowner != null && usuarioowner.isActivo();
    }
    
    public boolean PerfilEsPublico(String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return false;
        }
        
        return usuarioowner.getTipoCuenta() == TipoCuenta.PUBLICA;
    }
    
    public boolean PerfilEsPrivado(String owner) {
        Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
        
        if (usuarioowner == null || !usuarioowner.isActivo()) {
            return false;
        }
        
        return usuarioowner.getTipoCuenta() == TipoCuenta.PRIVADA;
    }
}
