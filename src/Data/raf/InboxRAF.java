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
import modelo.Mensaje;
import enums.TipoMensaje;

import java.io.IOException;
import java.util.ArrayList;

public class InboxRAF extends BaseRAF {
    
    private static final int USUARIO_LEN = 20;
    private static final int FECHA_LEN = 10;
    private static final int HORA_LEN = 8;
    private static final int CONTENIDO_LEN = 300;
    
    public InboxRAF(String owner) {
        super(Paths.getFileInbox(owner));
    }
    
    public boolean Agregar(Mensaje mensaje) {
        try {
            raf.seek(raf.length());
            EscribirMensaje(mensaje);
            return true;
        } catch (IOException e) {
            System.out.println("Error agregando mensaje");
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Mensaje> ListarTodos() {
        ArrayList<Mensaje> mensajes = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Mensaje mensaje = LeerMensaje();
                
                if (mensaje.isActivo()) {
                    mensajes.add(mensaje);
                }
            }
        } catch (IOException e) {
            System.out.println("Erorr listando mensajes");
            e.printStackTrace();
        }
        
        return mensajes;
    }
    
    public ArrayList<Mensaje> ObtenerConversacion(String otrouser) {
        ArrayList<Mensaje> conversacion = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Mensaje mensaje = LeerMensaje();
                
                if (!mensaje.isActivo()) {
                    continue;
                }
                
                if (mensaje.getEmisor().equalsIgnoreCase(otrouser) || mensaje.getReceptor().equalsIgnoreCase(otrouser)) {
                    conversacion.add(mensaje);
                }
            }
        } catch (IOException e) {
            System.out.println("Erorr obteniendo conversacion");
            e.printStackTrace();
        }
        
        return conversacion;
    }
    
    public ArrayList<String> ListarConversaciones() {
        ArrayList<String> conversaciones = new ArrayList<>();
        ArrayList<Mensaje> mensajes = ListarTodos();
        
        for (Mensaje mensaje : mensajes) {
            String otrouser = mensaje.getReceptor();
            
            if (!mensaje.getReceptor().equals("") && !mensaje.getEmisor().equalsIgnoreCase(mensaje.getReceptor())) {
                //owner no esta guardado explicitamente aqui, asi que este metodo se usa sobre el inbox del dueno
                //y el otro usuario sera quien no sea el dueno en el momento que lo filtre
            }
            
            if (!conversaciones.contains(mensaje.getEmisor())) {
                conversaciones.add(mensaje.getEmisor());
            }
            
            if (!conversaciones.contains(mensaje.getReceptor())) {
                conversaciones.add(mensaje.getReceptor());
            }
        }
        
        return conversaciones;
    }
    
    public int ContarNoLeidosRecibidorDe(String otrouser, String owner) {
        int contador = 0;
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Mensaje mensaje = LeerMensaje();
                
                if (!mensaje.isActivo()) {
                    continue;
                }
                
                if (mensaje.getEmisor().equalsIgnoreCase(otrouser) && mensaje.getReceptor().equalsIgnoreCase(owner) && !mensaje.isLeido()) {
                    contador++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erorr contando no leidos");
            e.printStackTrace();
        }
        
        return contador;
    }
    
    public int ContarNoLeidos(String owner) {
        int contador = 0;
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Mensaje mensaje = LeerMensaje();
                
                if (!mensaje.isActivo()) {
                    continue;
                }
                
                if (mensaje.getReceptor().equalsIgnoreCase(owner) && !mensaje.isLeido()) {
                    contador++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error contando mensajes no leidos");
            e.printStackTrace();
        }
        
        return contador;
    }
    
    public boolean MarcarLeidos(String otrouser, String owner) {
        boolean cambio = false;
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Mensaje mensaje = LeerMensaje();
                
                if (!mensaje.isActivo()) {
                    continue;
                }
                
                if (mensaje.getEmisor().equalsIgnoreCase(otrouser) && mensaje.getReceptor().equalsIgnoreCase(owner) && !mensaje.isLeido()) {
                    mensaje.setLeido(true);
                    
                    raf.seek(posicion);
                    EscribirMensaje(mensaje);
                    cambio = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error marcando mensajes como leidos");
            e.printStackTrace();
        }
        
        return cambio;
    }
    
    public boolean EliminarConversacion(String owner, String otrouser) {
        boolean cambio = false;
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Mensaje mensaje = LeerMensaje();

                if (!mensaje.isActivo()) {
                    continue;
                }

                boolean pertenececonversacion = (mensaje.getEmisor().equalsIgnoreCase(owner) && mensaje.getReceptor().equalsIgnoreCase(otrouser)) || (mensaje.getEmisor().equalsIgnoreCase(otrouser) && mensaje.getReceptor().equalsIgnoreCase(owner));

                if (pertenececonversacion) {
                    mensaje.setActivo(false);

                    raf.seek(posicion);
                    EscribirMensaje(mensaje);
                    cambio = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error eliminando conversacion");
            e.printStackTrace();
        }
        
        return cambio;
    }
    
    private void EscribirMensaje(Mensaje mensaje) throws IOException {
        RAFUtil.EscribirStringFijo(raf, mensaje.getEmisor(), USUARIO_LEN);
        RAFUtil.EscribirStringFijo(raf, mensaje.getReceptor(), USUARIO_LEN);
        RAFUtil.EscribirStringFijo(raf, mensaje.getFecha(), FECHA_LEN);
        RAFUtil.EscribirStringFijo(raf, mensaje.getHora(), HORA_LEN);
        RAFUtil.EscribirStringFijo(raf, mensaje.getContenido(), CONTENIDO_LEN);
        raf.writeInt(mensaje.getTipoMensaje().ordinal());
        raf.writeBoolean(mensaje.isLeido());
        raf.writeBoolean(mensaje.isActivo());
    }
    
    private Mensaje LeerMensaje() throws IOException {
        Mensaje mensaje = new Mensaje();
        
        mensaje.setEmisor(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        mensaje.setReceptor(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        mensaje.setFecha(RAFUtil.LeerStringFijo(raf, FECHA_LEN));
        mensaje.setHora(RAFUtil.LeerStringFijo(raf, HORA_LEN));
        mensaje.setContenido(RAFUtil.LeerStringFijo(raf, CONTENIDO_LEN));
        mensaje.setTipoMensaje(TipoMensaje.values()[raf.readInt()]);
        mensaje.setLeido(raf.readBoolean());
        mensaje.setActivo(raf.readBoolean());
        
        return mensaje;
    }
}
