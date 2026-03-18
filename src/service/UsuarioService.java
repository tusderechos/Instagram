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
import enums.EstadoCuenta;
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
        Usuario user = BuscarUsuario(usuario);
        
        if (user == null) {
            return false;
        }
        
        return user.getContrasena().equals(contrasena) && user.getEstadoCuenta() == EstadoCuenta.ACTIVA && user.isActivo();
    }
    
    public Usuario BuscarUsuario(String usuario) {
        Usuario user = usersRAF.Buscar(usuario);
        
        if (user == null) {
            return null;
        }
        
        if (!Paths.UsuarioTieneEstructuraBasica(user.getUsuario())) {
            return null;
        }
        
        if (!user.isActivo()) {
            return null;
        }
        
        if (user.getEstadoCuenta() != EstadoCuenta.ACTIVA) {
            return null;
        }
        
        return user;
    }
    
    public ArrayList<Usuario> ListarUsuarios() {
        ArrayList<Usuario> usuarios = usersRAF.Listar();
        ArrayList<Usuario> validos = new ArrayList<>();
        
        for (Usuario usuario : usuarios) {
            if (usuario == null) {
                continue;
            }
            
            if (!usuario.isActivo()) {
                continue;
            }
            
            if (usuario.getEstadoCuenta() != EstadoCuenta.ACTIVA) {
                continue;
            }
            
            if (!Paths.UsuarioTieneEstructuraBasica(usuario.getUsuario())) {
                continue;
            }
            
            validos.add(usuario);
        }
        
        return validos;
    }
    
    public boolean DesactivarCuenta(String usuario) {
        return usersRAF.CambiarEstadoCuenta(usuario, EstadoCuenta.DESACTIVADA);
    }
    
    public boolean ActivarCuenta(String usuario) {
        return usersRAF.CambiarEstadoCuenta(usuario, EstadoCuenta.ACTIVA);
    }
    
    public boolean UsuarioExisteFisicamente(String usuario) {
        return Paths.UsuarioTieneEstructuraBasica(usuario);
    }
    
    public boolean UsuarioEsValido(String usuario) {
        Usuario user = usersRAF.Buscar(usuario);
        
        if (user == null) {
            return false;
        }
        
        if (!user.isActivo()) {
            return false;
        }
        
        if (user.getEstadoCuenta() != EstadoCuenta.ACTIVA) {
            return false;
        }
        
        return Paths.UsuarioTieneEstructuraBasica(usuario);
    }
    
    public boolean ActualizarPerfil(String usuarioactual, String nuevonombre, String nuevacontrasena, int nuevaedad, TipoCuenta nuevotipocuenta, String nuevafotoperfil) {
        if (usuarioactual == null || usuarioactual.isBlank()) {
            return false;
        }
        
        Usuario usuario = usersRAF.Buscar(usuarioactual);
        
        if (usuario == null) {
            return false;
        }
        
        if (Validaciones.TextoVacio(nuevonombre) || Validaciones.TextoVacio(nuevacontrasena)) {
            return false;
        }
        
        if (!Validaciones.EdadValida(nuevaedad)) {
            return false;
        }
        
        usuario.setNombreCompleto(nuevonombre.trim());
        usuario.setContrasena(nuevacontrasena);
        usuario.setEdad(nuevaedad);
        usuario.setTipoCuenta(nuevotipocuenta);
        
        if (nuevafotoperfil != null) {
            usuario.setFotoPerfil(nuevafotoperfil);
        }
        
        return usersRAF.Actualizar(usuario);
    }
    
    public boolean ReactivarCuenta(String usuario, String contrasena) {
        if (usuario == null || usuario.isBlank() || contrasena == null || contrasena.isBlank()) {
            return false;
        }
        
        Usuario user = usersRAF.Buscar(usuario);
        
        if (user == null) {
            return false;
        }
        
        if (!user.getContrasena().equals(contrasena)) {
            return false;
        }
        
        return usersRAF.CambiarEstadoCuenta(usuario, EstadoCuenta.ACTIVA);
    }
    
    public UsersRAF getUsersRAF() {
        return usersRAF;
    }
}
