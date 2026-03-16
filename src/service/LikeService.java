/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.raf.LikeRAF;
import modelo.Like;

import java.util.ArrayList;

public class LikeService {
    
    public boolean ToogleLike(long postID, String autorpost, String usuarioactual) {
        LikeRAF raf = new LikeRAF(autorpost);
        
        if (raf.Existe(postID, usuarioactual)) {
            raf.Quitar(postID, usuarioactual);
            return false;
        } else {
            raf.Agregar(new Like(postID, autorpost, usuarioactual));
            return true;
        }
    }
    
    public int ContarLikes(long postID, String autorpost) {
        LikeRAF raf = new LikeRAF(autorpost);
        ArrayList<Like> lista = raf.Listar(postID, autorpost);
        
        return lista.size();
    }
    
    public boolean UsuarioDioLike(long postID, String autorpost, String usuarioactual) {
        LikeRAF raf = new LikeRAF(autorpost);
        return raf.Existe(postID, usuarioactual);
    }
}
