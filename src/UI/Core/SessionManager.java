/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Core;

/**
 *
 * @author USUARIO
 */

import Red.ManejoConexionChat;

public class SessionManager {
    
    private String UsuarioActual;
    private ManejoConexionChat manejoConexionChat;
    private String MensajePendienteLogin;
    
    public SessionManager() {
        UsuarioActual = null;
        manejoConexionChat = null;
        MensajePendienteLogin = "";
    }

    public String getUsuarioActual() {
        return UsuarioActual;
    }

    public void setUsuarioActual(String UsuarioActual) {
        this.UsuarioActual = UsuarioActual;
    }

    public ManejoConexionChat getManejoConexionChat() {
        return manejoConexionChat;
    }

    public void setManejoConexionChat(ManejoConexionChat manejoConexionChat) {
        this.manejoConexionChat = manejoConexionChat;
    }
    
    public boolean haySesionActiva() {
        return UsuarioActual != null && !UsuarioActual.isBlank();
    }
    
    public void setMensajePendienteLogin(String mensaje) {
        MensajePendienteLogin = mensaje != null ? mensaje : "";
    }
    
    public String ConsumirMensajePendienteLogin() {
        String mensaje = MensajePendienteLogin;
        MensajePendienteLogin = "";
        return mensaje;
    }
    
    public void CerrarSesion() {
        if (manejoConexionChat != null) {
            manejoConexionChat.Desconectar();
            manejoConexionChat = null;
        }
        
        UsuarioActual = null;
    }
}
