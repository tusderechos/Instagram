/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.raf;

/**
 *
 * @author USUARIO
 */

import modelo.Like;
import java.io.RandomAccessFile;
import java.io.File;
import java.util.ArrayList;

public class LikeRAF {
    
    private RandomAccessFile raf;
    private String Ruta;
    
    public LikeRAF(String autorpost) {
        try {
            File folder = new File("INSTA_RAIZ/" + autorpost);
            folder.mkdirs();
            
            Ruta = "INSTA_RAIZ/" + autorpost + "/likes.ins";
            raf = new RandomAccessFile(Ruta, "rw");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Agregar(Like like) {
        try {
            raf.seek(raf.length());
            
            raf.writeLong(like.getPostID());
            raf.writeUTF(like.getUsuarioLike());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Like> Listar(long postID, String autor) {
        ArrayList<Like> lista = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long id = raf.readLong();
                String usuario = raf.readUTF();
                
                if (id == postID) {
                    lista.add(new Like(id, autor, usuario));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public boolean Existe(long postID, String usuario) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long id = raf.readLong();
                String user = raf.readUTF();
                
                if (id == postID && user.equals(usuario)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public void Quitar(long postID, String usuario) {
        try {
            ArrayList<Like> todos = Listar(postID, "");
            raf.setLength(0);
            
            for (Like like : todos) {
                if (!(like.getPostID() == postID && like.getUsuarioLike().equals(usuario))) {
                    raf.writeLong(like.getPostID());
                    raf.writeUTF(like.getUsuarioLike());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
