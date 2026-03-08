/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */
public abstract class RegistroBase {
    
    protected boolean Activo;
    
    public RegistroBase() {
        Activo = true;
    }
    
    public boolean isActivo() {
        return Activo;
    }
    
    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }
    
    public abstract String getClavePrimaria();
}
