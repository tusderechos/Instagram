/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author USUARIO
 */
public final class Validaciones {
    
    private Validaciones() {
    }
    
    public static boolean TextoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
    public static boolean EdadValida(int edad) {
        return edad > 0 && edad <= 120;
    }
    
    public static boolean UsernameValido(String usuario) {
        return usuario != null && usuario.matches("[a-zA-Z0-9._]{3,20}");
    }
}
