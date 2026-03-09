/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.raf.RequestsRAF;
import modelo.Solicitud;
import modelo.Usuario;
import enums.EstadoSolicitud;
import enums.TipoCuenta;
import util.FechaUtil;

import java.util.ArrayList;

public class SolicitudService {
    
    private final UsuarioService usuarioService;
    
    public SolicitudService() {
        usuarioService = new UsuarioService();
    }
    
    public boolean EnviarSolicitud(String emisor, String receptor) {
        if (emisor == null || receptor == null) {
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
        
        if (usuarioreceptor.getTipoCuenta() != TipoCuenta.PRIVADA) {
            return false;
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        
        if (requestsRAF.ExisteSolicitudPendiente(emisor, receptor)) {
            return false;
        }
        
        Solicitud solicitud = new Solicitud(emisor, receptor, FechaUtil.hoy(), FechaUtil.ahora());
        
        return requestsRAF.Agregar(solicitud);
    }
    
    public ArrayList<Solicitud> ListarPendientesDe(String receptor) {
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        
        if (usuarioreceptor == null || !usuarioreceptor.isActivo()) {
            return new ArrayList<>();
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        
        return requestsRAF.ListarPendientes();
    }
    
    public boolean AceptarSolicitud(String receptor, String emisor) {
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        Usuario usuarioemisor = usuarioService.BuscarUsuario(emisor);
        
        if (usuarioreceptor == null || usuarioemisor == null) {
            return false;
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        return requestsRAF.CambiarEstado(emisor, receptor, EstadoSolicitud.ACEPTADA);
    }
    
    public boolean RechazarSolicitud(String receptor, String emisor) {
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        Usuario usuarioemisor = usuarioService.BuscarUsuario(emisor);
        
        if (usuarioreceptor == null || usuarioemisor == null) {
            return false;
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        return requestsRAF.CambiarEstado(emisor, receptor, EstadoSolicitud.RECHAZADA);
    }
    
    public boolean CancelarSolicitud(String receptor, String emisor) {
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        Usuario usuarioemisor = usuarioService.BuscarUsuario(emisor);
        
        if (usuarioreceptor == null || usuarioemisor == null) {
            return false;
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        return requestsRAF.CambiarEstado(emisor, receptor, EstadoSolicitud.CANCELADA);
    }
    
    public Solicitud BuscarSolicitud(String emisor, String receptor) {
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        
        if (usuarioreceptor == null) {
            return null;
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        return requestsRAF.Buscar(emisor, receptor);
    }
}
