/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */

import enums.EstadoSolicitud;

public class Solicitud extends RegistroBase {
    
    private String Emisor;
    private String Receptor;
    private String Fecha;
    private String Hora;
    private EstadoSolicitud Estado;
    
    public Solicitud() {
        super();
        Estado = EstadoSolicitud.PENDIENTE;
    }
    
    public Solicitud(String Emisor, String Receptor, String Fecha, String Hora) {
        super();
        this.Emisor = Emisor;
        this.Receptor = Receptor;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Estado = EstadoSolicitud.PENDIENTE;
    }
    
    @Override
    public String getClavePrimaria() {
        return Emisor + "->" + Receptor;
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

    public EstadoSolicitud getEstado() {
        return Estado;
    }

    public void setEstado(EstadoSolicitud Estado) {
        this.Estado = Estado;
    }
    
    @Override
    public String toString() {
        return "Solicitud{" + "Emisor='" + Emisor + '\'' + ", Receptor='" + Receptor + '\'' + ", Fecha='" + Fecha + '\'' + ", Hora='" + Hora + '\'' + ", Estado=" + Estado + ", Activo=" + Activo + '}' ;
    }
}
