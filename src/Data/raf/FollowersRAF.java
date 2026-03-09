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

public class FollowersRAF extends BaseRAF {
    
    private static final int USUARIO_LEN = 20;
    
    public FollowersRAF(String propietario) {
        super(Paths.getFileSeguidores(propietario));
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
            System.out.println("Error agregando follower");
            e.printStackTrace();
            return false;
        }
    }
    
    public Follow Buscar(String usernamerelacionado) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Follow seguir = LeerFollow();
                
                if (seguir.getUsernameRelacionado().equalsIgnoreCase(usernamerelacionado) && seguir.isActivo()) {
                    return seguir;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error buscando follower");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public long BuscarPosicion(String usernamerelacionado) {
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                long posicion = raf.getFilePointer();
                Follow seguir = LeerFollow();
                
                if (seguir.getUsernameRelacionado().equalsIgnoreCase(usernamerelacionado) && seguir.isActivo()) {
                    return posicion;
                }
            }
        } catch (IOException e) {
            System.out.println("Error buscando la posicion del follower");
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public boolean ExisteRelacion(String usernamerelacionado) {
        return Buscar(usernamerelacionado) != null;
    }
    
    public ArrayList<Follow> ListarTodos() {
        ArrayList<Follow> seguidores = new ArrayList<>();
        
        try {
            raf.seek(0);
            
            while (raf.getFilePointer() < raf.length()) {
                Follow seguir = LeerFollow();
                
                if (seguir.isActivo()) {
                    seguidores.add(seguir);
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error listando followers");
            e.printStackTrace();
        }
        
        return seguidores;
    }
    
    public ArrayList<String> ListarUsuarios() {
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Follow> seguidores = ListarTodos();
        
        for (Follow seguidor : seguidores) {
            lista.add(seguidor.getUsernameRelacionado());
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
            
            Follow seguidor = LeerFollowDesdePosicionActual();
            seguidor.setActivo(false);
            
            raf.seek(posicion);
            EscribirFollow(seguidor);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error eliminando follower");
            e.printStackTrace();
            return false;
        }
    }
    
    private void EscribirFollow(Follow seguidor) throws IOException {
        RAFUtil.EscribirStringFijo(raf, seguidor.getUsernameRelacionado(), USUARIO_LEN);
        raf.writeBoolean(seguidor.isActivo());
    }
    
    private Follow LeerFollow() throws IOException {
        Follow seguidor = new Follow();
        seguidor.setUsernameRelacionado(RAFUtil.LeerStringFijo(raf, USUARIO_LEN));
        seguidor.setActivo(raf.readBoolean());
        return seguidor;
    }
    
    private Follow LeerFollowDesdePosicionActual() throws IOException {
        return LeerFollow();
    }
}
