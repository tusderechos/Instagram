/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Core;

/**
 *
 * @author USUARIO
 */
public class SessionManager {
    
    private String UsuarioActual;
    
    public SessionManager() {
        UsuarioActual = null;
    }

    public String getUsuarioActual() {
        return UsuarioActual;
    }

    public void setUsuarioActual(String UsuarioActual) {
        this.UsuarioActual = UsuarioActual;
    }
    
    public boolean haySesionActiva() {
        return UsuarioActual != null && !UsuarioActual.isBlank();
    }
    
    public void CerrarSesion() {
        UsuarioActual = null;
    }
}
