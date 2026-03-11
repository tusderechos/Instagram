/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */

import enums.TipoMedia;

import java.util.ArrayList;

public class Publicacion extends RegistroBase {
    
    private long ID;
    private String Autor;
    private String Fecha;
    private String Hora;
    private String Contenido;
    private ArrayList<String> Hashtags;
    private ArrayList<String> Menciones;
    private String RutaImagen;
    private TipoMedia tipoMedia;
    
    public Publicacion() {
        super();
        Hashtags = new ArrayList<>();
        Menciones = new ArrayList<>();
        tipoMedia = TipoMedia.NINGUNO;
        RutaImagen = "";
    }

    public Publicacion(long ID, String Autor, String Fecha, String Hora, String Contenido, ArrayList<String> Hashtags, ArrayList<String> Menciones, String RutaImagen, TipoMedia tipoMedia) {
        super();
        this.ID = ID;
        this.Autor = Autor;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Contenido = Contenido;
        this.Hashtags = Hashtags;
        this.Menciones = Menciones;
        this.RutaImagen = RutaImagen;
        this.tipoMedia = tipoMedia;
    }
    
    @Override
    public String getClavePrimaria() {
        return String.valueOf(ID);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }

    public ArrayList<String> getHashtags() {
        return Hashtags;
    }

    public void setHashtags(ArrayList<String> Hashtags) {
        this.Hashtags = Hashtags;
    }

    public ArrayList<String> getMenciones() {
        return Menciones;
    }

    public void setMenciones(ArrayList<String> Menciones) {
        this.Menciones = Menciones;
    }

    public String getRutaImagen() {
        return RutaImagen;
    }

    public void setRutaImagen(String RutaImagen) {
        this.RutaImagen = RutaImagen;
    }

    public TipoMedia getTipoMedia() {
        return tipoMedia;
    }

    public void setTipoMedia(TipoMedia tipoMedia) {
        this.tipoMedia = tipoMedia;
    }
    
    @Override
    public String toString() {
        return "Publicacion{" + "ID=" + ID + ", Autor='" + Autor + '\'' + ", Fecha='" + Fecha + '\'' + ", Hora='" + Hora + '\'' + ", Contenido='" + Contenido + '\'' + ", Hashtags=" + Hashtags + ", Menciones=" + Menciones + ", RutaImagen='" + RutaImagen + '\'' + ", TipoMedia=" + tipoMedia + ", Activo=" + Activo + '}';
    }
}
