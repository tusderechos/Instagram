/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author USUARIO
 */

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import interfaces.MensajeChatListener;

public class SocketClient {
    
    private final String Host;
    private final int Puerto;
    private final String Usuario;
    
    private Socket socket;
    private ObjectInputStream Entrada;
    private ObjectOutputStream Salida;
    
    private boolean Conectado;
    private MensajeChatListener Listener;
    
    public SocketClient(String Host, int Puerto, String Usuario) {
        this.Host = Host;
        this.Puerto = Puerto;
        this.Usuario = Usuario;
        Conectado = false;
    }
    
    public boolean Conectar() {
        try {
            socket = new Socket(Host, Puerto);
            
            Salida = new ObjectOutputStream(socket.getOutputStream());
            Salida.flush();
            
            Entrada = new ObjectInputStream(socket.getInputStream());
            
            Salida.writeUTF(Usuario);
            Salida.flush();
            
            Conectado = true;
            NotificarEstado(true);
            
            IniciarEscucha();
            
            return true;
            
        } catch (IOException e) {
            Conectado = false;
            NotificarEstado(false);
            System.out.println("No se pudo conectar al servidor: " + e.getMessage());
            return false;
        }
    }
    
    private void IniciarEscucha() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                EscucharMensajes();
            }
        });
        
        hilo.start();
    }
    
    private void EscucharMensajes() {
        try {
            while (Conectado) {
                Object objeto = Entrada.readObject();
                
                if (objeto instanceof PaqueteMensaje) {
                    PaqueteMensaje paquete = (PaqueteMensaje) objeto;
                    
                    if (Listener != null) {
                        Listener.AlRecibirMensaje(paquete);
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("Servidor cerro la conexion");
        } catch (IOException e) {
            System.out.println("Error escuchando mensajes: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no reconocida recibida");
        } finally {
            Desconectar();
        }
    }
    
    public synchronized boolean EnviarMensaje(PaqueteMensaje paquete) {
        if (!Conectado || paquete == null) {
            return false;
        }
        
        try {
            Salida.writeObject(paquete);
            Salida.flush();
            return true;
            
        } catch (IOException e) {
            System.out.println("Error enviando mensaje al servidor: " + e.getMessage());
            return false;
        }
    }
    
    public void Desconectar() {
        Conectado = false;
        
        try {
            if (Entrada != null) {
                Entrada.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando entrada cliente: " + e.getMessage());
        }
        try {
            if (Salida != null) {
                Salida.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando salida cliente: " + e.getMessage());
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando socketcliente: " + e.getMessage());
        }
        
        NotificarEstado(false);
    }
    
    private void NotificarEstado(boolean estado) {
        if (Listener != null) {
            Listener.AlCambiarEstadoConexion(estado);
        }
    }
    
    public void setListener(MensajeChatListener Listener) {
        this.Listener = Listener;
    }
    
    public boolean isConectado() {
        return Conectado;
    }
    
    public String getUsuario() {
        return Usuario;
    }
}
