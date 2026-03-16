/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import service.StickerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class StickerPickerPanel extends JPanel {
    
    private final String UsuarioActual;
    private final StickerService stickerService;
    private final Consumer<String> AccionSeleccionarSticker;
    private final Runnable AccionRefrescarDespuesDeImportar;
    
    private JPanel PanelContenido;
    private JLabel LblEstado;
     
    public StickerPickerPanel(String UsuarioActual, Consumer<String> AccionSeleccionarSticker, Runnable AccionRefrescarDespuesDeImportar) {
        this.UsuarioActual = UsuarioActual;
        this.AccionSeleccionarSticker = AccionSeleccionarSticker;
        this.AccionRefrescarDespuesDeImportar = AccionRefrescarDespuesDeImportar;
        stickerService = new StickerService();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel panelsuperior = new JPanel(new BorderLayout());
        panelsuperior.setBackground(Color.WHITE);
        panelsuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lbltitulo = new JLabel("Stickes");
        lbltitulo.setFont(UIConstantes.SUBTITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        JButton btnimportar = new JButton("+ Importar");
        btnimportar.setFocusPainted(false);
        btnimportar.setFont(UIConstantes.PEQUENO_FONT);
        btnimportar.addActionListener(e -> ImportarStickerPersonal());
        
        panelsuperior.add(lbltitulo, BorderLayout.WEST);
        panelsuperior.add(btnimportar, BorderLayout.EAST);
        
        PanelContenido = new JPanel();
        PanelContenido.setLayout(new BoxLayout(PanelContenido, BoxLayout.Y_AXIS));
        PanelContenido.setBackground(Color.WHITE);
        PanelContenido.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(PanelContenido);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblEstado.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        add(panelsuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(LblEstado, BorderLayout.SOUTH);
        
        CargarStickers();
    }
    
    private void CargarStickers() {
        PanelContenido.removeAll();
        
        ArrayList<String> globales = stickerService.ListarRutasCompletasStickersGlobales();
        ArrayList<String> personales = stickerService.ListarRutasCompletasStickersPersonales(UsuarioActual);
        
        AgregarSeccion("Globales", globales);
        AgregarSeccion("Personales", personales);
        
        if (globales.isEmpty() && personales.isEmpty()) {
            JLabel lblvacio = new JLabel("No hay stickers disponibles");
            lblvacio.setFont(UIConstantes.TEXTO_FONT);
            lblvacio.setForeground(InstaColores.TEXTO_SECUNDARIO);
            lblvacio.setAlignmentX(Component.LEFT_ALIGNMENT);
            PanelContenido.add(lblvacio);
        }
        
        PanelContenido.revalidate();
        PanelContenido.repaint();
    }
    
    private void AgregarSeccion(String titulo, ArrayList<String> rutas) {
        JLabel lbltitulo = new JLabel(titulo);
        lbltitulo.setFont(UIConstantes.TEXTO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        PanelContenido.add(lbltitulo);
        PanelContenido.add(Box.createVerticalStrut(8));
        
        JPanel grid = new JPanel(new GridLayout(0, 3, 10, 10));
        grid.setBackground(Color.WHITE);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        for (String ruta : rutas) {
            JButton boton = CrearBotonSticker(ruta);
            grid.add(boton);
        }
        
        PanelContenido.add(grid);
        PanelContenido.add(Box.createVerticalStrut(15));
    }
    
    private JButton CrearBotonSticker(String rutasticker) {
        JButton boton = new JButton();
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorder(BorderFactory.createLineBorder(InstaColores.BORDER));
        boton.setPreferredSize(new Dimension(90, 90));
        
        System.out.println("cargando sticker en picker: " + rutasticker);
        
        File archivo = new File(rutasticker);
        System.out.println("existe archivo: " + archivo.exists());
        System.out.println("es archivo: " + archivo.isFile());
        
        ImageIcon icono = new ImageIcon(rutasticker);
        System.out.println("ancho icono " + icono.getIconWidth());
        System.out.println("alto icono " + icono.getIconHeight());
        
        if (icono.getIconWidth() > 0 && icono.getIconHeight() > 0) {
            Image imagen = icono.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagen));
        } else {
            boton.setText("Sticker");
        }
        
        boton.addActionListener(e -> AccionSeleccionarSticker.accept(rutasticker));
        return boton;
    }
    
    private void ImportarStickerPersonal() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona un sticker");
        
        int resultado = chooser.showOpenDialog(this);
        
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File archivo = chooser.getSelectedFile();
        
        boolean importado = stickerService.ImportarStickerPersonal(UsuarioActual, archivo.getAbsolutePath());
        
        if (!importado) {
            MostrarEstado("No se pudo importar el sticker");
            return;
        }
        
        MostrarEstado("Sticker importado correctamente");
        CargarStickers();
        
        if (AccionRefrescarDespuesDeImportar != null) {
            AccionRefrescarDespuesDeImportar.run();
        }
    }
    
    private void MostrarEstado(String mensaje) {
        LblEstado.setText(mensaje);
    }
}
