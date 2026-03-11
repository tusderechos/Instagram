/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.raf.FollowersRAF;
import Data.raf.FollowingRAF;
import Data.raf.RequestsRAF;
import modelo.Solicitud;
import modelo.Usuario;
import enums.EstadoSolicitud;
import enums.TipoCuenta;

import java.util.ArrayList;

public class FollowService {
    
    private final UsuarioService usuarioService;
    
    public FollowService() {
        usuarioService = new UsuarioService();
    }
    
    public boolean Seguir(String seguidor, String seguido) {
        if (seguidor == null || seguido == null) {
            return false;
        }
        
        if (seguidor.equalsIgnoreCase(seguido)) {
            return false;
        }
        
        Usuario usuarioseguidor = usuarioService.BuscarUsuario(seguidor);
        Usuario usuarioseguido = usuarioService.BuscarUsuario(seguidor);
        
        if (usuarioseguidor == null || usuarioseguido == null) {
            return false;
        }
        
        if (!usuarioseguidor.isActivo() || !usuarioseguido.isActivo()) {
            return false;
        }
        
        FollowingRAF followingRAF = new FollowingRAF(seguidor);
        FollowersRAF followersRAF = new FollowersRAF(seguido);
        
        if (followingRAF.ExisteRelacion(seguido) || followersRAF.ExisteRelacion(seguidor)) {
            return false;
        }
        
        if (usuarioseguido.getTipoCuenta() == TipoCuenta.PRIVADA) {
            return false;
        }
        
        boolean agregadofollowing = followersRAF.Agregar(seguido);
        boolean agregadofollowers = followersRAF.Agregar(seguidor);
        
        return agregadofollowing && agregadofollowers;
    }
    
    public boolean AceptarSolicitudYSeguir(String receptor, String emisor) {
        Usuario usuarioreceptor = usuarioService.BuscarUsuario(receptor);
        Usuario usuarioemisor = usuarioService.BuscarUsuario(emisor);
        
        if (usuarioreceptor == null || usuarioemisor == null) {
            return false;
        }
        
        RequestsRAF requestsRAF = new RequestsRAF(receptor);
        Solicitud solicitud = requestsRAF.Buscar(emisor, receptor);
        
        if (solicitud == null || solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            return false;
        }
        
        FollowingRAF followingRAF = new FollowingRAF(receptor);
        FollowersRAF followersRAF = new FollowersRAF(emisor);
        
        if (followingRAF.ExisteRelacion(receptor) || followersRAF.ExisteRelacion(emisor)) {
            return false;
        }
        
        boolean solicitudaceptada = requestsRAF.CambiarEstado(emisor, receptor, EstadoSolicitud.ACEPTADA);
        boolean agregadofollowing = followingRAF.Agregar(receptor);
        boolean agregadofollowers = followersRAF.Agregar(emisor);
        
        return solicitudaceptada && agregadofollowing && agregadofollowers;
    }
    
    public boolean DejarDeSeguir(String seguidor, String seguido) {
        Usuario usuarioseguidor = usuarioService.BuscarUsuario(seguidor);
        Usuario usuarioseguido = usuarioService.BuscarUsuario(seguido);
        
        if (usuarioseguidor == null || usuarioseguido == null) {
            return false;
        }
        
        FollowingRAF followingRAF = new FollowingRAF(seguidor);
        FollowersRAF followersRAF = new FollowersRAF(seguido);
        
        boolean eliminadofollowing = followingRAF.Eliminar(seguido);
        boolean eliminadofollowers = followersRAF.Eliminar(seguidor);
        
        return eliminadofollowing && eliminadofollowers;
    }
    
    public boolean SigueA(String seguidor, String seguido) {
        Usuario usuarioseguidor = usuarioService.BuscarUsuario(seguidor);
        Usuario usuarioseguido = usuarioService.BuscarUsuario(seguido);
        
        if (usuarioseguidor == null || usuarioseguido == null) {
            return false;
        }
        
        FollowingRAF followingRAF = new FollowingRAF(seguidor);
        return followingRAF.ExisteRelacion(seguido);
    }
    
    public ArrayList<String> ListarFollowersDe(String usuario) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return new ArrayList<>();
        }
        
        FollowersRAF followersRAF = new FollowersRAF(usuario);
        return followersRAF.ListarUsuarios();
    }
    
    public int ContarFollowersDe(String usuario) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return 0;
        }
        
        FollowersRAF followersRAF = new FollowersRAF(usuario);
        return followersRAF.Contar();
    }
    
    public int ContarFollowingDe(String usuario) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return 0;
        }
        
        FollowingRAF followingRAF = new FollowingRAF(usuario);
        return followingRAF.Contar();
    }
}
