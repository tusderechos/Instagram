/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author USUARIO
 */

import Red.PaqueteMensaje;

public interface MensajeChatListener {
    void AlRecibirMensaje(PaqueteMensaje paquete);
    void AlCambiarEstadoConexion(boolean conectado);
}
