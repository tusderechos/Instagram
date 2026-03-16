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
import modelo.Solicitud;
import enums.EstadoSolicitud;

import java.io.IOException;
import java.util.ArrayList;

public class RequestsRAF extends BaseRAF {
    
    private static final int USUARIO_LEN = 20;
    private static final int FECHA_LEN = 10;
    private static final int HORA_LEN = 8;
    
    public RequestsRAF(String usuarioreceptor) {
        super(Paths.getFileRequests(usuarioreceptor));
    }
    
    public boolean Agregar(Solicitud solicitud) {
        try {
            if (ExisteSolicitudPendiente(solicitud.getEmisor(), solicitud.getReceptor())) {
                return false;
            }
            
            raf.seek(raf.length());
            EscribirSolicitud(solicitud);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error agregando solicitud");
            e.printStackTrace();
            return false;
        }
    }
    
    public Solicitud Buscar(String emisor, String receptor) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Solicitud solicitud = LeerSolicitud();
                
                if (solicitud.getEmisor().equalsIgnoreCase(emisor) && solicitud.getReceptor().equalsIgnoreCase(receptor) && solicitud.isActivo() && solicitud.getEstado() == EstadoSolicitud.PENDIENTE) {
                    return solicitud;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando solicitud");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public long BuscarPosicion(String emisor, String receptor) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Solicitud solicitud = LeerSolicitud();
                
                if (solicitud.getEmisor().equalsIgnoreCase(emisor) && solicitud.getReceptor().equalsIgnoreCase(receptor) && solicitud.isActivo() && solicitud.getEstado() == EstadoSolicitud.PENDIENTE) {
                    return posicion;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando posicion de solicitud");
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public ArrayList<Solicitud> ListarTodas() {
        ArrayList<Solicitud> solicitudes = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                solicitudes.add(LeerSolicitud());
            }
            
        } catch (IOException e) {
            System.out.println("Error listando solicitudes");
            e.printStackTrace();
        }
        
        return solicitudes;
    }
    
    public ArrayList<Solicitud> ListarPendientes() {
        ArrayList<Solicitud> pendientes = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Solicitud solicitud = LeerSolicitud();
                
                if (solicitud.isActivo() && solicitud.getEstado() == EstadoSolicitud.PENDIENTE) {
                    pendientes.add(solicitud);
                }
            }
        } catch (IOException e) {
            System.out.println("Error listando pendientes");
            e.printStackTrace();
        }
        
        return pendientes;
    }
    
    public boolean Actualizar(Solicitud solicitud) {
        try {
            long posicion = BuscarPosicion(solicitud.getEmisor(), solicitud.getReceptor());
            
            if (posicion == -1) {
                return false;
            }
            
            raf.seek(posicion);
            EscribirSolicitud(solicitud);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error actualizando solicitud");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean CambiarEstado(String emisor, String receptor, EstadoSolicitud nuevoestado) {
        Solicitud solicitud = Buscar(emisor, receptor);
        
        if (solicitud == null) {
            return false;
        }
        
        solicitud.setEstado(nuevoestado);
        
        if (nuevoestado != EstadoSolicitud.PENDIENTE) {
            solicitud.setActivo(false);
        }
        
        return Actualizar(solicitud);
    }
    
    public boolean ExisteSolicitudPendiente(String emisor, String receptor) {
//        Solicitud solicitud = Buscar(emisor, receptor);
//        
//        return solicitud != null && solicitud.getEstado() == EstadoSolicitud.PENDIENTE && solicitud.isActivo();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Solicitud solicitud = LeerSolicitud();
                
                if (solicitud.getEmisor().equalsIgnoreCase(emisor) && solicitud.getReceptor().equalsIgnoreCase(receptor) && solicitud.getEstado() == EstadoSolicitud.PENDIENTE && solicitud.isActivo()) {
                    return true;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private void EscribirSolicitud(Solicitud solicitud) throws IOException {
        RAFUtil.EscribirStringFijo(raf, solicitud.getEmisor(), USUARIO_LEN);
        RAFUtil.EscribirStringFijo(raf, solicitud.getReceptor(), USUARIO_LEN);
        RAFUtil.EscribirStringFijo(raf, solicitud.getFecha(), FECHA_LEN);
        RAFUtil.EscribirStringFijo(raf, solicitud.getHora(), HORA_LEN);
        raf.writeInt(solicitud.getEstado().ordinal());
        raf.writeBoolean(solicitud.isActivo());
    }
    
    private Solicitud LeerSolicitud() throws IOException {
        Solicitud solicitud = new Solicitud();
        
        solicitud.setEmisor(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        solicitud.setReceptor(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        solicitud.setFecha(RAFUtil.LeerStringFijo(raf, FECHA_LEN));
        solicitud.setHora(RAFUtil.LeerStringFijo(raf, HORA_LEN));
        solicitud.setEstado(EstadoSolicitud.values()[raf.readInt()]);
        solicitud.setActivo(raf.readBoolean());
        
        return solicitud;
    }
}
