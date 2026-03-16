/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */
public class Comentario {
    
    private long PostID;
    private String AutorPost;
    private String Usuario;
    private String Texto;
    private String Fecha;

    public Comentario(long PostID, String AutorPost, String Usuario, String Texto, String Fecha) {
        this.PostID = PostID;
        this.AutorPost = AutorPost;
        this.Usuario = Usuario;
        this.Texto = Texto;
        this.Fecha = Fecha;
    }

    public long getPostID() {
        return PostID;
    }

    public String getAutorPost() {
        return AutorPost;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getTexto() {
        return Texto;
    }

    public String getFecha() {
        return Fecha;
    }
}
