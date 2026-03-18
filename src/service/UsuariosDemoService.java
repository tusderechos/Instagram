/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import enums.TipoCuenta;

public class UsuariosDemoService {
    
    private final UsuarioService usuarioService;
    
    public UsuariosDemoService() {
        usuarioService = new UsuarioService();
    }
    
    public void InicializarUsuariosDemo() {
        CrearSiNoExiste("Nathan Demo", "M", "nathan_demo", "1234", 20, TipoCuenta.PRIVADA, "");
        CrearSiNoExiste("Marcela Demo", "M", "marcela_demo", "1234", 22, TipoCuenta.PUBLICA, "");
        CrearSiNoExiste("Carlos Demo", "M", "carlos_demo", "1234", 26, TipoCuenta.PRIVADA, "");
    }
    
    private void CrearSiNoExiste(String nombre, String genero, String usuario, String contrasena, int edad, TipoCuenta tipocuenta, String fotoperfil) {
        if (usuarioService.BuscarUsuario(usuario) != null) {
            return;
        }
        
        usuarioService.RegistrarUsuario(nombre, genero, usuario, contrasena, edad, tipocuenta, fotoperfil);
    }
}
