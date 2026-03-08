/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.raf;

/**
 *
 * @author USUARIO
 */

import java.io.IOException;
import java.io.RandomAccessFile;

public final class RAFUtil {
    
    private RAFUtil() {
        
    }
    
    public static void EscribirStringFijo(RandomAccessFile raf, String valor, int longitud) throws IOException {
        
        if (valor == null) {
            valor = "";
        }
        
        StringBuilder sb = new StringBuilder(valor);
        
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        }
        
        while (sb.length() < longitud) {
            sb.append(' ');
        }
        
        raf.writeChars(sb.toString());
    }
    
    public static String LeerStringFijo(RandomAccessFile raf, int longitud) throws IOException {
        char[] chars = new char[longitud];
        
        for (int i = 0; i < longitud; i++) {
            chars[i] = raf.readChar();
        }
        
        return new String(chars).trim();
    }
    
    public static long TotalRegistros(RandomAccessFile raf, int recordsize) throws IOException {
        return raf.length() / recordsize;
    }
    
    public static void IrARegistro(RandomAccessFile raf, int indice, int recordsize) throws IOException {
        raf.seek((long) indice * recordsize);
    }
}
