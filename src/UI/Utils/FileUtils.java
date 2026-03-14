/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Utils;

/**
 *
 * @author USUARIO
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class FileUtils {
    
    private FileUtils() {
        
    }
    
    public static String CopiarImagenPost(String rutaorigen, String usuario) {
        if (rutaorigen == null || rutaorigen.isBlank()) {
            return "";
        }
        
        try {
            File origen = new File(rutaorigen);
            
            if (!origen.exists()) {
                return "";
            }
            
            String extension = ObtenerExtension(origen.getName());
            String nombrearchivo = "post_" + System.currentTimeMillis() + extension;
            String rutadestino = "INSTA_RAIZ/" + usuario + "/imagenes/" + nombrearchivo;
            
            File destino = new File(rutadestino);
            destino.getParentFile().mkdirs();
            
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            return destino.getPath();
            
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    private static String ObtenerExtension(String nombrearchivo) {
        int punto = nombrearchivo.lastIndexOf(".");
        
        if (punto == -1) {
            return ".png";
        }
        
        return nombrearchivo.substring(punto);
    }
}
