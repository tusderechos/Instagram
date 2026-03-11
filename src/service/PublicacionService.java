/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.raf.PostsRAF;
import modelo.Publicacion;
import modelo.Usuario;
import enums.TipoMedia;
import util.FechaUtil;
import util.TextoUtil;

import java.util.ArrayList;

public class PublicacionService {
    
    private final UsuarioService usuarioService;
    
    public PublicacionService() {
        usuarioService = new UsuarioService();
    }
    
    public boolean CrearPublicacion(String autor, String contenido, String rutaimagen, TipoMedia tipomedia) {
        Usuario usuarioautor = usuarioService.BuscarUsuario(autor);
        
        if (usuarioautor == null || !usuarioautor.isActivo()) {
            return false;
        }
        
        if (contenido == null) {
            contenido = "";
        }
        
        if (contenido.length() > 220) {
            return false;
        }
        
        if (rutaimagen == null) {
            rutaimagen = "";
        }
        
        if (tipomedia == null) {
            tipomedia = TipoMedia.NINGUNO;
        }
        
        ArrayList<String> hashtags = TextoUtil.ExtraerHashtags(contenido);
        ArrayList<String> menciones = TextoUtil.ExtraerMenciones(contenido);
        
        Publicacion post = new Publicacion(System.currentTimeMillis(), autor, FechaUtil.hoy(), FechaUtil.ahora(), contenido, hashtags, menciones, rutaimagen, tipomedia);
        PostsRAF postsRAF = new PostsRAF(autor);
        
        return postsRAF.Agregar(post);
    }
    
    public ArrayList<Publicacion> ListarPublicacionesDe(String usuario) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return new ArrayList<>();
        }
        
        PostsRAF postsRAF = new PostsRAF(usuario);
        return postsRAF.ListarTodas();
    }
    
    public Publicacion BuscarPublicacion(String usuario, long IDpost) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return null;
        }
        
        PostsRAF postsRAF = new PostsRAF(usuario);
        return postsRAF.BuscarPorID(IDpost);
    }
    
    public boolean EliminarPublicacion(String usuario, long IDpost) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return false;
        }
        
        PostsRAF postsRAF = new PostsRAF(usuario);
        return postsRAF.Eliminar(IDpost);
    }
    
    public int ContarPublicacionesDe(String usuario) {
        Usuario user = usuarioService.BuscarUsuario(usuario);
        
        if (user == null || !user.isActivo()) {
            return 0;
        }
        
        PostsRAF postsRAF = new PostsRAF(usuario);
        return postsRAF.Contar();
    }
    
    public ArrayList<Publicacion> BuscarPorHashtag(String hashtag, ArrayList<String> usuarios) {
        ArrayList<Publicacion> resultado = new ArrayList<>();
        
        if (hashtag == null || hashtag.isBlank()) {
            return resultado;
        }
        
        for (String usuario : usuarios) {
            ArrayList<Publicacion> publicaciones = ListarPublicacionesDe(usuario);
            
            for (Publicacion post : publicaciones) {
                if (post.getHashtags().contains(hashtag)) {
                    resultado.add(post);
                }
            }
        }
        
        return resultado;
    }
    
    public ArrayList<Publicacion> BuscarPorMencion(String mencion, ArrayList<String> usuarios) {
        ArrayList<Publicacion> resultado = new ArrayList<>();
        
        if (mencion == null || mencion.isBlank()) {
            return resultado;
        }
        
        for (String usuario : usuarios) {
            ArrayList<Publicacion> publicaciones = ListarPublicacionesDe(usuario);
            
            for (Publicacion post : publicaciones) {
                if (post.getMenciones().contains(mencion)) {
                    resultado.add(post);
                }
            }
        }
        
        return resultado;
    }
}
