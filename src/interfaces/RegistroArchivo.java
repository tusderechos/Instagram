/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author USUARIO
 */

import java.util.ArrayList;

public interface RegistroArchivo<T> {
    boolean Agregar(T obj);
    T Buscar(String clave);
    ArrayList<T> Listar();
    boolean Actualizar(T obj);
}
