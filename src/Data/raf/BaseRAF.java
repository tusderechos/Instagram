/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.raf;

/**
 *
 * @author USUARIO
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class BaseRAF {
    
    protected RandomAccessFile raf;
    protected String path;
    
    public BaseRAF(String path) {
        this.path = path;
        Abrir();
    }
    
    private void Abrir() {
        try {
            File file = new File(path);
            raf = new RandomAccessFile(file, "rw");
        } catch (IOException e) {
            System.out.println("Error abriendo archivo RAF: " + path);
            e.printStackTrace();
        }
    }
    
    public void Cerrar() {
        try {
            if (raf != null) {
                raf.close();
            }
        } catch (IOException e) {
            System.out.println("Error cerrando archivo RAF: " + path);
            e.printStackTrace();
        }
    }
    
    protected long TotalRegistros(int tamanoregistro) {
        try {
            return raf.length() / tamanoregistro;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
