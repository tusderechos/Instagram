/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */
public class Follow extends RegistroBase {
    
    private String UsernameRelacionado;
    
    public Follow() {
        super();
    }
    
    public Follow(String UsernameRelacionado) {
        this.UsernameRelacionado = UsernameRelacionado;
    }
    
    @Override
    public String getClavePrimaria() {
        return UsernameRelacionado;
    }

    public String getUsernameRelacionado() {
        return UsernameRelacionado;
    }

    public void setUsernameRelacionado(String UsernameRelacionado) {
        this.UsernameRelacionado = UsernameRelacionado;
    }
    
    @Override
    public String toString() {
        return "Follow{" + "UsernameRelacionado='" + UsernameRelacionado + '\'' + ", activo='" + Activo + '}';
    }
}
