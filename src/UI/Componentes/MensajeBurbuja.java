/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import Data.Paths.Paths;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import enums.TipoMensaje;
import modelo.Mensaje;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class MensajeBurbuja extends JPanel {
    
    public MensajeBurbuja(Mensaje mensaje, boolean esmio) {
        setLayout(new FlowLayout(esmio ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(4, 8, 4, 8));
        
        JPanel burbuja = new JPanel();
        burbuja.setLayout(new BoxLayout(burbuja, BoxLayout.Y_AXIS));
        burbuja.setOpaque(true);
        burbuja.setBackground(esmio ? new Color(220, 248, 198) : Color.WHITE);
        burbuja.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), new EmptyBorder(10, 12, 8, 12)));
        
        if (mensaje.getTipoMensaje() == TipoMensaje.STICKER) {
            JLabel lblsticker = CrearContenidoSticker(mensaje);
            lblsticker.setAlignmentX(Component.LEFT_ALIGNMENT);
            burbuja.add(lblsticker);
        } else {
            JTextArea txtcontenido = new JTextArea(mensaje.getContenido());
            txtcontenido.setEnabled(false);
            txtcontenido.setLineWrap(true);
            txtcontenido.setWrapStyleWord(true);
            txtcontenido.setOpaque(false);
            txtcontenido.setBorder(null);
            txtcontenido.setFont(UIConstantes.TEXTO_FONT);
            txtcontenido.setForeground(InstaColores.TEXTO_PRIMARIO);
            txtcontenido.setMaximumSize(new Dimension(320, Integer.MAX_VALUE));
            burbuja.add(txtcontenido);
        }
        
        burbuja.add(Box.createVerticalStrut(6));
        
        String hora = mensaje.getHora();
        
        if (hora != null && hora.length() >= 5) {
            hora = hora.substring(0, 5);
        }
        
        JLabel lblhora = new JLabel(mensaje.getFecha() + " " + hora);
        lblhora.setFont(UIConstantes.PEQUENO_FONT);
        lblhora.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblhora.setAlignmentX(esmio ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);
        burbuja.add(lblhora);
        
        add(burbuja);
    }
    
    private JLabel CrearContenidoSticker(Mensaje mensaje) {
        JLabel lblsticker = new JLabel();
        lblsticker.setFont(UIConstantes.TEXTO_FONT);
        lblsticker.setForeground(InstaColores.TEXTO_PRIMARIO);
        lblsticker.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String rutasticker = mensaje.getContenido();
        
        if (rutasticker == null || rutasticker.isBlank()) {
            lblsticker.setText("[Sticker]");
            return lblsticker;
        }
        
        File archivosticker = ResolverArchivoSticker(rutasticker, mensaje.getEmisor());
        
        if (archivosticker != null && archivosticker.exists() && archivosticker.isFile()) {
            ImageIcon iconooriginal = new ImageIcon(archivosticker.getAbsolutePath());
            
            if (iconooriginal.getIconWidth() > 0 && iconooriginal.getIconHeight() > 0) {
                Image imagenescalada = iconooriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                lblsticker.setIcon(new ImageIcon(imagenescalada));
                return lblsticker;
            }
        }
        
        lblsticker.setText("[Sticker] " + rutasticker);
        return lblsticker;
    }
    
    private File ResolverArchivoSticker(String valorguardado, String emisor) {
        File directo = new File(valorguardado);
        
        if (directo.exists() && directo.isFile()) {
            return directo;
        }
        
        String solonombre = new File(valorguardado).getName();
        
        File global = new File(Paths.STICKERS_GLOBALES, solonombre);
        
        if (global.exists() && global.isFile()) {
            return global;
        }
        
        if (emisor != null && !emisor.isBlank()) {
            File personal = new File(Paths.getFolderStickersPersonales(emisor), solonombre);
            
            if (personal.exists() && personal.isFile()) {
                return personal;
            }
        }
        
        return null;
    }
}
