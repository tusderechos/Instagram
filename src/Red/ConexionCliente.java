/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author USUARIO
 */

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexionCliente implements Runnable {
    
    private final Socket socket;
    private final ServidorSocket Servidor;
    
    private ObjectOutputStream Salida;
    private ObjectInputStream Entrada;
    private String Usuario;
    private boolean Activo;
    
    public ConexionCliente(Socket socket, ServidorSocket Servidor) {
        this.socket = socket;
        this.Servidor = Servidor;
        Activo = true;
    }
    
    @Override
    public void run() {
        try {
            Salida = new ObjectOutputStream(socket.getOutputStream());
            Salida.flush();
            
            Entrada = new ObjectInputStream(socket.getInputStream());
            
            Usuario = Entrada.readUTF();
            
            if (Usuario == null || Usuario.trim().isEmpty()) {
                EnviarRespuestaLogin("LOGIN_INVALIDO");
                Cerrar();
                return;
            }
            
            boolean registro = Servidor.RegistrarCliente(this);
            
            if (!registro) {
                EnviarRespuestaLogin("LOGIN_DUPLICADO");
                Cerrar();
                return;
            }
            
            EnviarRespuestaLogin("LOGIN_OK");
            
            while (Activo) {
                Object objeto = Entrada.readObject();
                
                if (objeto instanceof PaqueteMensaje) {
                    PaqueteMensaje paquete = (PaqueteMensaje) objeto;
                    Servidor.ProcesarMensaje(paquete, this);
                }
            }
            
        } catch (EOFException e) {
            System.out.println("Cliente desconectado: " + Usuario);
        } catch (IOException e) {
            System.out.println("Error de conexion con cliente: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Objeto desconocido recibido en socket");
        } finally {
            Servidor.RemoverCliente(this);
            Cerrar();
        }
    }
    
    private synchronized void EnviarRespuestaLogin(String respuesta) {
        try {
            if (Salida != null) {
                Salida.writeUTF(respuesta);
                Salida.flush();
            }
        } catch (IOException e) {
            System.out.println("Error enviando respuesta de login: " + e.getMessage());
        }
    }
    
    public synchronized boolean EnviarMensaje(PaqueteMensaje paquete) {
        try {
            if (Salida != null) {
                Salida.writeObject(paquete);
                Salida.flush();
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error enviando mensaje a " + Usuario + ": " + e.getMessage());
        }
        
        return false;        
    }
    
    public void Cerrar() {
        Activo = false;
        
        try {
            if (Entrada != null) {
                Entrada.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando entrada: " + e.getMessage());
        }
        try {
            if (Salida != null) {
                Salida.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando salida: " + e.getMessage());
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando socket: " + e.getMessage());
        }
    }
    
    public String getUsuario() {
        return Usuario;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public boolean isActivo() {
        return Activo;
    }
}
