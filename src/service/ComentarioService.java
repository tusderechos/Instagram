/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.raf.ComentarioRAF;
import modelo.Comentario;
import util.FechaUtil;

import java.util.ArrayList;

public class ComentarioService {
    
    public void AgregarComentario(long postID, String autorpost, String usuario, String texto) {
        ComentarioRAF raf = new ComentarioRAF(autorpost);
        Comentario comentario = new Comentario(postID, autorpost, usuario, texto, FechaUtil.ahora());
        
        raf.Agregar(comentario);
    }
    
    public ArrayList<Comentario> ListarComentarios(long postID, String autorpost) {
        ComentarioRAF raf = new ComentarioRAF(autorpost);
        return raf.Listar(postID, autorpost);
    }
}
