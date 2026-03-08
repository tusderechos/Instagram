/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */

import enums.EstadoCuenta;
import enums.TipoCuenta;

public class Usuario extends RegistroBase {
    
    private String NombreCompleto;
    private String Genero;
    private String Usuario;
    private String Contrasena;
    private String FechaRegistro;
    private int Edad;
    private TipoCuenta tipoCuenta;
    private EstadoCuenta estadoCuenta;
    private String FotoPerfil;
    
    public Usuario() {
        super();
        estadoCuenta = EstadoCuenta.ACTIVA;
        tipoCuenta = TipoCuenta.PUBLICA;
        FotoPerfil = "";
    }

    public Usuario(String NombreCompleto, String Genero, String Usuario, String Contrasena, String FechaRegistro, int Edad, TipoCuenta tipoCuenta, String FotoPerfil) {
        this.NombreCompleto = NombreCompleto;
        this.Genero = Genero;
        this.Usuario = Usuario;
        this.Contrasena = Contrasena;
        this.FechaRegistro = FechaRegistro;
        this.Edad = Edad;
        this.tipoCuenta = tipoCuenta;
        this.estadoCuenta = estadoCuenta.ACTIVA;
        this.FotoPerfil = FotoPerfil;
    }
    
    @Override
    public String getClavePrimaria() {
        return Usuario;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public String getFotoPerfil() {
        return FotoPerfil;
    }

    public void setFotoPerfil(String FotoPerfil) {
        this.FotoPerfil = FotoPerfil;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "NombreCompleto='" + NombreCompleto + '\'' + ", Genero='" + Genero + '\'' + ", Usuario='" + Usuario + '\'' + ", FechaRegistro='" + FechaRegistro + '\'' + ", Edad=" + Edad + ", TipoCuenta=" + tipoCuenta + ", EstadoCuenta=" + estadoCuenta + ", FotoPerfil='" + FotoPerfil + '\'' + ", Activo=" + Activo + '}';
    }
}
