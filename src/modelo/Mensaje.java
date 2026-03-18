    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */

import enums.TipoMensaje;

public class Mensaje extends RegistroBase {
    
    private String Emisor;
    private String Receptor;
    private String Fecha;
    private String Hora;
    private String Contenido;
    private TipoMensaje tipoMensaje;
    private boolean Leido;
    
    public Mensaje() {
        super();
        tipoMensaje = TipoMensaje.TEXTO;
        Leido = false;
    }

    public Mensaje(String Emisor, String Receptor, String Fecha, String Hora, String Contenido, TipoMensaje tipoMensaje) {
        this.Emisor = Emisor;
        this.Receptor = Receptor;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Contenido = Contenido;
        this.tipoMensaje = tipoMensaje;
        Leido = false;
    }
    
    @Override
    public String getClavePrimaria() {
        return Emisor + "->" + Receptor + "@" + Fecha + Hora;
    }

    public String getEmisor() {
        return Emisor;
    }

    public void setEmisor(String Emisor) {
        this.Emisor = Emisor;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String Receptor) {
        this.Receptor = Receptor;
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

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public boolean isLeido() {
        return Leido;
    }

    public void setLeido(boolean Leido) {
        this.Leido = Leido;
    }
    
    @Override
    public String toString() {
        return "Mensaje{" + "Emisor='" + Emisor + '\'' + ", Receptor='" + Receptor + '\'' + ", Fecha='" + Fecha + '\'' + ", Hora='" + Hora + '\'' + ", Contenido='" + Contenido + '\'' + ", TipoMensaje=" + tipoMensaje + ", Leido=" + Leido + ", Activo=" + Activo + "}";
    }
}
