/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.Paths.Paths;
import Data.raf.UsersRAF;
import modelo.Usuario;
import enums.TipoCuenta;
import util.FechaUtil;
import util.Validaciones;

import java.util.ArrayList;

public class UsuarioService {
    
    private final UsersRAF usersRAF;
    
    public UsuarioService() {
        usersRAF = new UsersRAF();
    }
    
    public boolean RegistrarUsuario(String NombreCompleto, String Genero, String Usuario, String Contrasena, int Edad, TipoCuenta tipoCuenta, String FotoPerfil) {
        
        if (Validaciones.TextoVacio(NombreCompleto) || Validaciones.TextoVacio(Genero) || Validaciones.TextoVacio(Usuario) || Validaciones.TextoVacio(Contrasena)) {
            return false;
        }
        
        if (!Validaciones.UsernameValido(Usuario) || !Validaciones.EdadValida(Edad)) {
            return false;
        }
        
        if (usersRAF.ExisteUsuario(Usuario)) {
            return false;
        }
        
        Usuario user = new Usuario(NombreCompleto, Genero, Usuario, Contrasena, FechaUtil.hoy(), Edad, tipoCuenta, FotoPerfil);
        
        boolean agregado = usersRAF.Agregar(user);
        
        if (agregado) {
            Paths.CrearEstructuraUsuario(Usuario);
        }
        
        return agregado;
    }
    
    public boolean Login(String usuario, String contrasena) {
        return usersRAF.Login(usuario, contrasena);
    }
    
    public Usuario BuscarUsuario(String usuario) {
        return usersRAF.Buscar(usuario);
    }
    
    public ArrayList<Usuario> ListarUsuarios() {
        return usersRAF.Listar();
    }
    
    public UsersRAF getUsersRAF() {
        return usersRAF;
    }
}
