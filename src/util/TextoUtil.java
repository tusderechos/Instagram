/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author USUARIO
 */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextoUtil {
    
    private TextoUtil() {
        
    }
    
    public static ArrayList<String> ExtraerHashtags(String texto) {
        ArrayList<String> lista = new ArrayList<>();
        
        if (texto == null || texto.isBlank()) {
            return lista;
        }
        
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(texto);
        
        while (matcher.find()) {
            String hashtag = matcher.group();
            
            if (!lista.contains(hashtag)) {
                lista.add(hashtag);
            }
        }
        
        return lista;
    }
    
    public static ArrayList<String> ExtraerMenciones(String texto) {
        ArrayList<String> lista = new ArrayList<>();
        
        if (texto == null || texto.isBlank()) {
            return lista;
        }
        
        Pattern pattern = Pattern.compile("@\\w+");
        Matcher matcher = pattern.matcher(texto);
        
        while (matcher.find()) {
            String mencion = matcher.group();
            
            if (!lista.contains(mencion)) {
                lista.add(mencion);
            }
        }
        
        return lista;
    }
    
    public static String ArrayListToTexto(ArrayList<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            
            if (i < lista.size() - 1) {
                sb.append("|");
            }
        }
        
        return sb.toString();
    }
    
    public static ArrayList<String> TextoToArrayList(String texto) {
        ArrayList<String> lista = new ArrayList<>();
        
        if (texto == null || texto.isBlank()) {
            return lista;
        }
        
        String[] partes = texto.split("\\|");
        
        for (String parte : partes) {
            String limpio = parte.trim();
            
            if (!limpio.isBlank() && !lista.contains(limpio)) {
                lista.add(limpio);
            }
        }
        
        return lista;
    }
}
