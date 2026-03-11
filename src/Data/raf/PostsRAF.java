/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.raf;

/**
 *
 * @author USUARIO
 */

import Data.Paths.Paths;
import modelo.Publicacion;
import enums.TipoMedia;
import util.TextoUtil;

import java.io.IOException;
import java.util.ArrayList;

public class PostsRAF extends BaseRAF {
    
    private static final int AUTOR_LEN = 20;
    private static final int FECHA_LEN = 10;
    private static final int HORA_LEN = 8;
    private static final int CONTENIDO_LEN = 220;
    private static final int HASHTAGS_LEN = 150;
    private static final int MENCIONES_LEN = 150;
    private static final int RUTA_LEN = 150;
    
    public PostsRAF(String usuarioowner) {
        super(Paths.getFilePosts(usuarioowner));
    }
    
    public boolean Agregar(Publicacion post) {
        try {
            raf.seek(raf.length());
            EscribirPost(post);
            return true;
        } catch (IOException e) {
            System.out.println("Error agregando publicacion");
            e.printStackTrace();
            return false;
        }
    }
    
    public Publicacion BuscarPorID(long ID) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Publicacion post = LeerPost();
                
                if (post.getID() == ID && post.isActivo()) {
                    return post;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando publicacion");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public long BuscarPosicionPorID(long ID) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Publicacion post = LeerPost();
                
                if (post.getID() == ID && post.isActivo()) {
                    return posicion;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando posicion de publicacion");
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public ArrayList<Publicacion> ListarTodas() {
        ArrayList<Publicacion> publicaciones = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Publicacion post = LeerPost();
                
                if (post.isActivo()) {
                    publicaciones.add(post);
                }
            }
        } catch (IOException e) {
            System.out.println("Error listando publicaciones");
            e.printStackTrace();
        }
        
        return publicaciones;
    }
    
    public int Contar() {
        return ListarTodas().size();
    }
    
    public boolean Eliminar(long ID) {
        try {
            long posicion = BuscarPosicionPorID(ID);
            
            if (posicion == -1) {
                return false;
            }
            
            raf.seek(posicion);
            Publicacion post = LeerPost();
            post.setActivo(false);
            
            raf.seek(posicion);
            EscribirPost(post);
            return true;
        } catch (IOException e) {
            System.out.println("Error eliminado publicacion");
            e.printStackTrace();
            return false;
        }
    }
    
    private void EscribirPost(Publicacion post) throws IOException {
        raf.writeLong(post.getID());
        RAFUtil.EscribirStringFijo(raf, post.getAutor(), AUTOR_LEN);
        RAFUtil.EscribirStringFijo(raf, post.getFecha(), FECHA_LEN);
        RAFUtil.EscribirStringFijo(raf, post.getHora(), HORA_LEN);
        RAFUtil.EscribirStringFijo(raf, post.getContenido(), CONTENIDO_LEN);
        RAFUtil.EscribirStringFijo(raf, TextoUtil.ArrayListToTexto(post.getHashtags()), HASHTAGS_LEN);
        RAFUtil.EscribirStringFijo(raf, TextoUtil.ArrayListToTexto(post.getMenciones()), MENCIONES_LEN);
        RAFUtil.EscribirStringFijo(raf, post.getRutaImagen(), RUTA_LEN);
        raf.writeInt(post.getTipoMedia().ordinal());
        raf.writeBoolean(post.isActivo());
    }
    
    private Publicacion LeerPost() throws IOException {
        Publicacion post = new Publicacion();
        
        post.setID(raf.readLong());
        post.setAutor(RAFUtil.LeerStringFijo(raf, AUTOR_LEN));
        post.setFecha(RAFUtil.LeerStringFijo(raf, FECHA_LEN));
        post.setHora(RAFUtil.LeerStringFijo(raf, HORA_LEN));
        post.setContenido(RAFUtil.LeerStringFijo(raf, CONTENIDO_LEN));
        post.setHashtags(TextoUtil.TextoToArrayList(RAFUtil.LeerStringFijo(raf, HASHTAGS_LEN)));
        post.setMenciones(TextoUtil.TextoToArrayList(RAFUtil.LeerStringFijo(raf, MENCIONES_LEN)));
        post.setRutaImagen(RAFUtil.LeerStringFijo(raf, RUTA_LEN));
        post.setTipoMedia(TipoMedia.values()[raf.readInt()]);
        post.setActivo(raf.readBoolean());
        
        return post;
    }
}
