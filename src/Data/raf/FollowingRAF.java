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
import modelo.Follow;

import java.io.IOException;
import java.util.ArrayList;

public class FollowingRAF extends BaseRAF {
    
    private static final int USUARIO_LEN = 20;
    
    public FollowingRAF(String propietario) {
        super(Paths.getFileSeguidos(propietario));
    }
    
    public boolean Agregar(String usernamerelacionado) {
        try {
            if (ExisteRelacion(usernamerelacionado)) {
                return false;
            }
            
            raf.seek(raf.length());
            EscribirFollow(new Follow(usernamerelacionado));
            return true;
            
        } catch (IOException e) {
            System.out.println("Error agregando following");
            e.printStackTrace();
            return false;
        }
    }
    
    public Follow Buscar(String usernamerelacionado) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Follow seguidor = LeerFollow();
                
                if (seguidor.getUsernameRelacionado().equalsIgnoreCase(usernamerelacionado) && seguidor.isActivo()) {
                    return seguidor;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error buscando following");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public long BuscarPosicion(String usernamerelacionado) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Follow seguidor = LeerFollow();
                
                if (seguidor.getUsernameRelacionado().equalsIgnoreCase(usernamerelacionado) && seguidor.isActivo()) {
                    return posicion;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error buscando posicion del following");
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public boolean ExisteRelacion(String usernamerelacionado) {
        return Buscar(usernamerelacionado) != null;
    }
    
    public ArrayList<Follow> ListarTodos() {
        ArrayList<Follow> seguidos = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Follow seguido = LeerFollow();
                
                if (seguido.isActivo()) {
                    seguidos.add(seguido);
                }
            }
        } catch (IOException e) {
            System.out.println("Error listando following");
            e.printStackTrace();
        }
        
        return seguidos;
    }
    
    public ArrayList<String> ListarUsuarios() {
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Follow> seguidos = ListarTodos();
        
        for (Follow seguido : seguidos) {
            lista.add(seguido.getUsernameRelacionado());
        }
        
        return lista;
    }
    
    public int Contar() {
        return ListarTodos().size();
    }
    
    public boolean Eliminar(String usernamerelacionado) {
        try {
            long posicion = BuscarPosicion(usernamerelacionado);
            
            if (posicion == -1) {
                return false;
            }
            
            raf.seek(posicion);
            
            Follow seguido = LeerFollowDesdePosicionActual();
            seguido.setActivo(false);
            
            raf.seek(posicion);
            EscribirFollow(seguido);
            return true;
        } catch (IOException e) {
            System.out.println("Error eliminando following");
            e.printStackTrace();
            return false;
        }
    }
    
    private void EscribirFollow(Follow seguido) throws IOException {
        RAFUtil.EscribirStringFijo(raf, seguido.getUsernameRelacionado(), USUARIO_LEN);
        raf.writeBoolean(seguido.isActivo());
    }
    
    private Follow LeerFollow() throws IOException {
        Follow seguido = new Follow();
        seguido.setUsernameRelacionado(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        seguido.setActivo(raf.readBoolean());
        
        return seguido;
    }
    
    private Follow LeerFollowDesdePosicionActual() throws IOException {
        return LeerFollow();
    }
}
