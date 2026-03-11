/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import modelo.Publicacion;
import modelo.Usuario;

import java.util.ArrayList;

public class FeedService {
    
    private final UsuarioService usuarioService;
    private final FollowService followService;
    private final PerfilService perfilService;
    private final PublicacionService publicacionService;
    
    public FeedService() {
        usuarioService = new UsuarioService();
        followService = new FollowService();
        perfilService = new PerfilService();
        publicacionService = new PublicacionService();
    }
    
    public ArrayList<Publicacion> GenerarTimeline(String usuario) {
        ArrayList<Publicacion> timeline = new ArrayList<>();
        
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return timeline;
        }
        
        ArrayList<String> usuariostimeline = ObtenerUsuariosDelTimeline(usuario);
        
        AgregarPublicacionesVisibles(usuario, usuariostimeline, timeline);
        OrdenarPorMasRecientes(timeline);
        
        return timeline;
    }
    
    public ArrayList<Publicacion> GenerarTimelineLimitado(String usuario, int limite) {
        ArrayList<Publicacion> timelinecompleto = GenerarTimeline(usuario);
        ArrayList<Publicacion> timelinelimitado = new ArrayList<>();
        
        if (limite <= 0) {
            return timelinelimitado;
        }
        
        for (int i = 0; i < timelinecompleto.size() && i < limite; i++) {
            timelinelimitado.add(timelinecompleto.get(i));
        }
        
        return timelinelimitado;
    }
    
    public ArrayList<String> ObtenerUsuariosDelTimeline(String usuario) {
        ArrayList<String> usuarios = new ArrayList<>();
        
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return usuarios;
        }
        
        //Siempre se agrega a si mismo
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
        
        ArrayList<String> following = followService.ListarFollowersDe(usuario);
        
        for (String seguido : following) {
            if (!usuarios.contains(seguido)) {
                usuarios.add(seguido);
            }
        }
        
        return usuarios;
    }
    
    private void AgregarPublicacionesVisibles(String viewer, ArrayList<String> usuarios, ArrayList<Publicacion> timeline) {
        for (String owner : usuarios) {
            Usuario usuarioowner = usuarioService.BuscarUsuario(owner);
            
            if (usuarioowner == null || !usuarioowner.isActivo()) {
                continue;
            }
            
            if (!perfilService.PuedeVerPublicaciones(viewer, owner)) {
                continue;
            }
            
            ArrayList<Publicacion> publicaciones = publicacionService.ListarPublicacionesDe(owner);
            
            for (Publicacion post : publicaciones) {
                timeline.add(post);
            }
        }
    }
    
    public void OrdenarPorMasRecientes(ArrayList<Publicacion> publicaciones) {
        OrdenarRecursivo(publicaciones, publicaciones.size());
    }
    
    private void OrdenarRecursivo(ArrayList<Publicacion> publicaciones, int numero) {
        if (numero <= 1) {
            return;
        }
        
        for (int i = 0; i < numero - 1; i++) {
            if (publicaciones.get(i).getID() < publicaciones.get(i + 1).getID()) {
                Publicacion aux = publicaciones.get(i);
                publicaciones.set(i, publicaciones.get(i + 1));
                publicaciones.set(i + 1, aux);
            }
        }
        
        OrdenarRecursivo(publicaciones, numero - 1);
    }
}
 