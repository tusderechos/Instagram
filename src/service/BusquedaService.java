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

public class BusquedaService {
    
    private final UsuarioService usuarioService;
    private final PublicacionService publicacionService;
    
    public BusquedaService() {
        usuarioService = new UsuarioService();
        publicacionService = new PublicacionService();
    }
    
    public ArrayList<Usuario> BuscarUsuariosPorUsername(String texto) {
        ArrayList<Usuario> resultados = new ArrayList<>();
        
        if (texto == null || texto.isBlank()) {
            return resultados;
        }
        
        String filtro = texto.trim().toLowerCase();
        ArrayList<Usuario> usuarios = usuarioService.ListarUsuarios();
        
        for (Usuario usuario : usuarios) {
            if (usuario == null || !usuario.isActivo()) {
                continue;
            }
            
            if (usuario.getUsuario() != null && usuario.getUsuario().toLowerCase().contains(filtro)) {
                resultados.add(usuario);
            }
        }
        
        return resultados;
    }
    
    public ArrayList<Publicacion> BuscarPublicacionesPorHashtag(String hashtag) {
        ArrayList<Publicacion> resultados = new ArrayList<>();
        
        if (hashtag == null || hashtag.isBlank()) {
            return resultados;
        }
        
        String hashtaglimpio = hashtag.trim();
        
        if (!hashtaglimpio.startsWith("#")) {
            hashtaglimpio = "#" + hashtaglimpio;
        }
        
        ArrayList<Usuario> usuarios = usuarioService.ListarUsuarios();
        ArrayList<String> usernames = new ArrayList<>();
        
        for (Usuario usuario : usuarios) {
            if (usuario != null && usuario.isActivo()) {
                usernames.add(usuario.getUsuario());
            }
        }
        
        resultados = publicacionService.BuscarPorHashtag(hashtaglimpio, usernames);
        return resultados;
    }
}
