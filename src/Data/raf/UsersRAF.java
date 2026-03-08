/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.raf;

/**
 *
 * @author USUARIO
 */

import Data.Paths.Paths;
import interfaces.Autenticable;
import interfaces.RegistroArchivo;
import modelo.Usuario;
import enums.EstadoCuenta;
import enums.TipoCuenta;

import java.io.IOException;
import java.util.ArrayList;

public class UsersRAF extends BaseRAF implements RegistroArchivo<Usuario>, Autenticable {
    
    private static final int NOMBRE_LEN = 40;
    private static final int GENERO_LEN = 1;
    private static final int USUARIO_LEN = 20;
    private static final int CONTRASENA_LEN = 20;
    private static final int FECHA_LEN = 10;
    private static final int FOTO_LEN = 120;
    
    private static final int RECORD_SIZE = (NOMBRE_LEN * 2) + (GENERO_LEN * 2) + (USUARIO_LEN * 2) + (CONTRASENA_LEN * 2) + (FECHA_LEN * 2) + 4 + 4 + 4 + (FOTO_LEN * 2) + 1;
    
    public UsersRAF() {
        super(Paths.USERS_FILE);
    }
    
    @Override
    public boolean Agregar(Usuario usuario) {
        try {
            if (Buscar(usuario.getUsuario()) != null) {
                return false;
            }
            
            raf.seek(raf.length());
            EscribirUsuario(usuario);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error agregando usuario");
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Usuario Buscar(String usuario) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Usuario user = LeerUsuario();
                
                if (user.getUsuario().equalsIgnoreCase(usuario)) {
                    return user;
                }
                
                if (raf.getFilePointer() == posicion) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando usuario");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public long BuscarPosicion(String usuario) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Usuario user = LeerUsuario();
                
                if (user.getUsuario().equalsIgnoreCase(usuario)) {
                    return posicion;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando posicion de usuario");
            e.printStackTrace();
        }
        
        return -1;
    }
    
    @Override
    public ArrayList<Usuario> Listar() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                usuarios.add(LeerUsuario());
            }
        } catch (IOException e) {
            System.out.println("Error listando usuarios");
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    @Override
    public boolean Actualizar(Usuario usuario) {
        try {
            long posicion = BuscarPosicion(usuario.getUsuario());
            
            if (posicion == -1) {
                return false;
            }
            
            raf.seek(posicion);
            EscribirUsuario(usuario);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error actualizando usuario");
            e.printStackTrace();
            return false;
        }
    }
   
    @Override
    public boolean Login(String usuario, String contrasena) {
        Usuario user = Buscar(usuario);
        
        if (user == null) {
            return false;
        }
        
        return user.getContrasena().equals(contrasena) && user.getEstadoCuenta() == EstadoCuenta.ACTIVA && user.isActivo();
    }
    
    public boolean CambiarEstadoCuenta(String usuario, EstadoCuenta nuevoestado) {
        Usuario user = Buscar(usuario);
        
        if (user == null) {
            return false;
        }
        
        user.setEstadoCuenta(nuevoestado);
        user.setActivo(nuevoestado == EstadoCuenta.ACTIVA);
        
        return Actualizar(user);
    }
    
    public boolean ExisteUsuario(String usuario) {
        return Buscar(usuario) != null;
    }
    
    private void EscribirUsuario(Usuario usuario) throws IOException {
        RAFUtil.EscribirStringFijo(raf, usuario.getNombreCompleto(), NOMBRE_LEN);
        RAFUtil.EscribirStringFijo(raf, usuario.getGenero(), GENERO_LEN);
        RAFUtil.EscribirStringFijo(raf, usuario.getUsuario(), USUARIO_LEN);
        RAFUtil.EscribirStringFijo(raf, usuario.getContrasena(), CONTRASENA_LEN);
        RAFUtil.EscribirStringFijo(raf, usuario.getFechaRegistro(), FECHA_LEN);
        raf.writeInt(usuario.getEdad());
        raf.writeInt(usuario.getTipoCuenta().ordinal());
        raf.writeInt(usuario.getEstadoCuenta().ordinal());
        RAFUtil.EscribirStringFijo(raf, usuario.getFotoPerfil(), FOTO_LEN);
        raf.writeBoolean(usuario.isActivo());
    }
    
    private Usuario LeerUsuario() throws IOException {
        Usuario usuario = new Usuario();
        
        usuario.setNombreCompleto(RAFUtil.LeerStringFijo(raf, NOMBRE_LEN));
        usuario.setGenero(RAFUtil.LeerStringFijo(raf, GENERO_LEN));
        usuario.setUsuario(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        usuario.setContrasena(RAFUtil.LeerStringFijo(raf, CONTRASENA_LEN));
        usuario.setFechaRegistro(RAFUtil.LeerStringFijo(raf, FECHA_LEN));
        usuario.setEdad(raf.readInt());
        usuario.setTipoCuenta(TipoCuenta.values()[raf.readInt()]);
        usuario.setEstadoCuenta(EstadoCuenta.values()[raf.readInt()]);
        usuario.setFotoPerfil(RAFUtil.LeerStringFijo(raf, FOTO_LEN));
        usuario.setActivo(raf.readBoolean());
        
        return usuario;
    }
    
    public int getRecordSize() {
        return RECORD_SIZE;
    }
}
