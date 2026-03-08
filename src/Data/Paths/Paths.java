/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data.Paths;

/**
 *
 * @author USUARIO
 */

import java.io.File;

public final class Paths {
    
    public static final String RAIZ = "INSTA_RAIZ";
    public static final String USERS_FILE = RAIZ + "/users.ins";
    public static final String STICKERS_GLOBALES = RAIZ + "/stickers_globales";
    
    private Paths() {
    }
    
    public static void InicializarSistema() {
        CrearDirectorioSiNoExiste(new File(RAIZ));
        CrearDirectorioSiNoExiste(new File(STICKERS_GLOBALES));
        CrearArchivoVacioSiNoExiste(USERS_FILE);
    }
    
    public static String getFolderUsuario(String usuario) {
        return RAIZ + "/" + usuario;
    }
    
    public static String getFileSeguidores(String usuario) {
        return getFolderUsuario(usuario) + "/followers.ins";
    }
    
    public static String getFileSeguidos(String usuario) {
        return getFolderUsuario(usuario) + "/following.ins";
    }
    
    public static String getFileRequests(String usuario) {
        return getFolderUsuario(usuario) + "/requests.ins";
    }
    
    public static String getFilePosts(String usuario) {
        return getFolderUsuario(usuario) + "/insta.ins";
    }
    
    public static String getFileInbox(String usuario) {
        return getFolderUsuario(usuario) + "/inbox.ins";
    }
    
    public static String getFileStickers(String usuario) {
        return getFolderUsuario(usuario) + "/stickers.ins";
    }
    
    public static String getFolderImagenes(String usuario) {
        return getFolderUsuario(usuario) + "/imagenes";
    }
    
    public static String getFoldersPersonalFolder(String usuario) {
        return getFolderUsuario(usuario) + "/folders_personales";
    }
    
    public static String getFolderStickersPersonales(String usuario) {
        return getFolderUsuario(usuario) + "/stickers_personales";
    }
    
    public static void CrearEstructuraUsuario(String usuario) {
        String[] rutas = {getFolderUsuario(usuario), getFolderImagenes(usuario), getFoldersPersonalFolder(usuario), getFolderStickersPersonales(usuario)};
    
        CrearDirectoriosRec(rutas, 0);
        
        CrearArchivoVacioSiNoExiste(getFileSeguidores(usuario));
        CrearArchivoVacioSiNoExiste(getFileSeguidos(usuario));
        CrearArchivoVacioSiNoExiste(getFileRequests(usuario));
        CrearArchivoVacioSiNoExiste(getFilePosts(usuario));
        CrearArchivoVacioSiNoExiste(getFileInbox(usuario));
        CrearArchivoVacioSiNoExiste(getFileStickers(usuario));
    }
    
    private static void CrearDirectoriosRec(String[] rutas, int indice) {
        if (indice >= rutas.length) {
            return;
        }
        
        CrearDirectorioSiNoExiste(new File(rutas[indice]));
        CrearDirectoriosRec(rutas, indice + 1);
    }
    
    private static void CrearDirectorioSiNoExiste(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    private static void CrearArchivoVacioSiNoExiste(String ruta) {
        try {
            File archivo = new File(ruta);
            
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error creando archivo: " + ruta);
            e.printStackTrace();
        }
    }
}
