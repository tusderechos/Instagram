/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */

public class Like {
    private long PostID;
    private String AutorPost;
    private String UsuarioLike;
    
    public Like(long PostID, String AutorPost, String UsuarioLike) {
        this.PostID = PostID;
        this.AutorPost = AutorPost;
        this.UsuarioLike = UsuarioLike;
    }

    public long getPostID() {
        return PostID;
    }

    public String getAutorPost() {
        return AutorPost;
    }

    public String getUsuarioLike() {
        return UsuarioLike;
    }
}
