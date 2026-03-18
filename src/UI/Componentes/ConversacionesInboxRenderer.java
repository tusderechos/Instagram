/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import Red.ManejoConexionChat;
import UI.Core.SessionManager;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConversacionesInboxRenderer extends JPanel implements ListCellRenderer<String> {
    
    private final SessionManager sessionManager;
    
    private JLabel LblUsuario;
    private JLabel LblPreview;
    private JLabel LblHora;
    private JLabel LblNoLeidos;
    
    public ConversacionesInboxRenderer(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        
        setLayout(new BorderLayout(12, 0));
        setBorder(new EmptyBorder(10, 12, 10, 12));
        setOpaque(true);
        
        JPanel paneltexto = new JPanel();
        paneltexto.setLayout(new BoxLayout(paneltexto, BoxLayout.Y_AXIS));
        paneltexto.setOpaque(false);
        
        LblUsuario = new JLabel();
        LblUsuario.setFont(UIConstantes.TEXTO_FONT);
        LblUsuario.setForeground(InstaColores.TEXTO_PRIMARIO);
        
        LblPreview = new JLabel();
        LblPreview.setFont(UIConstantes.PEQUENO_FONT);
        LblPreview.setForeground(InstaColores.TEXTO_SECUNDARIO);
        
        JPanel panelderecho = new JPanel();
        panelderecho.setLayout(new BoxLayout(panelderecho, BoxLayout.Y_AXIS));
        panelderecho.setOpaque(false);
        
        LblHora = new JLabel();
        LblHora.setFont(UIConstantes.PEQUENO_FONT);
        LblHora.setForeground(InstaColores.TEXTO_SECUNDARIO);
        LblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        LblHora.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        LblNoLeidos = new JLabel("●");
        LblNoLeidos.setForeground(InstaColores.AZUL);
        LblNoLeidos.setFont(new Font("SansSerif", Font.BOLD, 12));
        LblNoLeidos.setAlignmentX(Component.RIGHT_ALIGNMENT);
        LblNoLeidos.setVisible(false);
        
        paneltexto.add(LblUsuario);
        paneltexto.add(Box.createVerticalStrut(5));
        paneltexto.add(LblPreview);
        
        panelderecho.add(LblHora);
        panelderecho.add(Box.createVerticalStrut(8));
        panelderecho.add(LblNoLeidos);
        
        add(paneltexto, BorderLayout.CENTER);
        add(panelderecho, BorderLayout.EAST);
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        ManejoConexionChat chat = sessionManager.getManejoConexionChat();
        
        String preview = "";
        String hora = "";
        int noleidos = 0;
        
        if (chat != null) {
            preview = chat.ObtenerPreviewUltimoMensajeDe(value);
            hora = chat.ObtenerHoraUltimoMensajeDe(value);
            noleidos = chat.ContarNoLeidosDe(value);
        }
        
        if (preview == null || preview.isBlank()) {
            preview = "Sin mensajes";
        }
        
        if (hora == null) {
            hora = "";
        }
        
        boolean tienenoleidos = noleidos > 0;
        
        LblUsuario.setText("@" + value);
        LblPreview.setText("<html><body style='width:160px'>" + Escapar(preview) + "</body></html>");
        LblHora.setText(hora);
        LblNoLeidos.setVisible(tienenoleidos);
        
        if (tienenoleidos) {
            LblUsuario.setFont(new Font("SansSerif", Font.BOLD, 15));
            LblPreview.setFont(new Font("SansSerif", Font.BOLD, 12));
        } else {
            LblUsuario.setFont(UIConstantes.TEXTO_FONT);
            LblPreview.setFont(UIConstantes.PEQUENO_FONT);
        }
        
        if (isSelected) {
            setBackground(InstaColores.FONDO_SECUNDARIO);
        } else if (tienenoleidos) {
            setBackground(new Color(248, 250, 252));
        } else {
            setBackground(InstaColores.CARD);
        }
        
        return this;
    }
    
    private String Escapar(String texto) {
        if (texto == null) {
            return "";
        }
        
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "^gt;");
    }
}
