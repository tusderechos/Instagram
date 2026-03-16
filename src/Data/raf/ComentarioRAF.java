/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.raf;

/**
 *
 * @author USUARIO
 */

import modelo.Comentario;

import java.io.RandomAccessFile;
import java.io.File;
import java.util.ArrayList;

public class ComentarioRAF {
    
    private RandomAccessFile raf;
    
    public ComentarioRAF(String autorpost) {
        try {
            File folder = new File("INSTA_RAIZ/" + autorpost);
            folder.mkdirs();
            
            raf = new RandomAccessFile("INSTA_RAIZ/" + autorpost + "/comentarios.ins", "rw");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Agregar(Comentario comentario) {
        try {
            raf.seek(raf.length());
            
            raf.writeLong(comentario.getPostID());
            raf.writeUTF(comentario.getUsuario());
            raf.writeUTF(comentario.getTexto());
            raf.writeUTF(comentario.getFecha());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Comentario> Listar(long postID, String autor) {
        ArrayList<Comentario> lista = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while(raf.getFilePointer() < raf.length()) {
                long id = raf.readLong();
                String user = raf.readUTF();
                String txt = raf.readUTF();
                String fecha = raf.readUTF();
                
                if (id == postID) {
                    lista.add(new Comentario(id, autor, user, txt, fecha));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
}
