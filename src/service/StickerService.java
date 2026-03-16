/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author USUARIO
 */

import Data.Paths.Paths;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class StickerService {
    
    public StickerService() {
        CrearCarpetasBaseSiNoExisten();
    }
    
    public void CrearCarpetasBaseSiNoExisten() {
        File carpetaglobal = new File(Paths.STICKERS_GLOBALES);
        
        if (!carpetaglobal.exists()) {
            carpetaglobal.mkdirs();
        }
    }
    
    public boolean CrearCarpetaStickersPersonalesSiNoExiste(String usuario) {
        if (usuario == null || usuario.isBlank()) {
            return false;
        }
        
        File carpeta = new File(Paths.getFolderStickersPersonales(usuario));
        
        if (carpeta.exists()) {
            return true;
        }
        
        return carpeta.mkdirs();
    }
    
    public String ObtenerRutaCarpetaStickersGlobales() {
        return Paths.STICKERS_GLOBALES;
    }
    
    public String ObtenerRutaCarpetaStickersPersonales(String usuario) {
        return Paths.getFolderStickersPersonales(usuario);
    }
    
    public ArrayList<String> ListarStickersGlobales() {
        return ListarArchivosValidosDeCarpeta(ObtenerRutaCarpetaStickersGlobales());
    }
    
    public ArrayList<String> ListarStickersPersonales(String usuario) {
        CrearCarpetaStickersPersonalesSiNoExiste(usuario);
        return ListarArchivosValidosDeCarpeta(ObtenerRutaCarpetaStickersPersonales(usuario));
    }
    
    public ArrayList<String> ListarRutasCompletasStickersGlobales() {
        return ListarRutasCompletasDeCarpeta(ObtenerRutaCarpetaStickersGlobales());
    }
    
    public ArrayList<String> ListarRutasCompletasStickersPersonales(String usuario) {
        CrearCarpetaStickersPersonalesSiNoExiste(usuario);
        return ListarRutasCompletasDeCarpeta(ObtenerRutaCarpetaStickersPersonales(usuario));
    }
    
    public ArrayList<String> ListarTodasLasRutasStickers(String usuario) {
        ArrayList<String> todas = new ArrayList<>();
        todas.addAll(ListarRutasCompletasStickersGlobales());
        todas.addAll(ListarRutasCompletasStickersPersonales(usuario));
        return todas;
    }
    
    public boolean ImportarStickerPersonal(String usuario, String rutaorigen) {
        if (usuario == null || usuario.isBlank() || rutaorigen == null || rutaorigen.isBlank()) {
            return false;
        }
        
        File archivoorigen = new File(rutaorigen);
        
        if (!archivoorigen.exists() || !archivoorigen.isFile()) {
            return false;
        }
        
        if (!EsArchivoStickerValido(archivoorigen.getName())) {
            return false;
        }
        
        CrearCarpetaStickersPersonalesSiNoExiste(usuario);
        
        String nombrebase = archivoorigen.getName();
        String nombrefinal = GenerarNombreDisponible(Paths.getFolderStickersPersonales(usuario), nombrebase);
        File archivodestino = new File(Paths.getFolderStickersPersonales(usuario), nombrefinal);
        
        return CopiarArchivo(archivoorigen, archivodestino);
    }
    
    public boolean ExisteStickerEnRuta(String rutasticker) {
        if (rutasticker == null || rutasticker.isBlank()) {
            return false;
        }
        
        File archivo = new File(rutasticker);
        return archivo.exists() && archivo.isFile() && EsArchivoStickerValido(archivo.getName());
    }
    
    public boolean EsArchivoStickerValido(String nombrearchivo) {
        if (nombrearchivo == null || nombrearchivo.trim().isEmpty()) {
            return false;
        }
        
        String nombre = nombrearchivo.toLowerCase();
        
        return nombre.endsWith(".png") || nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || nombre.endsWith(".gif") || nombre.endsWith(".webp");
    }
    
    private ArrayList<String> ListarArchivosValidosDeCarpeta(String rutacarpeta) {
        ArrayList<String> stickers = new ArrayList<>();
        File carpeta = new File(rutacarpeta);
        
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            return stickers;
        }
        
        File[] archivos = carpeta.listFiles();
        
        if (archivos == null) {
            return stickers;
        }
        
        for (File archivo : archivos) {
            if (archivo.isFile() && EsArchivoStickerValido(archivo.getName())) {
                stickers.add(archivo.getName());
            }
        }
        
        Collections.sort(stickers);
        return stickers;
    }
    
    private ArrayList<String> ListarRutasCompletasDeCarpeta(String rutacarpeta) {
        ArrayList<String> rutas = new ArrayList<>();
        File carpeta = new File(rutacarpeta);
        
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            return rutas;
        }
        
        File[] archivos = carpeta.listFiles();
        
        if (archivos == null) {
            return rutas;
        }
        
        for (File archivo : archivos) {
            if (archivo.isFile() && EsArchivoStickerValido(archivo.getName())) {
                rutas.add(archivo.getAbsolutePath());
            }
        }
        
        Collections.sort(rutas);
        return rutas;
    }
    
    private String GenerarNombreDisponible(String carpetadestino, String nombreoriginal) {
        File archivooriginal = new File(nombreoriginal);
        String nombre = archivooriginal.getName();
        
        int punto = nombre.lastIndexOf('.');
        String base = punto >= 0 ? nombre.substring(0, punto) : nombre;
        String extension = punto >= 0 ? nombre.substring(punto) : "";
        
        File candidato = new File(carpetadestino, nombre);
        
        if (!candidato.exists()) {
            return nombre;
        }
        
        int contador = 1;
        
        while (true) {
            String nuevonombre = base + "_" + contador + extension;
            candidato = new File(carpetadestino, nuevonombre);
            
            if (!candidato.exists()) {
                return nuevonombre;
            }
            
            contador++;
        }
    }
    
    private boolean CopiarArchivo(File origen, File destino) {
        try {
            FileInputStream entrada = new FileInputStream(origen);
            FileOutputStream salida = new FileOutputStream(destino);
            
            byte[] buffer = new byte[4096];
            int leidos;
            
            while ((leidos = entrada.read(buffer)) != -1) {
                salida.write(buffer, 0, leidos);
            }
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Error copiando sticker personal");
            e.printStackTrace();
            return false;
        }
    }
}
