/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author USUARIO
 */
public class ServidorChatLauncher {
    
    private static ServidorSocket Servidor;
    private static boolean Iniciado = false;
    
    private ServidorChatLauncher() {
        
    }
    
    public static synchronized void IniciarServidorSiNoExiste(int puerto) {
        if (Iniciado) {
            return;
        }
        
        Servidor = new ServidorSocket(puerto);
        
        Thread hiloservidor = new Thread(new Runnable() {
            @Override
            public void run() {
                Servidor.Iniciar();
            }
        });
        
        hiloservidor.setDaemon(true);
        hiloservidor.start();
        
        Iniciado = true;
        System.out.println("Servidor de chat lanzado automaticamente");
    }
    
    public static synchronized void DetenerServidor() {
        if (Servidor != null) {
            Servidor.Detener();
            Servidor = null;
        }
        
        Iniciado = false;
    }
    
    public static synchronized boolean EstaIniciado() {
        return Iniciado;
    }
}
