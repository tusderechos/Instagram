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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorSocket {
    
    private ServerSocket serverSocket;
    private boolean Corriendo;
    private final ArrayList<ConexionCliente> Clientes;
    private final int Puerto;
    
    public ServidorSocket(int Puerto) {
        this.Puerto = Puerto;
        Clientes = new ArrayList<>();
        Corriendo = false;
    }
    
    public void Iniciar() {
        try {
            serverSocket = new ServerSocket(Puerto);
            Corriendo = true;
            System.out.println("Servidor de chat iniciado en puerto " + Puerto);
            
            while (Corriendo) {
                Socket socket = serverSocket.accept();
                ConexionCliente cliente = new ConexionCliente(socket, this);
                Thread hilo = new Thread(cliente);
                hilo.start();
            }
            
        } catch (IOException e) {
            if (Corriendo) {
                System.out.println("Error al iniciar servidor: " + e.getMessage());
            }
        } finally {
            Detener();
        }
    }
    
    public synchronized void RegistrarCliente(ConexionCliente nuevocliente) {
        if (nuevocliente == null || nuevocliente.getUsuario() == null) {
            return;
        }
        
        ConexionCliente existente = BuscarCliente(nuevocliente.getUsuario());
        
        if (existente != null) {
            Clientes.remove(existente);
            existente.Cerrar();
        }
        
        Clientes.add(nuevocliente);
        System.out.println("Cliente conectado: " + nuevocliente.getUsuario());
    }
    
    public synchronized void RemoverCliente(ConexionCliente cliente) {
        if (cliente == null) {
            return;
        }
        
        Clientes.remove(cliente);
        
        if (cliente.getUsuario() != null) {
            System.out.println("Cliente removido: " + cliente.getUsuario());
        }
    }
    
    public synchronized void ProcesarMensaje(PaqueteMensaje paquete, ConexionCliente origen) {
        if (paquete == null) {
            return;
        }
        
        ConexionCliente receptor = BuscarCliente(paquete.getReceptor());
        
        if (receptor != null && receptor.isActivo()) {
            receptor.EnviarMensaje(paquete);
        }
    }
    
    public synchronized ConexionCliente BuscarCliente(String usuario) {
        for (ConexionCliente cliente : Clientes) {
            if (cliente.getUsuario() != null && cliente.getUsuario().equalsIgnoreCase(usuario)) {
                return cliente;
            }
        }
        
        return null;
    }
    
    public synchronized ArrayList<ConexionCliente> ListarClientesConectados() {
        return new ArrayList<>(Clientes);
    }
    
    public void Detener() {
        Corriendo = false;
        
        for (int i = Clientes.size() - 1; i >= 0; i--) {
            Clientes.get(i).Cerrar();
        }
        
        Clientes.clear();
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando servidor: " + e.getMessage());
        }
    }
    
    public boolean isCorriendo() {
        return Corriendo;
    }
    
    public int getPuerto() {
        return Puerto;
    }
}
